package uk.co.richyhbm.coinbag.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
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

        final String walletAddres= wallet.getAddress();

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

        String calculating = "Calculating";
        balanceTextView.setText(calculating);
        valueTextView.setText(calculating);

        AsyncTask<Wallet, Void, Pair<String, String>> asyncTask = new AsyncTask<Wallet, Void, Pair<String, String>>() {
            @Override
            protected Pair<String, String> doInBackground(Wallet... params) {
                Wallet wallet = params[0];
                try {
                    CryptoCurrencies cryptoType = wallet.getType();
                    double balance = 0;
                    if (Balance.balanceFetchers.containsKey(cryptoType)) {
                        double blc = Balance.balanceFetchers.get(cryptoType).getBalanceForAddress(wallet.getAddress());
                        if (blc >= 0) balance = blc;
                        else return new Pair<>("Unknown", "Unknown");
                    } else return new Pair<>("Unknown", "Unknown");

                    if (Exchange.exchangeFetchers.containsKey(cryptoType)) {
                        double usd = Exchange.exchangeFetchers.get(cryptoType).getExchangeForCurrency(cryptoType);
                        if (usd >= 0) {
                            double totalValue = (balance * usd);
                            DecimalFormat df = new DecimalFormat("#.##");
                            df.setRoundingMode(RoundingMode.HALF_DOWN);

                            return new Pair<>(
                                    balance + " " + cryptoType.getDenomination(),
                                Double.parseDouble(df.format(totalValue)) + " USD");
                        }
                    }

                    return new Pair<>(balance + " " + cryptoType.getDenomination(), "Unknown");
                }catch(Exception e){
                    e.printStackTrace();
                    return new Pair<>("Error", "Error");
                }
            }

            @Override
            protected void onPostExecute(Pair<String, String> result) {
                balanceTextView.setText(result.first);
                valueTextView.setText(result.second);
            }
        };

        asyncTask.execute(wallet);

        return convertView;
    }

}
