package uk.co.richyhbm.coinbag.records;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

//Wallet class, uses SugarORM to store in sqlite db
public class Wallet extends SugarRecord {
    //Store the address and type of crypto currency of the wallet
    @Unique
    String address;
    CryptoCurrencies cryptoType;

    public Wallet() {

    }

    public Wallet(String address, CryptoCurrencies type){
        this.address = address;
        this.cryptoType = type;
    }

    public CryptoCurrencies getType() {
        return cryptoType;
    }

    public String getAddress() {
        return address;
    }
}
