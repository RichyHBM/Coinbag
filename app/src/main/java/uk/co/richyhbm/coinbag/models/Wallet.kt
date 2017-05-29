package uk.co.richyhbm.coinbag.models

import uk.co.richyhbm.coinbag.enums.Cryptocoins

class Wallet (
        val name:String,
        val address:String,
        val type:Cryptocoins
)

