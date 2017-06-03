package uk.co.richyhbm.coinbag.models

import uk.co.richyhbm.coinbag.enums.Cryptocoins

class Wallet (
        val id: Int,
        val name:String,
        val address:String,
        val type:Cryptocoins
)

