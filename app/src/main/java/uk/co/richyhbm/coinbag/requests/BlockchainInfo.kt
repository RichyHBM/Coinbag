package uk.co.richyhbm.coinbag.requests

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BlockchainInfo {
    @GET("/q/addressbalance/{address}")
    fun addressBalance(@Path("address") address: String): Call<String>
}