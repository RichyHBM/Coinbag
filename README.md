Coin Bag
========

Coin Bag is a read-only cryptocurrency wallet manager allowing users to view and receive cryptocurrency payments.

Coin Bag is read only, it only stores your wallet public keys and thus can't make payments.

## Adding a new type of crypto currency

1. Add a new entry into the `enums/Cryptocoins.kt` enum, setting the currency symbol string:
	```
	enum class Cryptocoins(val supported:Boolean, val symbol: String) {
    	...
    	Litecoin(true, "LTC"),
    	...
    }
    ```

2. Create a new mapping for your new cryto enum in `getCryptoIcon` in the `utils/Icons.kt` file, this mapping should map you new crypto type to an icon within the `CryptocoinsIcons` set

	```
	fun getCryptoIcon(type: Cryptocoins): IIcon {
        when(type) {
            ...
            Cryptocoins.Litecoin -> return CryptocoinsIcons.Icon.cci_LTC
            ...
        }
    }
    ```

3. Create a new retrofit style interface for retreiving the balance of a given address
	```
	interface LtcBlockcypherService {
        @GET("/v1/ltc/main/addrs/{address}/balance")
        fun addressBalance(@Path("address") address: String, @Query("omitWalletAddresses") omit:Boolean = true): Call<LtcBlockcypherResponse?>
    }
	```

4. Optionally, if the endpoint returns a json datastructure, you may want to create a class to deserialise the json
	```
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
	```

6. Create a function for retreiving the balance using the previously created retrofit interface
	```
	object LtcBlockcypher {
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
	            //Turn into ltc
	            return response.balance.toDouble() / 100000000
	        }
	    }
	}
	```

6. Add a new entry into `utils/BalanceFetcher.kt` to return the amount of crypto held in the address
	```
	object BalanceFetcher {
	    fun getBalance(type: Cryptocoins, address:String): Double {
	        return when(type) {
	            ...
	            Cryptocoins.Litecoin -> LtcBlockcypher.getBalance(address)
	            ...
	            else -> 0.0
	        }
	    }
	}
	```