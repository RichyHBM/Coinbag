package uk.co.richyhbm.coinbag.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.hardware.Camera
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import io.realm.Realm
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.databinding.ActivityAddAccountBinding
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.models.Wallet
import uk.co.richyhbm.coinbag.realm.RealmWallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.view_model.AddAccountViewModel

class EditAccountActivity : AppCompatActivity() {
    companion object {
        val EDIT_WALLET_ID = "EditWalletId"
    }

    val viewModel = AddAccountViewModel()
    var walletId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Edit wallet"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        assert(!intent.extras.containsKey(EDIT_WALLET_ID), {"Edit wallet only available when there is a wallet to edit"})
        val binding: ActivityAddAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_account)
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()
        viewModel.fabIcon.set(Icons.getIcon(this, CommunityMaterial.Icon.cmd_qrcode_scan, R.color.grey_50, 18))
        viewModel.supportedCryptoList.set(Cryptocoins.values().filter { it.supported }.map { it.name }.toTypedArray())
        val spinner = findViewById(R.id.add_acc_type_spinner) as Spinner

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel.cryptoIconVisible.set(true)
                viewModel.spinnerSelectedIdx.set(position)
                val crypto = Cryptocoins.valueOf(viewModel.supportedCryptoList.get()[position])
                viewModel.cryptoIcon.set(Icons.getIcon(view.context, Icons.getCryptoIcon(crypto), R.color.grey_700, 72))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                viewModel.cryptoIconVisible.set(false)
            }
        }

        walletId = this.intent.extras.get(EDIT_WALLET_ID) as Int

        val asyncTask = object : AsyncTask<Void, Void, Wallet>() {
            override fun doInBackground(vararg params: Void?): Wallet? {
                val realm = Realm.getDefaultInstance()
                val realmWallet = realm.where(RealmWallet::class.java).equalTo("walletId", walletId).findFirst()
                var wallet:Wallet? = null

                if(realmWallet != null) {
                    wallet = Wallet(
                            realmWallet.walletId,
                            realmWallet.walletNickName,
                            realmWallet.walletAddress,
                            Cryptocoins.valueOf(realmWallet.walletType)
                    )
                } else

                realm.close()
                return wallet
            }

            override fun onPostExecute(wallet:Wallet?) {
                if(wallet != null) {
                    viewModel.walletName.set(wallet.name)
                    viewModel.address.set(wallet.address)
                    viewModel.walletName.set(wallet.name)
                    val spinnerIndex = viewModel.supportedCryptoList.get().indexOfFirst { it == wallet.type.getFriendlyName() }
                    spinner.setSelection(spinnerIndex)
                    viewModel.spinnerSelectedIdx.set(spinnerIndex)
                } else {
                    finish()
                }
            }
        }

        asyncTask.execute()
    }

    fun onScanFabClicked(v: View) {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
        integrator.setPrompt("Scan your public key!")
        integrator.setOrientationLocked(true)
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    fun onCancelButtonClick(v: View) {
        finish()
    }

    fun onSaveButtonClick(v: View) {
        val cryptoType = Cryptocoins.valueOf(viewModel.supportedCryptoList.get()[viewModel.spinnerSelectedIdx.get()])

        val wallet = Wallet(
                walletId!!,
                viewModel.walletName.get(),
                viewModel.address.get(),
                cryptoType
        )

        val asyncTask = object : AsyncTask<Wallet, Void, Unit>() {
            override fun doInBackground(vararg params: Wallet): Unit {
                val walletModel = params.first()
                val realm = Realm.getDefaultInstance()
                val walletTypeCount = realm.where(RealmWallet::class.java).equalTo("walletType", walletModel.type.getFriendlyName()).findAll().size
                realm.beginTransaction()
                val realmWallet = realm.where(RealmWallet::class.java).equalTo("walletId", walletModel.id).findFirst()
                realmWallet.walletType = walletModel.type.getFriendlyName()
                realmWallet.walletAddress = walletModel.address

                if(!walletModel.name.isNullOrBlank()) {
                    realmWallet.walletNickName = walletModel.name
                } else {
                    realmWallet.walletNickName = walletModel.type.getFriendlyName() + " " + (walletTypeCount + 1)
                }
                realm.commitTransaction()
                realm.close()
            }

            override fun onPostExecute(u:Unit) {
                finish()
            }
        }

        asyncTask.execute(wallet)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                viewModel.address.set(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_menu -> {
                AsyncWrap({
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    realm.where(RealmWallet::class.java).equalTo("walletId", walletId!!).findFirst().deleteFromRealm()
                    realm.commitTransaction()
                    realm.close()
                }, {
                    finish()
                }).execute()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
