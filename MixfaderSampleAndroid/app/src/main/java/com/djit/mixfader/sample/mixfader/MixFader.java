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

import android.graphics.Color;

import com.djit.mixfader.MixFaderBatteryListener;
import com.djit.mixfader.MixFaderButtonListener;
import com.djit.mixfader.MixFaderCalibrationListener;
import com.djit.mixfader.MixFaderColorListener;
import com.djit.mixfader.MixFaderConnectionListener;
import com.djit.mixfader.MixFaderCrossfaderListener;
import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.MixFaderNameListener;
import com.djit.mixfader.MixFaderRssiListener;
import com.djit.mixfader.MixFaderStatsListener;

/* Package */ class MixFader implements MixFaderMvp.Presenter {

    private MixFaderInterface mixFaderInterface;
    private MixFaderMvp.View mixfaderView;

    /* Package */ MixFader(MixFaderMvp.View mixfaderView, MixFaderInterface mixFaderInterface) {
        this.mixfaderView = mixfaderView;
        this.mixFaderInterface = mixFaderInterface;
    }

    /**
     * Unregisters all listeners
     */
    @Override
    public void onPause() {
        mixFaderInterface.unregisterBatteryListener(mixfaderBatteryListener);
        mixFaderInterface.unregisterButtonListener(mixfaderButtonListener);
        mixFaderInterface.unregisterCalibrationListener(mixfaderCalibrationListener);
        mixFaderInterface.unregisterColorListener(mixfaderColorListener);
        mixFaderInterface.unregisterCrossfaderListener(mixFaderCrossfaderListener);
        mixFaderInterface.unregisterConnectionListener(mixfaderConnectionListener);
        mixFaderInterface.unregisterNameListener(mixfaderNameListener);
        mixFaderInterface.unregisterRssiListener(mixfaderRssiListener);
        mixFaderInterface.unregisterStatsListener(mixfaderStatsListener);
    }

    /**
     * Registers all listeners
     */
    @Override
    public void onResume() {
        mixFaderInterface.registerBatteryListener(mixfaderBatteryListener);
        mixFaderInterface.registerButtonListener(mixfaderButtonListener);
        mixFaderInterface.registerCalibrationListener(mixfaderCalibrationListener);
        mixFaderInterface.registerColorListener(mixfaderColorListener);
        mixFaderInterface.registerCrossfaderListener(mixFaderCrossfaderListener);
        mixFaderInterface.registerConnectionListener(mixfaderConnectionListener);
        mixFaderInterface.registerNameListener(mixfaderNameListener);
        mixFaderInterface.registerRssiListener(mixfaderRssiListener);
        mixFaderInterface.registerStatsListener(mixfaderStatsListener);
    }

    @Override
    public void connect() {
        if (!mixFaderInterface.isConnected()) {
            mixFaderInterface.connect();
        }
    }

    @Override
    public void setCrossfaderNotificationEnable(boolean enable) {
        if (enable && !mixFaderInterface.isNotificationEnable()) {
            mixFaderInterface.setCrossfaderNotificationEnable(true);
            mixfaderView.updateCrossfaderNotificationStatus(true);
        } else if (!enable && mixFaderInterface.isNotificationEnable()) {
            mixFaderInterface.setCrossfaderNotificationEnable(false);
            mixfaderView.updateCrossfaderNotificationStatus(false);
        }
    }

    @Override
    public void setCrossfaderOrientationReverse(boolean enable) {
        if (enable && !mixFaderInterface.isOrientationReverse()) {
            mixFaderInterface.setCrossfaderOrientationReverse(true);
        } else if (!enable && mixFaderInterface.isOrientationReverse()) {
            mixFaderInterface.setCrossfaderOrientationReverse(false);
        }
    }

    @Override
    public void disconnect() {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.disconnect();
        }
    }

    @Override
    public void blinkLed() {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.blinkLed();
        }
    }

    @Override
    public void startCalibration() {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.startCalibration();
        }
    }

    @Override
    public void cancelCalibration() {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.cancelCalibration();
        }
    }

    @Override
    public void resetCalibration() {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.resetCalibration();
        }
    }

    @Override
    public void rename(String name) {
        if (mixFaderInterface.isConnected()) {
            final int error = mixFaderInterface.validateName(name);
            if (error != MixFaderInterface.NAME_VALID) {
                mixfaderView.displayNameEditError(SampleUtils.getNameErrorString(error));
            } else {
                mixFaderInterface.rename(name);
            }
        }
    }

    @Override
    public void setColor(String color) {
        if (mixFaderInterface.isConnected()) {
            mixFaderInterface.setColor(Color.parseColor(color));
        }
    }

    private MixFaderConnectionListener mixfaderConnectionListener = new MixFaderConnectionListener() {
        @Override
        public void onMixFaderConnectionStarted(MixFaderInterface mixFaderInterface) {
            mixfaderView.setupConnectingUI();
        }

        @Override
        public void onMixFaderConnected(MixFaderInterface mixFaderInterface) {
            mixfaderView.setupConnectedUI();
        }

        @Override
        public void onMixFaderConnectionFailed(MixFaderInterface mixFaderInterface) {

        }

        @Override
        public void onMixFaderDisconnected(MixFaderInterface mixFaderInterface) {
            mixfaderView.setupDisconnectedUI();
        }
    };

    private MixFaderCrossfaderListener mixFaderCrossfaderListener = new MixFaderCrossfaderListener() {
        @Override
        public void onCrossfaderValueChanged(MixFaderInterface mixFaderInterface, final float crossfaderValue) {
            mixfaderView.updateCrossfaderProgress(crossfaderValue);
        }
    };

    private MixFaderButtonListener mixfaderButtonListener = new MixFaderButtonListener() {
        @Override
        public void onMixFaderButtonStateChanged(MixFaderInterface mixFaderInterface, final boolean isPushed) {
            mixfaderView.updateButtonState(isPushed);
        }
    };

    private MixFaderBatteryListener mixfaderBatteryListener = new MixFaderBatteryListener() {
        @Override
        public void onMixFaderBatteryValueUpdated(MixFaderInterface mixFaderInterface, int batteryValue) {

        }

        @Override
        public void onMixFaderBatteryChargingStatusChanged(MixFaderInterface mixFaderInterface, final boolean isCharging) {
            mixfaderView.updateBatteryState(isCharging);
        }
    };

    private MixFaderRssiListener mixfaderRssiListener = new MixFaderRssiListener() {
        @Override
        public void onMixFaderRssiUpdated(final MixFaderInterface mixFaderInterface, final int rssi) {
            mixfaderView.updateRssiValues(String.valueOf(rssi), SampleUtils.getRssiLevelString(mixFaderInterface.getRssiLevel()));
        }
    };

    private MixFaderStatsListener mixfaderStatsListener = new MixFaderStatsListener() {
        @Override
        public void onMixFaderStatsChanged(MixFaderInterface mixFaderInterface, final int crossfaderCyclesCount, final int batteryCyclesCount, final int buttonClicksCount, final int connectionsCount) {
            mixfaderView.updateStats(
                    String.valueOf(crossfaderCyclesCount),
                    String.valueOf(buttonClicksCount),
                    String.valueOf(batteryCyclesCount),
                    String.valueOf(connectionsCount)
            );
        }
    };

    private MixFaderCalibrationListener mixfaderCalibrationListener = new MixFaderCalibrationListener() {
        @Override
        public void onCalibrationStateChanged(MixFaderInterface mixFaderInterface, final int newState) {
            mixfaderView.updateCalibrationState(SampleUtils.getCalibrationStateString(newState));
        }
    };

    private MixFaderNameListener mixfaderNameListener = new MixFaderNameListener() {
        @Override
        public void onMixFaderNameChanged(MixFaderInterface mixFaderInterface, final String name) {
            mixfaderView.updateName(name);
        }
    };

    private MixFaderColorListener mixfaderColorListener = new MixFaderColorListener() {
        @Override
        public void onMixFaderColorChanged(MixFaderInterface mixFaderInterface, final int color) {
            final String hexColor = String.format("#%06X", (0xFFFFFF & color));
            mixfaderView.updateColor(hexColor);
        }
    };
}
