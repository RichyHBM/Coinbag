package uk.co.richyhbm.coinbag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
        int qr_code = 0;

        int donationTypeInt = getIntent().getIntExtra(DONATION_TYPE_INTENT_EXTRA, -1);

        if(donationTypeInt != -1) {
            DonationType TYPE = DonationType.values()[donationTypeInt];

            switch (TYPE) {
                case BITCOIN:
                    typeDescription = "Donate via Bitcoin";
                    address = "1234";
                    qr_code = R.drawable.qr;
                    break;
                case LITECOIN:
                    typeDescription = "Donate via Litecoin";
                    address = "5678";
                    qr_code = R.drawable.qr;
                    break;
                case ETHEREUM:
                    typeDescription = "Donate via Ethereum";
                    address = "9012";
                    qr_code = R.drawable.qr;
                    break;
            }
        }

        donationType.setText(typeDescription);
        donationAddress.setText(address);
        donationQrCode.setImageResource(qr_code);
    }
}