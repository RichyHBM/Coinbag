package uk.co.richyhbm.coinbag.balances;

import android.util.Log;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.Request;
import okhttp3.Response;

//Balance fetcher for DashExplorer
public class DashExplorer extends Balance {
    public DashExplorer() {
        super(10);
    }

    @Override
    protected double getBalance(String address) throws IOException {
        //Fetches the address balance and returns it in DASH
        Request req = new Request.Builder().url("https://explorer.dash.org/chain/Dash/q/addressbalance/" + address).build();
        Response res = client.newCall(req).execute();
        String s = res.body().string();
        try {
            Double dash = Double.parseDouble(s);

            DecimalFormat df = new DecimalFormat("#.########");
            df.setRoundingMode(RoundingMode.HALF_DOWN);
            return Double.parseDouble(df.format(dash));
        }catch(ArithmeticException ae) {
            ae.printStackTrace();
            return -1;
        }
    }
}
