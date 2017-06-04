package uk.co.richyhbm.coinbag.requests

import android.util.Log
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object LtcBlockcypher {
    interface LtcBlockcypherService {
        @GET("/v1/ltc/main/addrs/{address}/balance")
        fun addressBalance(@Path("address") address: String, @Query("omitWalletAddresses") omit:Boolean = true): Call<LtcBlockcypherResponse?>
    }

    fun getBalance(address:String) : Double {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.blockcypher.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val call = retrofit.create<LtcBlockcypher.LtcBlockcypherService>(LtcBlockcypher.LtcBlockcypherService::class.java).addressBalance(address)
        val response = call.execute().body()

        if(response == null) {
            return 0.0
        } else {
            //Turn into ether
            return response.balance.toDouble() / 100000000
        }
    }

    class LtcBlockcypherResponse
    {
        val address: String = ""
        val total_received: Long = 0
        val total_sent: Long = 0
        val balance: Long = 0
        val unconfirmed_balance: Long = 0
        val final_balance: Long = 0
        val n_tx: Long = 0
        val unconfirmed_n_tx: Long = 0
        val final_n_tx: Long = 0
    }
}