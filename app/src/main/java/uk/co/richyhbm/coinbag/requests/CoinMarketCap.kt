package uk.co.richyhbm.coinbag.requests

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path



class CoinMarketCap {
    interface CoinMarketCapService {
        @GET("/v1/ticker/")
        fun getTicker(): Call<List<CoinMarketCap>>
    }

    @Json(name = "id")
    val id: String = ""
    @Json(name = "name")
    val name: String = ""
    @Json(name = "symbol")
    val symbol: String = ""
    @Json(name = "rank")
    val rank: String? = ""
    @Json(name = "price_usd")
    val priceUsd: String? = ""
    @Json(name = "price_btc")
    val priceBtc: String? = ""
    @Json(name = "24h_volume_usd")
    val _24hVolume: String? = ""
    @Json(name = "market_cap_usd")
    val marketCapUsd: String? = ""
    @Json(name = "available_supply")
    val avaliableSupply: String? = ""
    @Json(name = "total_supply")
    val totalSupply: String? = ""
    @Json(name = "percent_change_1h")
    val percentChange1h: String? = ""
    @Json(name = "percent_change_24h")
    val percentChange24h: String? = ""
    @Json(name = "percent_change_7d")
    val percentChange7d: String? = ""
    @Json(name = "last_updated")
    val lastUpdated: String? = ""
}