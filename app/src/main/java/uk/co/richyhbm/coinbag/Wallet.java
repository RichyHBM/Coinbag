package uk.co.richyhbm.coinbag;

import android.graphics.Bitmap;

import com.orm.SugarRecord;

public class Wallet extends SugarRecord {
    String address;
    String type;
    byte[] qr_code;

    public Wallet() {

    }

    public Wallet(String address, String type, byte[] qr_code){
        this.address = address;
        this.type = type;
        this.qr_code = qr_code;
    }


}
