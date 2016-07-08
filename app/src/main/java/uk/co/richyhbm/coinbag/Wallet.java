package uk.co.richyhbm.coinbag;

import android.graphics.Bitmap;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Wallet extends SugarRecord {
    @Unique
    String address;
    int cryptoType;

    public Wallet() {

    }

    public Wallet(String address, CryptoCurrencies type){
        this.address = address;
        this.cryptoType = type.getValue();
    }

    public CryptoCurrencies getType() {
        return CryptoCurrencies.getFromValue(cryptoType);
    }
}
