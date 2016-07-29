package uk.co.richyhbm.coinbag.balances;


import android.util.Log;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.Request;
import okhttp3.Response;

//Balance fetcher for BlockChain Info
public class BlockChainInfo extends Balance {
    public BlockChainInfo() {
        super(10);
    }

    @Override
    protected double getBalance(String address) throws IOException {
        //Fetches the address balance and returns it in BTC
        Request req = new Request.Builder().url("https://blockchain.info/q/addressbalance/" + address).build();
        Response res = client.newCall(req).execute();
        String s = res.body().string();
        try {
            Double satoshis = Double.parseDouble(s);
            Double btc = satoshis / 100000000.0;

            DecimalFormat df = new DecimalFormat("#.########");
            df.setRoundingMode(RoundingMode.CEILING);
            return Double.parseDouble(df.format(btc));
        }catch(ArithmeticException ae) {
            ae.printStackTrace();
            return -1;
        }
    }
}
