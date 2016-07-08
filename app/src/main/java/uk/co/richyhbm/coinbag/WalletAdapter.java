package uk.co.richyhbm.coinbag;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.List;

public class WalletAdapter extends ArrayAdapter<Wallet> {
    public WalletAdapter(Context context, List<Wallet> wallets) {
        super(context, 0, wallets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Wallet wallet = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_item, parent, false);
        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.icon);
        assert icon != null;

        TextView address = (TextView) convertView.findViewById(R.id.address);
        TextView cointype = (TextView) convertView.findViewById(R.id.cointype);
        TextView value = (TextView) convertView.findViewById(R.id.value);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);

        icon.setImageResource(wallet.getType().getIcon());
        address.setText(wallet.address);
        cointype.setText(wallet.getType().toString());
        value.setText("$0");
        balance.setText("12.543");

        return convertView;
    }

}
