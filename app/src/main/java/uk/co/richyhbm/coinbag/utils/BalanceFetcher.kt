package uk.co.richyhbm.coinbag.utils

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.requests.BlockchainInfo

object BalanceFetcher {
    fun getBalance(type: Cryptocoins, address:String, respond: (String) -> Unit) {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://blockchain.info")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        val call = retrofit.create<BlockchainInfo>(BlockchainInfo::class.java).addressBalance(address)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful()) {
                    Log.d("response", "something right " + response.body())
                    if(response.body() != null)
                        respond(response.body().toString())
                } else {
                    // error response, no access to resource?
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("tag", "something wrong " + t.message)
                // something went completely south (like no internet connection)
            }
        })
    }
}
