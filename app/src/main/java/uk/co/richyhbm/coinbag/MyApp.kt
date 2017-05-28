package uk.co.richyhbm.coinbag

import android.app.Application
import android.arch.persistence.room.Room
import uk.co.richyhbm.coinbag.database.AppDatabase

class MyApp : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        MyApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "wallets-db").build()
    }
}