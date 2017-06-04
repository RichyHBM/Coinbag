package uk.co.richyhbm.coinbag.utils

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.requests.BlockchainInfo
import uk.co.richyhbm.coinbag.requests.Etherscan
import uk.co.richyhbm.coinbag.requests.LtcBlockcypher

object BalanceFetcher {
    fun getBalance(type: Cryptocoins, address:String): Double {
        return when(type) {
            Cryptocoins.Bitcoin -> BlockchainInfo.getBalance(address)
            Cryptocoins.Litecoin -> LtcBlockcypher.getBalance(address)
            Cryptocoins.Ethereum -> Etherscan.getBalance(address)
            else -> 0.0
        }
    }
}
