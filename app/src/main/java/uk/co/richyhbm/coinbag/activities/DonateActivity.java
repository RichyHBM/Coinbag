package uk.co.richyhbm.coinbag.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import uk.co.richyhbm.coinbag.interfaces.IAsyncResult;
import uk.co.richyhbm.coinbag.utils.QRData;
import uk.co.richyhbm.coinbag.utils.QRGenerator;
import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.enums.DonationType;

//Activity for the donations page listing the address and a QR code for the address
public class DonateActivity extends AppCompatActivity implements IAsyncResult<Bitmap>, View.OnClickListener {
    //Identifier for additional information when creating the activity
    public static final String DONATION_TYPE_INTENT_EXTRA = "DONATION_TYPE_INTENT_EXTRA";
    ImageView donationQrCode;
    String address = "";

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

        FloatingActionButton copyButton = (FloatingActionButton) findViewById(R.id.copy_to_clipboard_button);

        copyButton.setOnClickListener(this);
    }

    @Override
    public void processResult(Bitmap result) {
        donationQrCode.setImageBitmap(result);
    }

    @Override
    public void onClick(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("donation address", address);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "Address copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}