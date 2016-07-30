package uk.co.richyhbm.coinbag.exchanges;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import okhttp3.Request;
import okhttp3.Response;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

public class BlockChain extends Exchange {

    public BlockChain() {
        super(10);
    }

    @Override
    protected double getExchange() throws IOException {
        Request req = new Request.Builder().url("https://blockchain.info/ticker").build();
        Response res = client.newCall(req).execute();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<BlockChainExchangeResponse> jsonAdapter = moshi.adapter(BlockChainExchangeResponse.class);

        BlockChainExchangeResponse exchange = jsonAdapter.fromJson(res.body().string());
        if(exchange == null)
            return 0;

        return exchange.USD.last;
    }

    static class BlockChainExchangeResponse {
        public Ticker USD;

        static class Ticker {
            @Json(name = "15m") public double _15m;
            public double last;
            public double buy;
            public double sell;
            public String symbol;
        }
    }
}
