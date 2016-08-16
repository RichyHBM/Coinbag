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
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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

        balanceTextView.setText(wallet.getBalance());
        valueTextView.setText(wallet.getValues());

        return convertView;
    }

}
