package tech.ja_ke.dps_bluetooth

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import android.content.Intent
import android.app.Activity






class MainActivity : AppCompatActivity() {

    val bt = BluetoothSPP(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!bt.isBluetoothAvailable) {
            Toast.makeText(this, "Bluetooth not availible!", Toast.LENGTH_SHORT)
            finish();
        }

        if (!bt.isBluetoothEnabled) {
            Toast.makeText(this, "Bluetooth not enabled...", Toast.LENGTH_SHORT)
        }

        bt.startService(BluetoothState.DEVICE_OTHER)
    }

    override fun onStop() {
        super.onStop()
        bt.stopService()
    }

    fun connect_bl(view: Any): Unit {
        Log.i("click", view.toString());
        val intent = Intent(applicationContext, DeviceList::class.java)
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_ANDROID)
                Log.d("BL  Result", data.toString())
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }
}
