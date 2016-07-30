package uk.co.richyhbm.coinbag.balances;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.OkHttpClient;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

//Abstract class for defining a way to get a balance in a wallet
public abstract class Balance {
    public class CachedBalance {
        public Date nextRequestAt = new Date();
        public double balance;
    }

    //Store a list of crypto type to balance fetcher
    public static final HashMap<CryptoCurrencies, Balance> balanceFetchers = new HashMap<CryptoCurrencies, Balance>();
    static {
        balanceFetchers.put(CryptoCurrencies.Bitcoin, new BlockChainInfo());
        balanceFetchers.put(CryptoCurrencies.Ethereum, new EtherChain());
        balanceFetchers.put(CryptoCurrencies.Litecoin, new LtcBlockr());
        balanceFetchers.put(CryptoCurrencies.Dash, new DashExplorer());
    }

    //Keep a cache so that the balances are only refreshed every X seconds at most
    private int rateLimitSeconds;
    private HashMap<String, CachedBalance> cachedBalances = new HashMap<String, CachedBalance>();
    protected OkHttpClient client = new OkHttpClient();

    public Balance(int rateLimitSeconds) {
        this.rateLimitSeconds = rateLimitSeconds;
    }

    public final double getBalanceForAddress(String address) {
        Date now = new Date();
        //Only fetch a new balance if the last time it was fetched was before X seconds, or it hasn't been fetched before
        if (!cachedBalances.containsKey(address) || now.after(cachedBalances.get(address).nextRequestAt)) {
            try {
                double balance = getBalance(address);
                CachedBalance cb = new CachedBalance();
                cb.balance = balance;
                cb.nextRequestAt.setTime(now.getTime() + rateLimitSeconds * 1000);
                cachedBalances.put(address, cb);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return -1;
            }
        }

        return cachedBalances.get(address).balance;
    }

    protected abstract double getBalance(String address) throws IOException;
}
