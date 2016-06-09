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

import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.scanner.MixFaderScanner;

public class SampleUtils {

    /* Package */ static String getRssiLevelString(int rssiLevel) {
        switch (rssiLevel){
            case MixFaderInterface.RSSI_VERY_LOW:
                return "VERY_LOW";
            case MixFaderInterface.RSSI_LOW:
                return "LOW";
            case MixFaderInterface.RSSI_NORMAL:
                return "NORMAL";
            case MixFaderInterface.RSSI_HIGH:
                return "HIGH";
            case MixFaderInterface.RSSI_VERY_HIGH:
                return "VERY_HIGH";
            default:
                return "-";
        }
    }

    /* Package */ static String getCalibrationStateString(int state) {
        switch (state) {
            case MixFaderInterface.CALIBRATION_START:
                return "CALIBRATION_START";
            case MixFaderInterface.CALIBRATION_LEFT_DONE:
                return "CALIBRATION_LEFT_DONE";
            case MixFaderInterface.CALIBRATION_RIGHT_DONE:
                return "CALIBRATION_RIGHT_DONE";
            case MixFaderInterface.CALIBRATION_FAIL:
                return "CALIBRATION_FAIL";
            case MixFaderInterface.CALIBRATION_CANCELLED:
                return "CALIBRATION_CANCELLED";
            case MixFaderInterface.CALIBRATION_END:
                return "CALIBRATION_END";
            case MixFaderInterface.CALIBRATION_IDLE:
                return "CALIBRATION_IDLE";
            default:
                return "-";
        }
    }

    /* Package */ static String getNameErrorString(int error) {
        switch (error) {
            case MixFaderInterface.NAME_INVALID_STRING_EMPTY:
                return  "NAME_INVALID_STRING_EMPTY";
            case MixFaderInterface.NAME_INVALID_STRING_NULL:
                return  "NAME_INVALID_STRING_NULL";
            case MixFaderInterface.NAME_INVALID_STRING_TOO_LONG:
                return  "NAME_INVALID_STRING_TOO_LONG";
            case MixFaderInterface.NAME_INVALID_STRING_TOO_SHORT:
                return  "NAME_INVALID_STRING_TOO_SHORT";
            default:
                return  "-";
        }
    }
    
    /* Package */ static String getColorString(int intColor) {
        return String.format("#%06X", (0xFFFFFF & intColor));
    }

    public static String getScannerErrorString(MixFaderScanner.InitResult error) {
        switch (error) {
            case SUCCESS:
                return  "SUCCESS";
            case ERROR_BLE_NOT_SUPPORTED:
                return  "ERROR_BLE_NOT_SUPPORTED";
            case ERROR_BLUETOOTH_ADMIN_PERMISSION_NEEDED:
                return  "ERROR_BLUETOOTH_ADMIN_PERMISSION_NEEDED";
            case ERROR_BLUETOOTH_NOT_ENABLED:
                return  "ERROR_BLUETOOTH_NOT_ENABLED";
            case ERROR_BLUETOOTH_PERMISSION_NEEDED:
                return  "ERROR_BLUETOOTH_PERMISSION_NEEDED";
            case ERROR_INVALID_ADAPTER:
                return  "ERROR_INVALID_ADAPTER";
            case ERROR_INVALID_MANAGER:
                return  "ERROR_INVALID_MANAGER";
            case ERROR_LOCATION_PERMISSION_NEEDED:
                return  "ERROR_LOCATION_PERMISSION_NEEDED";
            case ERROR_NULL_CONTEXT:
                return  "ERROR_NULL_CONTEXT";
            default:
                return  "UNKNOWN ERROR";
        }
    }
}
