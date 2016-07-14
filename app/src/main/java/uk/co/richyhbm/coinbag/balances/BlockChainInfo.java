package uk.co.richyhbm.coinbag.balances;


import android.util.Log;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class BlockChainInfo extends Balance {
    public BlockChainInfo() {
        super(10);
    }

    @Override
    protected String getBalance(String address) throws IOException {
        Request req = new Request.Builder().url("https://blockchain.info/q/addressbalance/" + address).build();
        Response res = client.newCall(req).execute();
        String s = res.body().string();
        try {
            Double satoshis = Double.parseDouble(s);
            Double btc = satoshis / 100000000.0;
            return btc.toString() + " BTC";
        }catch(ArithmeticException ae) {
            return ae.getMessage();
        }
    }
}
