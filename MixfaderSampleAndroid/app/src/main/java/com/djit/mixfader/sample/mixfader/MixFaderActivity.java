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

package com.djit.mixfader.sample.mixfader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.sample.BaseActivity;
import com.djit.mixfader.sample.MixFaderProvider;
import com.djit.mixfader.sample.R;
import com.djit.mixfader.sample.scan.ScanActivity;

public class MixFaderActivity extends BaseActivity implements MixFaderMvp.View {

    /**
     * Default {@link MixFaderCrossFaderView} progress.
     */
    public static final float DEFAULT_PROGRESS = 0.5f;

    public static void startForMixFader(Context context, MixFaderInterface mixFaderInterface) {
        final Intent intent = new Intent(context, MixFaderActivity.class);
        intent.putExtra(ScanActivity.EXTRA_MIXFADER_NAME, mixFaderInterface.getName().hashCode());

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    /**
     * The {@link MixFaderMvp.Presenter} instance to
     * deal with {@link MixFaderInterface} in the end.
     */
    private MixFader mixFader;

    /**
     * Connection UI
     */
    private Button connectButton, disconnectButton;
    private AppCompatCheckedTextView connectionCheckedTextView;

    /**
     * Notification UI
     */
    private TextView notificationsTextView, orientationTextView;
    private Switch notificationsSwitch, orientationSwitch;
    private MixFaderCrossFaderView mixFaderCrossFaderView;

    /**
     * Information UI
     */
    private TextView serialTitleTextView, serialTextView, softTitleTextView, softTextView, hardTitleTextView, hardTextView;

    /**
     * Rssi UI
     */
    private TextView rssiTitleTextView, rssiTextView, rssiLevelTitleTextView, rssiLevelTextView;

    /**
     * Statistics UI
     */
    private TextView crossfaderTitleTextView, crossfaderTextView, buttonTitleTextView, buttonTextView, batteryTitleTextView, batteryTextView, connectionTitleTextView, connectionTextView;

    /**
     * States UI
     */
    private TextView buttonStateTextView, chargingStateTextView;
    private Switch buttonStateSwitch, chargingStateSwitch;

    /**
     * Blink UI
     */
    private TextView blinkTextView;
    private Button blinkButton;

    /**
     * Calibration UI
     */
    private Button calibStartButton, calibCancelButton, calibResetButton;
    private TextView calibTitleTextView, calibStateTextView;

    /**
     * Name UI
     */
    private TextView nameTitleTextView, nameTextView, nameEditTitleTextView;
    private EditText nameEditEditText;
    private Button nameEditButton;

    /**
     * Color UI
     */
    private TextView colorTitleTextView, colorTextView, colorEditTitleTextView;
    private Spinner colorEditSpinner;
    private Button colorEditButton;

    /**
     * A {@link Handler} to post on the UI thread.
     */
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixfader);

        /**
         * Retrieves MixfaderInterface from Intent
         */
        ensureSaneExtras(getIntent().getExtras());
        final int mixfaderHashcode = getIntent().getIntExtra(ScanActivity.EXTRA_MIXFADER_NAME, -1);
        final MixFaderInterface mixFaderInterface = MixFaderProvider.getInstance().getDevice(mixfaderHashcode);
        mixFader = new MixFader(this, mixFaderInterface);

        /**
         * Retrieves views from XML
         */
        bindsUI();

        /**
         * Inits the different UI parts
         */
        // inits connection part
        connectButton.setOnClickListener(onClickListener);
        disconnectButton.setOnClickListener(onClickListener);

        // inits notification part
        mixFaderCrossFaderView.setProgress(DEFAULT_PROGRESS);
        notificationsSwitch.setOnCheckedChangeListener(onSwitchCheckedChangedListener);
        orientationSwitch.setOnCheckedChangeListener(onSwitchCheckedChangedListener);

        // inits information part
        serialTextView.setText(mixFaderInterface.getSerialNumber());
        softTextView.setText(String.valueOf(mixFaderInterface.getSoftwareVersion()));
        hardTextView.setText(String.valueOf(mixFaderInterface.getHardwareVersion()));

        // inits rssi part
        rssiTextView.setText(String.valueOf(mixFaderInterface.getRssi()));
        rssiLevelTextView.setText(SampleUtils.getRssiLevelString(mixFaderInterface.getRssiLevel()));

        // inits stats part
        crossfaderTextView.setText(String.valueOf(mixFaderInterface.getCrossfaderCyclesCount()));
        buttonTextView.setText(String.valueOf(mixFaderInterface.getButtonClicksCount()));
        batteryTextView.setText(String.valueOf(mixFaderInterface.getBatteryCyclesCount()));
        connectionTextView.setText(String.valueOf(mixFaderInterface.getConnectionCount()));

