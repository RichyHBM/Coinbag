package uk.co.richyhbm.coinbag.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.records.Wallet;

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
        TextView cointype = (TextView) convertView.findViewById(R.id.cointype);
        TextView value = (TextView) convertView.findViewById(R.id.value);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);

        icon.setImageResource(wallet.getType().getIcon());
        address.setText(wallet.getAddress());
        cointype.setText(wallet.getType().toString());
        value.setText(wallet.getValue());
        balance.setText(wallet.getBalance());

        return convertView;
    }

}
