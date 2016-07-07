package uk.co.richyhbm.coinbag;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class PhotoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    IntentIntegrator integrator;
    String addressType = CryptoCurrencies.values()[0].name();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan your public key!");
        integrator.setOrientationLocked(true);
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                setupUI(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void setupUI(final String address) {
        setContentView(R.layout.add_account_activity);

        TextView addressPreview = (TextView)findViewById(R.id.address_preview);
        assert addressPreview != null;

        ImageView qrCodePreview = (ImageView)findViewById(R.id.qr_preview);
        assert qrCodePreview != null;

        final Spinner typePreview = (Spinner) findViewById(R.id.type_preview);
        assert typePreview != null;

        addressPreview.setText(address);

        try {
            Bitmap qr = QRGenerator.qrFromString(address);
            qrCodePreview.setImageBitmap(qr);
        } catch (WriterException we) {
            we.printStackTrace();
        }

        List<String> spinnerArray =  new ArrayList<String>();

        CryptoCurrencies[] currencies = CryptoCurrencies.values();
        addressType = currencies[0].name();
        for(CryptoCurrencies cc : currencies) {
            spinnerArray.add(cc.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typePreview.setAdapter(adapter);
        typePreview.setOnItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_new_account);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = null;
                try {
                    Bitmap qr = QRGenerator.qrFromString(address, 256, 256);
                    bytes = QRGenerator.toBytes(qr);
                } catch (WriterException we) {
                    we.printStackTrace();
                }
                Wallet wallet = new Wallet(address, addressType, bytes);
                wallet.save();
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        addressType = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView parent) {
    }
}
