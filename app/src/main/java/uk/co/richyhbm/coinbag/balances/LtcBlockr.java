package uk.co.richyhbm.coinbag.balances;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import okhttp3.Request;
import okhttp3.Response;

//Balance fetcher for Blockr Info
public class LtcBlockr extends Balance {
    public LtcBlockr() {
        super(10);
    }

    @Override
    protected double getBalance(String address) throws IOException {
        //Fetches the address balance and returns it in LTC
        Request req = new Request.Builder().url("https://ltc.blockr.io/api/v1/address/balance/" + address).build();
        Response res = client.newCall(req).execute();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<BlockrResponse> jsonAdapter = moshi.adapter(BlockrResponse.class);

        BlockrResponse ltcResponse = jsonAdapter.fromJson(res.body().string());
        if(ltcResponse == null || ltcResponse.code != 200)
            return -1;

        DecimalFormat df = new DecimalFormat("#.########");
        df.setRoundingMode(RoundingMode.HALF_DOWN);
        Double ether = ltcResponse.data.balance;
        return Double.parseDouble(df.format(ether));
    }

    static class BlockrResponse {
        public String status;
        public int code;
        public String message;
        public Data data;

        static class Data {
            public String address;
            public double balance;
            public double balance_multisig;
        }
    }
}