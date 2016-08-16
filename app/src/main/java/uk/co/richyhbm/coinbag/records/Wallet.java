package uk.co.richyhbm.coinbag.records;

import android.os.AsyncTask;
import android.util.Pair;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;
import uk.co.richyhbm.coinbag.exchanges.Exchange;
import uk.co.richyhbm.coinbag.utils.WalletBalanceValueFetcher;

//Wallet class, uses SugarORM to store in sqlite db
public class Wallet extends SugarRecord {
    //Store the address and type of crypto currency of the wallet
    @Unique
    String address;
    CryptoCurrencies cryptoType;

    @Ignore
    static HashMap<String, String> balances = new HashMap<String, String>();
    @Ignore
    static HashMap<String, String> values = new HashMap<String, String>();

    @Ignore
    private WalletBalanceValueFetcher fetcher = null;

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

    public void setBalance(String balance) {
        String key = cryptoType.getDenomination() + address;
        balances.put(key, balance);
    }

    public void setValues(String value) {
        String key = cryptoType.getDenomination() + address;
        values.put(key, value);
    }

    public String getBalance() {
        String key = cryptoType.getDenomination() + address;
        if(balances.containsKey(key)) {
            return balances.get(key);
        }
        return "Unknown";
    }

    public String getValues() {
        String key = cryptoType.getDenomination() + address;
        if(values.containsKey(key)) {
            return values.get(key);
        }
        return "Unknown";
    }

    public void updateBalanceAndValues() {
        if(fetcher == null)
            fetcher = new WalletBalanceValueFetcher(this);

        if(fetcher.getStatus() != AsyncTask.Status.RUNNING) {
            fetcher.execute(this);
        }
    }

}
