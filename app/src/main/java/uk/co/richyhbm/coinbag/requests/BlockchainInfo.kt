package uk.co.richyhbm.coinbag.requests

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object BlockchainInfo {
    interface BlockchainInfoService {
        @GET("/q/addressbalance/{address}")
        fun addressBalance(@Path("address") address: String): Call<String?>
    }

    fun getBalance(address:String) : Double {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://blockchain.info")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        val call = retrofit.create<BlockchainInfo.BlockchainInfoService>(BlockchainInfo.BlockchainInfoService::class.java).addressBalance(address)
        val balance = call.execute().body()

        if(balance == null) {
            return 0.0
        } else {
            //Returned amount is satoshi, turn into bitcoin
            return balance.toDouble() / 100000000
        }
    }
}