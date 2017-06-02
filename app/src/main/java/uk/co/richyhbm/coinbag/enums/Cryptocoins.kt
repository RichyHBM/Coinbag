package uk.co.richyhbm.coinbag.enums

import java.util.concurrent.atomic.AtomicInteger
import java.lang.Float.*

enum class Cryptocoins(val supported:Boolean, val symbol: String) {
    Other(false, ""),
    Bitcoin(true, "BTC"),
    Litecoin(false, "LTC"),
    Ethereum(true, "ETH");

    val value = AtomicInteger(0)

    fun setValue(v: Float) {
        value.set(floatToIntBits(v))
    }

    fun getValue(): Float {
        return intBitsToFloat(value.get())
    }

    fun getUriString(address: String): String {
        return this.name.toLowerCase() + "://" + address
    }

    fun getFriendlyName(): String {
        return this.name.replace("_", " ")
    }
}