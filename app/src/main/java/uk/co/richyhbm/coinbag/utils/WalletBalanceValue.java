package uk.co.richyhbm.coinbag.utils;

import android.os.AsyncTask;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;
import uk.co.richyhbm.coinbag.exchanges.Exchange;
import uk.co.richyhbm.coinbag.records.Wallet;

public class WalletBalanceValue {
    public String balance = "Fetching";
    public String value = "Fetching";

    public WalletBalanceValue(){}
    public WalletBalanceValue(String b, String v){
        balance = b;
        value = v;
    }
}
