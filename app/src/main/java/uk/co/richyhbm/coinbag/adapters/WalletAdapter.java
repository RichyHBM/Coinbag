package uk.co.richyhbm.coinbag.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.balances.Balance;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;
import uk.co.richyhbm.coinbag.exchanges.Exchange;
import uk.co.richyhbm.coinbag.records.Wallet;

//Array adapter for displaying all the wallets saved in the database
public class WalletAdapter extends ArrayAdapter<Wallet> {
    public WalletAdapter(Context context, List<Wallet> wallets) {
        super(context, 0, wallets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Wallet wallet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wallet_item, parent, false);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        assert icon != null;

        TextView address = (TextView) convertView.findViewById(R.id.address);
        assert address != null;

        TextView cointype = (TextView) convertView.findViewById(R.id.cointype);
        assert cointype != null;

        final TextView valueTextView = (TextView) convertView.findViewById(R.id.value);
        assert valueTextView != null;

        final TextView balanceTextView = (TextView) convertView.findViewById(R.id.balance);
        assert balanceTextView != null;

        //Set the default image for that wallets cryptocurrency type, and its information
        icon.setImageResource(wallet.getType().getIcon());
        address.setText(wallet.getAddress());
        cointype.setText(wallet.getType().toString());

        AsyncTask<Wallet, Void, String> balanceAsyncTask = new AsyncTask<Wallet, Void, String>() {
            @Override
            protected String doInBackground(Wallet... params) {
                try{
                    Wallet wallet = params[0];
                    CryptoCurrencies cryptoType = wallet.getType();
                    if(Balance.balanceFetchers.containsKey(cryptoType)) {
                        double blc = Balance.balanceFetchers.get(cryptoType).getBalanceForAddress(wallet.getAddress());
                        if(blc >= 0) return blc + " " + cryptoType.getDenomination();
                        else return "Unknown";
                    }
                    return "Unknown";
                }catch(Exception e){
                    e.printStackTrace();
                    return "Error";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                balanceTextView.setText(result);
            }
        };
        AsyncTask<Wallet, Void, String> valueAsyncTask = new AsyncTask<Wallet, Void, String>() {
            @Override
            protected String doInBackground(Wallet... params) {
                try {
                    Wallet wallet = params[0];
                    CryptoCurrencies cryptoType = wallet.getType();
                    double balance = 0;
                    if (Balance.balanceFetchers.containsKey(cryptoType)) {
                        double blc = Balance.balanceFetchers.get(cryptoType).getBalanceForAddress(wallet.getAddress());
                        if (blc >= 0) balance = blc;
                        else return "Unknown";
                    } else return "Unknown";

                    if (Exchange.exchangeFetchers.containsKey(cryptoType)) {
                        double usd = Exchange.exchangeFetchers.get(cryptoType).getExchangeForCurrency(cryptoType);
                        if (usd >= 0) {
                            double totalValue = (balance * usd);
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setRoundingMode(RoundingMode.HALF_DOWN);

                            return Double.parseDouble(df.format(totalValue)) + " USD";
                        }
                    }

                    return "Unknown";
                }catch(Exception e){
                    e.printStackTrace();
                    return "Error";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                valueTextView.setText(result);
            }
        };

        balanceAsyncTask.execute(wallet);
        valueAsyncTask.execute(wallet);

        return convertView;
    }

}
