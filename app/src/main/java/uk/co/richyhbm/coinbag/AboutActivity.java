package uk.co.richyhbm.coinbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        //Elements that have a hyper link in them
        int data[] = {R.id.developed_by, R.id.dm77_barcode_license, R.id.sugar_orm_license};
        for (int aData : data) {
            TextView text = (TextView) findViewById(aData);
            if(text != null)
                text.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
