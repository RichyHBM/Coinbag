package uk.co.richyhbm.coinbag.exchanges;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import uk.co.richyhbm.coinbag.balances.BlockChainInfo;
import uk.co.richyhbm.coinbag.balances.DashExplorer;
import uk.co.richyhbm.coinbag.balances.EtherChain;
import uk.co.richyhbm.coinbag.balances.LtcBlockr;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

public abstract class Exchange {
    public class CachedExchangeValue {
        public Date nextRequestAt = new Date();
        public double value;
    }

    //Store a list of crypto type to balance fetcher
    public static final HashMap<CryptoCurrencies, Exchange> exchangeFetchers = new HashMap<CryptoCurrencies, Exchange>();
    static {
        exchangeFetchers.put(CryptoCurrencies.Bitcoin, new BlockChain());
        exchangeFetchers.put(CryptoCurrencies.Litecoin, new CoinMarketCap(CryptoCurrencies.Litecoin));
        exchangeFetchers.put(CryptoCurrencies.Ethereum, new CoinMarketCap(CryptoCurrencies.Ethereum));
        exchangeFetchers.put(CryptoCurrencies.Dash, new CoinMarketCap(CryptoCurrencies.Dash));
    }

    //Keep a cache so that the balances are only refreshed every X seconds at most
    private int rateLimitSeconds;
    private HashMap<CryptoCurrencies, CachedExchangeValue> cachedExchanges = new HashMap<CryptoCurrencies, CachedExchangeValue>();
    protected OkHttpClient client = new OkHttpClient();

    public Exchange(int rateLimitSeconds) {
        this.rateLimitSeconds = rateLimitSeconds;
    }

    public final double getExchangeForCurrency(CryptoCurrencies currency) {
        Date now = new Date();
        //Only fetch a new balance if the last time it was fetched was before X seconds, or it hasn't been fetched before
        if (!cachedExchanges.containsKey(currency) || now.after(cachedExchanges.get(currency).nextRequestAt)) {
            try {
                double value = getExchange();
                CachedExchangeValue ce = new CachedExchangeValue();
                ce.value = value;
                ce.nextRequestAt.setTime(now.getTime() + rateLimitSeconds * 1000);
                cachedExchanges.put(currency, ce);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return -1;
            }
        }

        return cachedExchanges.get(currency).value;
    }

    protected abstract double getExchange() throws IOException;
}
