package uk.co.richyhbm.coinbag.balances;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import okhttp3.OkHttpClient;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

//TODO: Use AsyncTask to not make requests on the main thread
public abstract class Balance {
    public class CachedBalance {
        public Date nextRequestAt = new Date();
        public String balance;
    }

    public static final HashMap<CryptoCurrencies, Balance> balanceFetchers = new HashMap<CryptoCurrencies, Balance>();
    static {
        balanceFetchers.put(CryptoCurrencies.Bitcoin, new BlockChainInfo());
    }

    private int rateLimitSeconds;
    private HashMap<String, CachedBalance> cachedBalances = new HashMap<String, CachedBalance>();
    protected OkHttpClient client = new OkHttpClient();
    private Object lock = new Object();

    public Balance(int rateLimitSeconds) {
        this.rateLimitSeconds = rateLimitSeconds;
    }

    public final String getBalanceForAddress(String address) {
        Date now = new Date();
        if (!cachedBalances.containsKey(address) || now.after(cachedBalances.get(address).nextRequestAt)) {
            try {
                String balance = getBalance(address);
                CachedBalance cb = new CachedBalance();
                cb.balance = balance;
                cb.nextRequestAt.setTime(now.getTime() + rateLimitSeconds * 1000);
                cachedBalances.put(address, cb);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return "";
            }
        }

        return cachedBalances.get(address).balance;
    }

    protected abstract String getBalance(String address) throws IOException;
}
