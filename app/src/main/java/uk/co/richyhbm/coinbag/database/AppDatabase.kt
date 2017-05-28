package uk.co.richyhbm.coinbag.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.TypeConverters
import android.content.Context
import uk.co.richyhbm.coinbag.database.daos.*
import uk.co.richyhbm.coinbag.database.entities.*

@Database(entities = arrayOf(Wallet::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walletDao(): WalletDao

    companion object Singleton {
        private var database: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if(database == null) {
                database = Room.databaseBuilder(context, AppDatabase::class.java, "wallets-db").build()
            }

            return database!!
        }
    }
}