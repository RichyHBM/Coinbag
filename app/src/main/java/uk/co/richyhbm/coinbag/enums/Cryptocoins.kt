package uk.co.richyhbm.coinbag.enums

enum class Cryptocoins(val supported:Boolean) {
    Other(false),
    Bitcoin(true),
    Litecoin(false),
    Ethereum(true);

    fun getUriString(address: String): String {
        return this.name.toLowerCase() + "://" + address
    }

    fun getFriendlyName(): String {
        return this.name.replace("_", " ")
    }
}