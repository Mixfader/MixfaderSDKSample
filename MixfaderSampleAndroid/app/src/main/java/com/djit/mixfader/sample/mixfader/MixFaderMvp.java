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

/* Package */ interface MixFaderMvp {

    /**
     * {@link com.djit.mixfader.MixFaderInterface) is our "model" in this case :)
     */
    //interface Model {
    //
    //}

    /**
     * Represents a view that displays MixFader data
     */
    interface View {
        void updateCrossfaderNotificationStatus(boolean enable);

        void updateButtonState(boolean isPushed);

        void updateBatteryState(boolean isCharging);

        void updateRssiValues(String rssi, String rssiLevel);

        void updateCalibrationState(String state);

        void updateCrossfaderProgress(float crossfaderValue);

        void updateName(String name);

        void updateColor(String color);

        void updateStats(String crossfaderCyclesCount, String buttonClicksCount, String batteryCyclesCount, String connectionsCount);

        void displayNameEditError(String error);

        void setupConnectingUI();

        void setupConnectedUI();

        void setupDisconnectedUI();
    }

    /**
     * Represents the business class that receive UI inputs and deals
     * with {@link com.djit.mixfader.MixFaderInterface} instance.
     */
    interface Presenter {
        void onPause();

        void onResume();

        void connect();

        void disconnect();

        void setCrossfaderNotificationEnable(boolean enable);

        void setCrossfaderOrientationReverse(boolean enable);

        void blinkLed();

        void startCalibration();

        void cancelCalibration();

        void resetCalibration();

        void rename(String name);

        void setColor(String color);
    }
}
