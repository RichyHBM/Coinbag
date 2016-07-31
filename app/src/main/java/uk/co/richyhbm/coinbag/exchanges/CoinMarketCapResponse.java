package uk.co.richyhbm.coinbag.exchanges;

import com.squareup.moshi.Json;

public class CoinMarketCapResponse {
    public String id;
    public String name;
    public String symbol;
    public int rank;
    public double price_usd;
}
