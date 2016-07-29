package uk.co.richyhbm.coinbag.enums;

import uk.co.richyhbm.coinbag.R;

//TODO: Remove "value" and just use enum

//Types of crypto currencies that have an image
public enum CryptoCurrencies {
    Other(0, R.drawable.coin_bag, ""),
    Bitcoin(1, R.drawable.btc, "BTC"),
    Litecoin(2, R.drawable.ltc, "LTC"),
    Ethereum(3, R.drawable.eth_alt, "ETH"),
    Dash(4, R.drawable.dash, "DASH");

    //Use a value to assure crypto currency id's don't change, store the image to use with the enum
    private int value;
    private int iconId;
    private String denomination;
    CryptoCurrencies(int i, int icon, String denomination) {
        value = i;
        iconId = icon;
        this.denomination = denomination;
    }

    public int getValue() {
        return value;
    }

    //Converts an int to a crypto currency
    public static CryptoCurrencies getFromValue(int i) {
        for(CryptoCurrencies cc : CryptoCurrencies.values())
        {
            if(cc.getValue() == i)
                return cc;
        }
        throw new IllegalArgumentException("CryptoCurrency " + i + " does not exist");
    }

    //Returns the icon to use for the crypto currency
    public int getIcon() {
        return iconId;
    }

    //Returns the string denomination of the currency
    public String getDenomination() {
        return denomination;
    }
}
