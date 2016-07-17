package uk.co.richyhbm.coinbag.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import uk.co.richyhbm.coinbag.utils.QRGenerator;
import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.records.Wallet;
import uk.co.richyhbm.coinbag.enums.CryptoCurrencies;

//Activity for registering and adding a new wallet
public class NewWalletActivity extends AppCompatActivity {
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //On creation of this activity, open the camera and parse a qr code
        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan your public key!");
        integrator.setOrientationLocked(true);
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    // When a QR code is parsed, process the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {
                //Setup the UI with the parsed address
                setupUI(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void setupUI(final String address) {
        setContentView(R.layout.add_account_layout);

        TextView addressPreview = (TextView)findViewById(R.id.address_preview);
        assert addressPreview != null;

        ImageView qrCodePreview = (ImageView)findViewById(R.id.qr_preview);
        assert qrCodePreview != null;

        final Spinner typePreview = (Spinner) findViewById(R.id.type_preview);
        assert typePreview != null;

        addressPreview.setText(address);
        //Create a qr image from the address
        try {
            Bitmap qr = QRGenerator.qrFromString(address);
            qrCodePreview.setImageBitmap(qr);
        } catch (WriterException we) {
            we.printStackTrace();
        }
        //Populate the dropdown of possible crypto currencies
        List<String> spinnerArray =  new ArrayList<String>();

        for(CryptoCurrencies cc : CryptoCurrencies.values()) {
            spinnerArray.add(cc.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typePreview.setAdapter(adapter);
        //Add the save floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_new_account);
        assert fab != null;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CryptoCurrencies cc = CryptoCurrencies.valueOf(typePreview.getSelectedItem().toString());
                Wallet wallet = new Wallet(address, cc);
                wallet.save();
                finish();
            }
        });
    }
}
