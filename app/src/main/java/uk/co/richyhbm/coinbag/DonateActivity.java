package uk.co.richyhbm.coinbag;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

public class DonateActivity extends AppCompatActivity {
    public static final String DONATION_TYPE_INTENT_EXTRA = "DONATION_TYPE_INTENT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_layout);

        TextView donationType = (TextView)findViewById(R.id.donate_type);
        assert donationType != null;

        TextView donationAddress = (TextView)findViewById(R.id.donate_address);
        assert donationAddress != null;

        ImageView donationQrCode = (ImageView)findViewById(R.id.donate_qr_address);
        assert donationQrCode != null;

        String typeDescription = "Donate activity failed";
        String address = "";

        int donationTypeInt = getIntent().getIntExtra(DONATION_TYPE_INTENT_EXTRA, -1);

        if(donationTypeInt != -1) {
            DonationType TYPE = DonationType.values()[donationTypeInt];

            switch (TYPE) {
                case BITCOIN:
                    typeDescription = "Donate via Bitcoin";
                    address = "1234MSEYstWetqTFn5Au";
                    break;
                case LITECOIN:
                    typeDescription = "Donate via Litecoin";
                    address = "5678MSEYstWetqTFn5Au";
                    break;
                case ETHEREUM:
                    typeDescription = "Donate via Ethereum";
                    address = "9010MSEYstWetqTFn5Au";
                    break;
            }
        }

        donationType.setText(typeDescription);
        donationAddress.setText(address);
        try {
            Bitmap qr = QRGenerator.qrFromString(address);
            donationQrCode.setImageBitmap(qr);
        } catch (WriterException we) {
            we.printStackTrace();
        }
    }
}