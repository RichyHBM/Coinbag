package uk.co.richyhbm.coinbag.database.entities

import android.arch.persistence.room.*
import uk.co.richyhbm.coinbag.enums.Cryptocoins

@Entity(tableName = "wallets",
        indices = arrayOf(
            Index(value = "wallet_address", unique = true)
        ))
class Wallet {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wallet_id")
    var walletId: Int = 0

    @ColumnInfo(name = "wallet_nick")
    var walletNickName:String = ""

    @ColumnInfo(name = "wallet_address")
    var walletAddress:String = ""

    @ColumnInfo(name = "wallet_type")
    var walletType: Cryptocoins = Cryptocoins.Other
}