package uk.co.richyhbm.coinbag.database

import android.arch.persistence.room.TypeConverter
import uk.co.richyhbm.coinbag.enums.Cryptocoins

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromCryptocoin(crypto: Cryptocoins): String {
        return crypto.getFriendlyName()
    }

    @TypeConverter
    @JvmStatic
    fun toCryptocoin(crypto: String): Cryptocoins {
        return Cryptocoins.valueOf(crypto)
    }
}