package uk.co.richyhbm.coinbag.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.enums.DonationType;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        initVersion();
        initHyperlinks();
        initImageViews();
    }

    private void initVersion() {
        String versionName = "Version: 0.9";
        try {
            versionName = "Version: " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView versionText = (TextView) findViewById(R.id.app_version);
        if(versionText != null) {

            versionText.setText(versionName);
        }
    }

    private void initHyperlinks() {
        //Elements that have a hyper link in them
        int data[] = {
                R.id.developed_by,
                R.id.ja_bc_license,
                R.id.zxing_license,
                R.id.sugar_orm_license,
                R.id.coin_bag_icon_license,
                R.id.cryptocoin_icon_license,
                R.id.okhttp_license,
                R.id.moshi_license
        };

        for (int aData : data) {
            TextView text = (TextView) findViewById(aData);
            if(text != null) {
                text.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    private void initImageViews() {
        //Set event handler for donation buttons
        ImageView bitcoinDonation = (ImageView)findViewById(R.id.bitcoin_donate);
        if(bitcoinDonation != null) {
            bitcoinDonation.setOnClickListener(this);
        }

        ImageView ethereumDonation = (ImageView)findViewById(R.id.ethereum_donate);
        if(ethereumDonation != null) {
            ethereumDonation.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bitcoin_donate || v.getId() == R.id.ethereum_donate ){
            DonationType donationType = DonationType.BITCOIN;
            if(v.getId() == R.id.bitcoin_donate) donationType = DonationType.BITCOIN;
            else if(v.getId() == R.id.ethereum_donate) donationType = DonationType.ETHEREUM;

            Intent donateIntent = new Intent("android.intent.action.VIEW", Uri.parse(donationType.getDonationUrl()));
            if(this.getPackageManager().queryIntentActivities(donateIntent,PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                startActivity(donateIntent);
            } else {
                Intent intent = new Intent(this, DonateActivity.class);
                intent.putExtra(DonateActivity.DONATION_TYPE_INTENT_EXTRA, donationType.getValue());
                startActivity(intent);
            }
        }
    }
}
