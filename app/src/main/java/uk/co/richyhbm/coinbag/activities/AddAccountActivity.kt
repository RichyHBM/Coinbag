package uk.co.richyhbm.coinbag.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.database.AppDatabase
import uk.co.richyhbm.coinbag.database.entities.Wallet
import uk.co.richyhbm.coinbag.databinding.ActivityAddAccountBinding
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.view_model.AddAccountViewModel

class AddAccountActivity : AppCompatActivity() {
    val viewModel = AddAccountViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAddAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_account)
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()

        viewModel.fabIcon.set(Icons.getIcon(this, CommunityMaterial.Icon.cmd_qrcode_scan, R.color.grey_50, 18))
        viewModel.address.set("")
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
        val wallet = Wallet()
        val cryptoType = Cryptocoins.valueOf(viewModel.supportedCryptoList.get()[viewModel.spinnerSelectedIdx.get()])
        wallet.walletType = cryptoType
        wallet.walletAddress = viewModel.address.get()
        wallet.walletNickName = viewModel.walletName.get()

        AsyncWrap<Unit>( {
            val walletDao =  AppDatabase.get(applicationContext).walletDao()
            if(wallet.walletNickName.isNullOrBlank()) {
                wallet.walletNickName = "%s %d".format(
                        cryptoType.getFriendlyName(),
                        walletDao.loadAllByType(cryptoType).size + 1)
            }
            walletDao.insertAll(wallet)
        }, {
            finish()
        }, { ex:Exception? ->
            Snackbar.make(v, "Failed: " + ex!!.message, Snackbar.LENGTH_LONG).show()
        }).execute()
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
}
