package com.example.bleh

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.os.Handler
import android.os.Looper

fun Context.bluetoothAdapter(): BluetoothAdapter =
    (this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

class MainActivity : AppCompatActivity() {
    private val bluetoothAdapter: BluetoothAdapter = bluetoothAdapter()
    private val b : BluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())
    private fun scan() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        val cb = object:ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                // overrides goes here
            }
        }
        if (!scanning) {
            handler.postDelayed({
                scanning = false
                b.stopScan(cb)
            }, 10000)
            scanning = true
            b.startScan(cb)
        }
        else {
            scanning = false
            b.stopScan(cb)
        }
    }
}