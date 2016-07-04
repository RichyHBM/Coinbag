package uk.co.richyhbm.coinbag;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar.*;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

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
        int data[] = {R.id.developed_by, R.id.dm77_barcode_license, R.id.sugar_orm_license};
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

        ImageView litecoinDonation = (ImageView)findViewById(R.id.litecoin_donate);
        if(litecoinDonation != null) {
            litecoinDonation.setOnClickListener(this);
        }

        ImageView ethereumDonation = (ImageView)findViewById(R.id.ethereum_donate);
        if(ethereumDonation != null) {
            ethereumDonation.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bitcoin_donate) {
            Intent intent = new Intent(this, DonateActivity.class);
            intent.putExtra(DonateActivity.DONATION_TYPE_INTENT_EXTRA, DonationType.BITCOIN.ordinal());
            startActivity(intent);
        }
        else if(v.getId() == R.id.litecoin_donate) {
            Intent intent = new Intent(this, DonateActivity.class);
            intent.putExtra(DonateActivity.DONATION_TYPE_INTENT_EXTRA, DonationType.LITECOIN.ordinal());
            startActivity(intent);
        }
        else if(v.getId() == R.id.ethereum_donate) {
            Intent intent = new Intent(this, DonateActivity.class);
            intent.putExtra(DonateActivity.DONATION_TYPE_INTENT_EXTRA, DonationType.ETHEREUM.ordinal());
            startActivity(intent);
        }
    }

}
