package uk.co.richyhbm.coinbag

import android.app.Application
import android.widget.Toast
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.requests.CoinMarketCap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val scheduler = Executors.newSingleThreadScheduledExecutor()

        scheduler.scheduleAtFixedRate({
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.coinmarketcap.com")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

            val service = retrofit.create<CoinMarketCap.CoinMarketCapService>(CoinMarketCap.CoinMarketCapService::class.java)
            val call = service.getTicker()
            val ticker = call.execute().body()

            if(ticker != null) {
                val cap = ticker.associateBy ({it.symbol}, {
                    if(it.priceUsd != null) it.priceUsd.toFloat()
                    else 0.0f
                })
                for (crypt in Cryptocoins.values()) {
                    if (crypt.supported && cap.containsKey(crypt.symbol)) {
                        crypt.setValue(cap[crypt.symbol]!!)
                    }
                }
            }else {
                Toast.makeText(this, "Unable to get crypto values", Toast.LENGTH_LONG).show()
            }
        }, 0, 1, TimeUnit.MINUTES)
    }
}