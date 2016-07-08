package uk.co.richyhbm.coinbag.enums;

import uk.co.richyhbm.coinbag.R;

public enum CryptoCurrencies {
    Bitcoin(0),
    Litecoin(1),
    Ethereum(2),
    Dash(3),
    Other(999);

    private int value;
    CryptoCurrencies(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }

    public static CryptoCurrencies getFromValue(int i) {
        for(CryptoCurrencies cc : CryptoCurrencies.values())
        {
            if(cc.getValue() == i)
                return cc;
        }
        throw new IllegalArgumentException("CryptoCurrency " + i + " does not exist");
    }

    public int getIcon() {
        switch (this) {
            case Bitcoin:
                return R.drawable.btc;
            case Litecoin:
                return R.drawable.ltc;
            case Ethereum:
                return R.drawable.eth_alt;
            case Dash:
                return R.drawable.dash;
            case Other:
            default:
                return R.drawable.coin_bag;
        }
    }
}