        // inits states part
        buttonStateSwitch.setChecked(mixFaderInterface.isButtonPushed());
        chargingStateSwitch.setChecked(mixFaderInterface.isCharging());

        // inits blink part
        blinkButton.setOnClickListener(onClickListener);

        // init calibration part
        calibStartButton.setOnClickListener(onClickListener);
        calibCancelButton.setOnClickListener(onClickListener);
        calibResetButton.setOnClickListener(onClickListener);

        // inits name part
        nameTextView.setText(mixFaderInterface.getName());
        nameEditButton.setOnClickListener(onClickListener);

        // inits color part
        colorTextView.setText(SampleUtils.getColorString(mixFaderInterface.getColor()));
        colorEditButton.setOnClickListener(onClickListener);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mxf_hex_color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorEditSpinner.setAdapter(adapter);

        /**
         * Sets global UI as disconnected
         */
        setupDisconnectedUI();
    }

    /**
     * Ensure that all the required extras are present.
     *
     * @param extras the extra {@link Bundle} to check.
     */
    private void ensureSaneExtras(Bundle extras) {
        if (extras == null
                || !extras.containsKey(ScanActivity.EXTRA_MIXFADER_NAME)) {
            throw new IllegalArgumentException("Missing extras. Please use the MixFaderActivity#startForMixFader() method");
        }
    }

    @Override
    protected void onPause() {
        mixFader.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mixFader.onResume();
    }

    /**
     * Retrieves the UI components from XML
     */
    private void bindsUI() {
        connectButton = (Button) findViewById(R.id.mxf_connect_bt);
        disconnectButton = (Button) findViewById(R.id.mxf_disconnect_bt);
        connectionCheckedTextView = (AppCompatCheckedTextView) findViewById(R.id.mxf_connection_ctv);

        mixFaderCrossFaderView = (MixFaderCrossFaderView) findViewById(R.id.mxf_mixfader_view);
        notificationsSwitch = (Switch) findViewById(R.id.mxf_notifications_switch);
        orientationSwitch = (Switch) findViewById(R.id.mxf_orientation_switch);
        notificationsTextView = (TextView) findViewById(R.id.mxf_notifications_tv);
        orientationTextView = (TextView) findViewById(R.id.mxf_orientation_tv);

        serialTitleTextView = (TextView) findViewById(R.id.mxf_info_serial_title_tv);
        serialTextView = (TextView) findViewById(R.id.mxf_info_serial_tv);
        softTitleTextView = (TextView) findViewById(R.id.mxf_info_soft_title_tv);
        softTextView = (TextView) findViewById(R.id.mxf_info_soft_tv);
        hardTitleTextView = (TextView) findViewById(R.id.mxf_info_hard_title_tv);
        hardTextView = (TextView) findViewById(R.id.mxf_info_hard_tv);

        rssiTitleTextView = (TextView) findViewById(R.id.mxf_info_rssi_title_tv);
        rssiTextView = (TextView) findViewById(R.id.mxf_rssi_tv);
        rssiLevelTitleTextView = (TextView) findViewById(R.id.mxf_rssi_level_title_tv);
        rssiLevelTextView = (TextView) findViewById(R.id.mxf_rssi_level_tv);

        crossfaderTitleTextView = (TextView) findViewById(R.id.mxf_stat_crossfader_title_tv);
        crossfaderTextView = (TextView) findViewById(R.id.mxf_stat_crossfader_tv);
        buttonTitleTextView = (TextView) findViewById(R.id.mxf_stat_button_title_tv);
        buttonTextView = (TextView) findViewById(R.id.mxf_stat_button_tv);
        batteryTitleTextView = (TextView) findViewById(R.id.mxf_stat_battery_title_tv);
        batteryTextView = (TextView) findViewById(R.id.mxf_stat_battery_tv);
        connectionTitleTextView = (TextView) findViewById(R.id.mxf_stat_connection_title_tv);
        connectionTextView = (TextView) findViewById(R.id.mxf_stat_connection_tv);

        buttonStateTextView = (TextView) findViewById(R.id.mxf_states_button_tv);
        chargingStateTextView = (TextView) findViewById(R.id.mxf_states_charging_tv);
        buttonStateSwitch = (Switch) findViewById(R.id.mxf_states_button_switch);
        chargingStateSwitch = (Switch) findViewById(R.id.mxf_states_charging_switch);

        blinkTextView = (TextView) findViewById(R.id.mxf_blink_tv);
        blinkButton = (Button) findViewById(R.id.mxf_blink_bt);

        calibStartButton = (Button) findViewById(R.id.mxf_calib_start_bt);
        calibCancelButton = (Button) findViewById(R.id.mxf_calib_cancel_bt);
        calibResetButton = (Button) findViewById(R.id.mxf_calib_reset_bt);
        calibTitleTextView = (TextView) findViewById(R.id.mxf_calib_tv);
        calibStateTextView = (TextView) findViewById(R.id.mxf_calib_state_tv);

        nameTitleTextView = (TextView) findViewById(R.id.mxf_name_title_tv);
        nameTextView = (TextView) findViewById(R.id.mxf_name_tv);
        nameEditTitleTextView = (TextView) findViewById(R.id.mxf_name_edit_title_tv);
        nameEditEditText = (EditText) findViewById(R.id.mxf_name_edit_et);
        nameEditButton = (Button) findViewById(R.id.mxf_name_edit_bt);

        colorTitleTextView = (TextView) findViewById(R.id.mxf_color_title_tv);
        colorTextView = (TextView) findViewById(R.id.mxf_color_tv);
        colorEditTitleTextView = (TextView) findViewById(R.id.mxf_color_edit_title_tv);
        colorEditSpinner = (Spinner) findViewById(R.id.mxf_color_edit_spinner);
        colorEditButton = (Button) findViewById(R.id.mxf_color_edit_bt);
    }

    /**
     * Enables all the UI as a MixFader is connected
     */
    @Override
    public void setupConnectedUI() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                connectionCheckedTextView.setEnabled(true);
                notificationsSwitch.setEnabled(true);
                connectButton.setText(getResources().getString(R.string.mxf_connect));
                disconnectButton.setEnabled(true);
                mixFaderCrossFaderView.setEnabled(true);
                notificationsTextView.setEnabled(true);
                orientationTextView.setEnabled(true);
                serialTitleTextView.setEnabled(true);
                serialTextView.setEnabled(true);
                softTitleTextView.setEnabled(true);
                softTextView.setEnabled(true);
                hardTitleTextView.setEnabled(true);
                hardTextView.setEnabled(true);
                rssiTitleTextView.setEnabled(true);
                rssiTextView.setEnabled(true);
                rssiLevelTitleTextView.setEnabled(true);
                rssiLevelTextView.setEnabled(true);
                crossfaderTitleTextView.setEnabled(true);
                crossfaderTextView.setEnabled(true);
                buttonTitleTextView.setEnabled(true);
                buttonTextView.setEnabled(true);
                batteryTitleTextView.setEnabled(true);
                batteryTextView.setEnabled(true);
                connectionTitleTextView.setEnabled(true);
                connectionTextView.setEnabled(true);
                buttonStateTextView.setEnabled(true);
                chargingStateTextView.setEnabled(true);
                buttonStateSwitch.setEnabled(true);
                chargingStateSwitch.setEnabled(true);
                blinkTextView.setEnabled(true);
                blinkButton.setEnabled(true);
                calibStartButton.setEnabled(true);
                calibCancelButton.setEnabled(true);
                calibResetButton.setEnabled(true);
                calibTitleTextView.setEnabled(true);
                calibStateTextView.setEnabled(true);
                nameTitleTextView.setEnabled(true);
                nameTextView.setEnabled(true);
                nameEditTitleTextView.setEnabled(true);
                nameEditEditText.setEnabled(true);
                nameEditButton.setEnabled(true);
                colorTitleTextView.setEnabled(true);
                colorTextView.setEnabled(true);
                colorEditTitleTextView.setEnabled(true);
                colorEditSpinner.setEnabled(true);
                colorEditButton.setEnabled(true);

                connectionCheckedTextView.setChecked(true);
                mixFaderCrossFaderView.setProgress(DEFAULT_PROGRESS);
            }
        });
    }

    /**
     * Disables all the UI
     */
    @Override
    public void setupDisconnectedUI() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                connectionCheckedTextView.setEnabled(false);
                notificationsSwitch.setEnabled(false);
                orientationSwitch.setEnabled(false);
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                mixFaderCrossFaderView.setEnabled(false);
                notificationsTextView.setEnabled(false);
                orientationTextView.setEnabled(false);
                serialTitleTextView.setEnabled(false);
                serialTextView.setEnabled(false);
                softTitleTextView.setEnabled(false);
                softTextView.setEnabled(false);
                hardTitleTextView.setEnabled(false);
                hardTextView.setEnabled(false);
                rssiTitleTextView.setEnabled(false);
                rssiTextView.setEnabled(false);
                rssiLevelTitleTextView.setEnabled(false);
                rssiLevelTextView.setEnabled(false);
                crossfaderTitleTextView.setEnabled(false);
                crossfaderTextView.setEnabled(false);
                buttonTitleTextView.setEnabled(false);
                buttonTextView.setEnabled(false);
                batteryTitleTextView.setEnabled(false);
                batteryTextView.setEnabled(false);
                connectionTitleTextView.setEnabled(false);
                connectionTextView.setEnabled(false);
                buttonStateTextView.setEnabled(false);
                chargingStateTextView.setEnabled(false);
                buttonStateSwitch.setEnabled(false);
                chargingStateSwitch.setEnabled(false);
                blinkTextView.setEnabled(false);
                blinkButton.setEnabled(false);
                calibStartButton.setEnabled(false);
                calibCancelButton.setEnabled(false);
                calibResetButton.setEnabled(false);
                calibTitleTextView.setEnabled(false);
                calibStateTextView.setEnabled(false);
                nameTitleTextView.setEnabled(false);
                nameTextView.setEnabled(false);
                nameEditTitleTextView.setEnabled(false);
                nameEditEditText.setEnabled(false);
                nameEditButton.setEnabled(false);
                colorTitleTextView.setEnabled(false);
                colorTextView.setEnabled(false);
                colorEditTitleTextView.setEnabled(false);
                colorEditSpinner.setEnabled(false);
                colorEditButton.setEnabled(false);

                connectionCheckedTextView.setChecked(false);
                notificationsSwitch.setChecked(false);
                orientationSwitch.setChecked(false);
                buttonStateSwitch.setChecked(false);
                chargingStateSwitch.setChecked(false);
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int viewId = v.getId();
            switch (viewId) {
                case R.id.mxf_connect_bt:
                    mixFader.connect();
                    break;
                case R.id.mxf_disconnect_bt:
                    mixFader.disconnect();
                    break;
                case R.id.mxf_blink_bt:
                    mixFader.blinkLed();
                    break;
                case R.id.mxf_calib_start_bt:
                    mixFader.startCalibration();
                    break;
                case R.id.mxf_calib_cancel_bt:
                    mixFader.cancelCalibration();
                    break;
                case R.id.mxf_calib_reset_bt:
                    mixFader.resetCalibration();
                    break;
                case R.id.mxf_name_edit_bt:
                    mixFader.rename(nameEditEditText.getText().toString());
                    break;
                case R.id.mxf_color_edit_bt:
                    mixFader.setColor((String) colorEditSpinner.getSelectedItem());
                    break;
                default:
                    break;
            }
        }
    };

    private Switch.OnCheckedChangeListener onSwitchCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.mxf_notifications_switch:
                    mixFader.setCrossfaderNotificationEnable(isChecked);
                    break;
                case R.id.mxf_orientation_switch:
                    mixFader.setCrossfaderOrientationReverse(isChecked);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void updateCrossfaderNotificationStatus(final boolean enable) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!enable && orientationSwitch.isChecked()) {
                    orientationSwitch.setChecked(false);
                }
                orientationSwitch.setEnabled(enable);
            }
        });
    }

    @Override
    public void updateButtonState(final boolean isPushed) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                buttonStateSwitch.setChecked(isPushed);
            }
        });
    }

    @Override
    public void updateBatteryState(final boolean isCharging) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                chargingStateSwitch.setChecked(isCharging);
            }
        });
    }

    @Override
    public void updateRssiValues(final String rssi, final String rssiLevel) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                rssiTextView.setText(rssi);
                rssiLevelTextView.setText(rssiLevel);
            }
        });
    }

    @Override
    public void updateCalibrationState(final String newState) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                calibStateTextView.setText(newState);
            }
        });
    }

    @Override
    public void updateCrossfaderProgress(final float crossfaderValue) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                mixFaderCrossFaderView.setProgress(crossfaderValue);
            }
        });
    }

    @Override
    public void updateStats(final String crossfaderCyclesCount, final String buttonClicksCount, final String batteryCyclesCount, final String connectionsCount) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                crossfaderTextView.setText(crossfaderCyclesCount);
                buttonTextView.setText(buttonClicksCount);
                batteryTextView.setText(batteryCyclesCount);
                connectionTextView.setText(connectionsCount);
            }
        });
    }

    @Override
    public void updateName(final String name) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                nameTextView.setText(name);
            }
        });
    }

    @Override
    public void updateColor(final String hexColor) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                colorTextView.setText(hexColor);
            }
        });
    }

    @Override
    public void displayNameEditError(final String error) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                nameEditEditText.setError(error);
            }
        });
    }

    @Override
    public void setupConnectingUI() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                connectButton.setEnabled(false);
                connectButton.setText(getResources().getString(R.string.mxf_connecting));
            }
        });
    }
}
