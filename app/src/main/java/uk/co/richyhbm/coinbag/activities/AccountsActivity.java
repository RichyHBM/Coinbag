package uk.co.richyhbm.coinbag.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uk.co.richyhbm.coinbag.R;
import uk.co.richyhbm.coinbag.records.Wallet;
import uk.co.richyhbm.coinbag.adapters.WalletAdapter;

public class AccountsActivity extends AppCompatActivity {
    WalletAdapter walletAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.accounts_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent photoIntent = new Intent(this, NewWalletActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(photoIntent);
            }
        });


        final Intent receiveIntent = new Intent(this, ReceiveActivity.class);

        List<Wallet> arrayOfWallet = Wallet.listAll(Wallet.class);
        walletAdapter = new WalletAdapter(this, arrayOfWallet);
        ListView listView = (ListView) findViewById(R.id.wallet_list);
        listView.setAdapter(walletAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView address = (TextView) view.findViewById(R.id.address);
                TextView cointype = (TextView) view.findViewById(R.id.cointype);

                receiveIntent.putExtra(ReceiveActivity.ADDRESS_INTENT_EXTRA, address.getText());
                receiveIntent.putExtra(ReceiveActivity.CRYPTO_TYPE_INTENT_EXTRA, cointype.getText());
                startActivity(receiveIntent);
            }
        });
        registerForContextMenu(listView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        resetListView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo)menuInfo;
        Wallet wallet = walletAdapter.getItem(adapterMenuInfo.position);

        menu.setHeaderIcon(wallet.getType().getIcon());
        menu.setHeaderTitle(wallet.getAddress());
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Here's how you can get the correct item in onContextItemSelected()
        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Wallet wallet = walletAdapter.getItem(adapterMenuInfo.position);

        if(item.getTitle().equals("Delete")) {
            new AlertDialog.Builder(this)
                    .setTitle("Please confirm")
                    .setMessage("Are you sure you want to remove address " + wallet.getAddress() + "?")
                    .setIcon(wallet.getType().getIcon())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            wallet.delete();
                            resetListView();
                            Toast.makeText(AccountsActivity.this, "Deleted address", Toast.LENGTH_LONG).show();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();

            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accounts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            resetListView();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetListView() {
        List<Wallet> arrayOfWallet = Wallet.listAll(Wallet.class);
        walletAdapter.clear();
        walletAdapter.addAll(arrayOfWallet);
    }
}
