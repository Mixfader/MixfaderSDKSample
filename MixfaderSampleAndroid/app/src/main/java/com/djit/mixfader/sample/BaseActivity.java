/*
 * Copyright (C) 2016 Djit SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.djit.mixfader.sample;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Checks for permissions and features state
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Handles bluetooth activation request result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                // Bluetooth not enabled
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Handles permissions and features needed by the app
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Asks for Bluetooth activation is needed
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            final Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // Asks for permissions if needed
        final int checkCoarseLocation = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        final int checkFineLocation = ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        if(checkCoarseLocation == PackageManager.PERMISSION_DENIED && checkFineLocation == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        } else {
            // Ask for location service if needed
            if (needToEnableLocation(this)) {
                final Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(viewIntent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION: {
                // permission denied
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
                // permission was granted
            }
        }
    }

    /**
     * Check if the user needs to enable the location.
     * <p/>
     * Since Android Marshmallow, the location is required to scan for bluetooth devices.
     *
     * @return Return true is the location need to be enable, false otherwise.
     */
    private static boolean needToEnableLocation(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            final LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            return !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                    && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    }

}
