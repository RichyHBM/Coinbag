package uk.co.richyhbm.coinbag.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import uk.co.richyhbm.coinbag.database.entities.Wallet
import uk.co.richyhbm.coinbag.enums.Cryptocoins

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallets")
    fun getAll(): List<Wallet>

    @Query("SELECT * FROM wallets WHERE wallet_id IN (:arg0)")
    fun loadAllByIds(walletIds: IntArray): List<Wallet>

    @Query("SELECT * FROM wallets WHERE wallet_type LIKE :arg0")
    fun loadAllByType(type: Cryptocoins): List<Wallet>

    @Insert
    fun insertAll(vararg wallet: Wallet)

    @Delete
    fun delete(wallet: Wallet)
}