package uk.co.richyhbm.coinbag.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import uk.co.richyhbm.coinbag.interfaces.IAsyncResult;
import uk.co.richyhbm.coinbag.utils.QRData;
import uk.co.richyhbm.coinbag.utils.QRGenerator;
import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.enums.DonationType;

//Activity for the donations page listing the address and a QR code for the address
public class DonateActivity extends AppCompatActivity implements IAsyncResult<Bitmap> {
    //Identifier for additional information when creating the activity
    public static final String DONATION_TYPE_INTENT_EXTRA = "DONATION_TYPE_INTENT_EXTRA";
    ImageView donationQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_layout);

        TextView donationType = (TextView)findViewById(R.id.donate_type);
        assert donationType != null;

        TextView donationAddress = (TextView)findViewById(R.id.donate_address);
        assert donationAddress != null;

        donationQrCode = (ImageView)findViewById(R.id.donate_qr_address);
        assert donationQrCode != null;

        String typeDescription = "Donate activity failed";
        String address = "";

        //Fetch the type of donation from the extras field, and set the text and image accordingly
        int donationTypeInt = getIntent().getIntExtra(DONATION_TYPE_INTENT_EXTRA, -1);

        if(donationTypeInt != -1) {
            DonationType ccType = DonationType.values()[donationTypeInt];
            typeDescription = "Donate via " + ccType.toString();
            address = ccType.getAddress();
        }

        donationType.setText(typeDescription);
        donationAddress.setText(address);

        QRGenerator qrGen = new QRGenerator();
        qrGen.asyncresult = this;
        qrGen.execute(new QRData(address));
    }

    @Override
    public void processResult(Bitmap result) {
        donationQrCode.setImageBitmap(result);
    }
}