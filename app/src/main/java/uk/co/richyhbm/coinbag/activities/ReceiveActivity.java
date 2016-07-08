package uk.co.richyhbm.coinbag.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import uk.co.richyhbm.coinbag.utils.QRGenerator;
import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

public class ReceiveActivity extends AppCompatActivity {
    public static final String ADDRESS_INTENT_EXTRA = "ADDRESS_INTENT_EXTRA";
    public static final String CRYPTO_TYPE_INTENT_EXTRA = "CRYPTO_TYPE_INTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);

        String address = getIntent().getStringExtra(ADDRESS_INTENT_EXTRA);
        CryptoCurrencies ccType = CryptoCurrencies.valueOf(getIntent().getStringExtra(CRYPTO_TYPE_INTENT_EXTRA));

        TextView receiveTitle = (TextView)findViewById(R.id.receive_type);
        TextView receiveAddress = (TextView)findViewById(R.id.receive_address);
        ImageView receiveQrCode = (ImageView)findViewById(R.id.receive_qr_address);

        String desc = "Receive " + ccType.toString();
        receiveTitle.setText(desc);
        receiveAddress.setText(address);

        try {
            Bitmap qr = QRGenerator.qrFromString(address);
            receiveQrCode.setImageBitmap(qr);
        } catch (WriterException we) {
            we.printStackTrace();
        }
    }
}
