package uk.co.richyhbm.coinbag.exchanges;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

public class CoinMarketCap extends Exchange {

    CryptoCurrencies cryptoType;
    public CoinMarketCap(CryptoCurrencies cryptoType) {
        super(60);
        this.cryptoType = cryptoType;
    }

    @Override
    protected double getExchange() throws IOException {
        Request req = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/").build();
        Response res = client.newCall(req).execute();

        Type listCoinMarketCapResponse = Types.newParameterizedType(List.class, CoinMarketCapResponse.class);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<List<CoinMarketCapResponse>> jsonAdapter = moshi.adapter(listCoinMarketCapResponse);

        List<CoinMarketCapResponse> exchange = jsonAdapter.fromJson(res.body().string());
        if(exchange == null)
            return 0;

        for (CoinMarketCapResponse cmr: exchange) {
            if(cmr.symbol == cryptoType.getDenomination()) return cmr.price_usd;
        }

        return 0;
    }
}