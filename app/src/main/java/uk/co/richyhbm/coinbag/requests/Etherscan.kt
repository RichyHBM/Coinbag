package uk.co.richyhbm.coinbag.requests

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object Etherscan {
    interface EtherscanService {
        @GET("/api")
        fun addressBalance(
                @Query("address") address: String,
                @Query("module") module:String = "account",
                @Query("action") action: String = "balance",
                @Query("tag") tag: String = "latest"
        ): Call<EtherscanResponse?>
    }

    fun getBalance(address:String) : Double {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.etherscan.io")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val call = retrofit.create<Etherscan.EtherscanService>(Etherscan.EtherscanService::class.java).addressBalance(address)
        val response = call.execute().body()

        if(response == null) {
            return 0.0
        } else {
            //Turn into ether
            return response.result.subSequence(0, response.result.length - 10).toString().toDouble() / 100000000
        }
    }

    class EtherscanResponse {
        val status: String = ""
        val message: String = ""
        val result: String = ""
    }
}
