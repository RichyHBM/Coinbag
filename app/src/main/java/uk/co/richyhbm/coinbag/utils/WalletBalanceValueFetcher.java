package uk.co.richyhbm.coinbag.utils;

import android.os.AsyncTask;
import android.util.Pair;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;
import uk.co.richyhbm.coinbag.exchanges.Exchange;
import uk.co.richyhbm.coinbag.records.Wallet;

public class WalletBalanceValueFetcher extends AsyncTask<Wallet, WalletBalanceValueFetcher.WalletBalanceValue, WalletBalanceValueFetcher.WalletBalanceValue> {

    public class WalletBalanceValue {
        public WalletBalanceValue(){}
        public WalletBalanceValue(String b, String v){
            balance = b;
            value = v;
        }

        public String balance = "Unknown";
        public String value = "Unknown";
    }

    private Wallet wallet;
    private DecimalFormat df2;
    private DecimalFormat df5;

    public WalletBalanceValueFetcher(Wallet w){
        wallet = w;
        df2 = new DecimalFormat("#,###.##");
        df5 = new DecimalFormat("#,###.#####");
        df2.setRoundingMode(RoundingMode.HALF_DOWN);
        df5.setRoundingMode(RoundingMode.HALF_DOWN);
    }

    @Override
    protected WalletBalanceValue doInBackground(Wallet... params) {
        Wallet wallet = params[0];
        WalletBalanceValue result = new WalletBalanceValue();
        CryptoCurrencies cryptoType = wallet.getType();
        double balance = 0;

        try {
            if (Balance.balanceFetchers.containsKey(cryptoType)) {
                publishProgress(new WalletBalanceValue("Fetching", "Fetching"));

                double blc = Balance.balanceFetchers.get(cryptoType).getBalanceForAddress(wallet.getAddress());
                if (blc >= 0) balance = blc;
                else return result;
            } else return result;

            result.balance = df5.format(balance) + " " + cryptoType.getDenomination();
        }catch(Exception e){
            e.printStackTrace();
            return new WalletBalanceValue("Error", "Error");
        }

        try {
            if (Exchange.exchangeFetchers.containsKey(cryptoType)) {
                publishProgress(new WalletBalanceValue(result.balance, "Fetching"));
                double usd = Exchange.exchangeFetchers.get(cryptoType).getExchangeForCurrency(cryptoType);
                if (usd >= 0) {
                    double totalValue = (balance * usd);
                    result.value = df2.format(totalValue) + " USD";
                }
            }
            return result;
        }catch(Exception e){
            e.printStackTrace();
            return new WalletBalanceValue(result.balance, "Error");
        }
    }

    @Override
    protected void onProgressUpdate(WalletBalanceValue... result) {
        if(result == null || result.length != 1) {
            String unknown = "Unknown";
            wallet.setBalance(unknown);
            wallet.setValues(unknown);
        }else {
            wallet.setBalance(result[0].balance);
            wallet.setValues(result[0].value);
        }
    }

    @Override
    protected void onPostExecute(WalletBalanceValue result) {
        if(result == null) {
            String unknown = "Unknown";
            wallet.setBalance(unknown);
            wallet.setValues(unknown);
        }else {
            wallet.setBalance(result.balance);
            wallet.setValues(result.value);
        }
    }
}
