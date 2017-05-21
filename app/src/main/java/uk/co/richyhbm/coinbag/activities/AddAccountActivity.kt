package uk.co.richyhbm.coinbag.activities


import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.enums.Cryptocoins


class AddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        val address = findViewById(R.id.add_acc_address_entry_area) as EditText
        address.text.clear()

        val spinnerArray = ArrayList<String>()
        for(crypto in Cryptocoins.values()) {
            if(crypto.supported) {
                spinnerArray.add(crypto.getFriendlyName())
            }
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = findViewById(R.id.add_acc_type_spinner) as Spinner
        sItems.adapter = adapter

        val fab = findViewById(R.id.scan_fab) as FloatingActionButton

        fab.setImageDrawable(
                IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_qrcode_scan)
                        .color(ContextCompat.getColor(this, R.color.grey_50))
                        .sizeDp(18))

        fab.setOnClickListener( { _: View ->
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt("Scan your public key!")
            integrator.setOrientationLocked(true)
            integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(true)
            integrator.initiateScan()
        })

        val cancelBtn = findViewById(R.id.add_acc_cancel) as Button
        cancelBtn.setOnClickListener { _:View -> finish() }

        val saveBtn = findViewById(R.id.add_acc_save) as Button
        saveBtn.setOnClickListener { v:View ->
            Snackbar.make(v, "Save button pressed!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val address = findViewById(R.id.add_acc_address_entry_area) as EditText
                address.text.clear()
                address.setText(result.contents.toCharArray(), 0, result.contents.length)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
