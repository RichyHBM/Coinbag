package uk.co.richyhbm.coinbag.exchanges;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class LtcBlockr extends Exchange {

    public LtcBlockr() {
        super(60);
    }

    @Override
    protected double getExchange() throws IOException {
        Request req = new Request.Builder().url("https://ltc.blockr.io/api/v1/exchangerate/current").build();
        Response res = client.newCall(req).execute();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<BlockrExchangeResponse> jsonAdapter = moshi.adapter(BlockrExchangeResponse.class);

        BlockrExchangeResponse exchange = jsonAdapter.fromJson(res.body().string());
        if(exchange == null || exchange.code != 200)
            return 0;

        try {
            Double ltcToDollar = Double.parseDouble(exchange.data.rates.LTC);
            return 1.0 / ltcToDollar;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    static class BlockrExchangeResponse {
        public String status;
        public int code;
        public Data data;

        static class Data {
            public String base;
            public Rates rates;

            static class Rates {
                public String BTC;
                public String LTC;
                public String EUR;
                public String ZAR;
                public String THB;
                public String SGD;
                public String PHP;
                public String NZD;
                public String MYR;
                public String MXN;
                public String KRW;
                public String INR;
                public String ILS;
                public String IDR;
                public String HKD;
                public String CNY;
                public String CAD;
                public String BRL;
                public String AUD;
                public String TRY;
                public String RUB;
                public String HRK;
                public String NOK;
                public String CHF;
                public String SEK;
                public String RON;
                public String PLN;
                public String HUF;
                public String GBP;
                public String DKK;
                public String CZK;
                public String BGN;
                public String JPY;
                public String USD;
            }
        }
    }
}