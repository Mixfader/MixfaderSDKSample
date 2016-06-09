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
 *
 *
 *  MXFErrorDefine.h
 *  MXFSDK
 *
 *  Created by Jean-baptiste Fabre on 16/09/2015.
 *  Copyright (c) 2015 Jean-baptiste Fabre. All rights reserved.
 */

#ifndef MXFSDK_MXFErrorDefine_h
#define MXFSDK_MXFErrorDefine_h

#define MXFDOMAINE @"com.mixfader.sdk"

#define k_ERROR_CODE_INTERNAL               -1

#define k_ERROR_CODE_SETUP                  000
#define k_ERROR_WRONG_TIMEOUT_VALUES        k_ERROR_CODE_SETUP + 1
#define k_ERROR_WRONG_AVAILBALE_VALUES      k_ERROR_CODE_SETUP + 2
#define k_ERROR_WRONG_RSSI_FILTER_VALUE     k_ERROR_CODE_SETUP + 3
#define k_ERROR_SESSION_NOT_STARTED         k_ERROR_CODE_SETUP + 4
#define k_ERROR_SESSION_NEED_BLE            k_ERROR_CODE_SETUP + 5


#define k_ERROR_CODE_CONNECTION             100
#define k_ERROR_CODE_CONNECTION_AL_CONNECT  k_ERROR_CODE_CONNECTION + 1
#define k_ERROR_CODE_CONNECTION_AL_DCONNECT k_ERROR_CODE_CONNECTION + 2
#define k_ERROR_CODE_CONNECTION_NO_CONNECT  k_ERROR_CODE_CONNECTION + 3
#define k_ERROR_CODE_CONNECTION_NO_PERIPH   k_ERROR_CODE_CONNECTION + 4
#define k_ERROR_CODE_CONNECTING             k_ERROR_CODE_CONNECTION + 5
#define k_ERROR_CODE_TIMEOUT_CONNECT        k_ERROR_CODE_CONNECTION + 6

#define k_ERROR_CODE_RENAME                 200
#define k_ERROR_CODE_RENAME_STR_NULL        k_ERROR_CODE_RENAME + 1
#define k_ERROR_CODE_RENAME_STR_SHORT       k_ERROR_CODE_RENAME + 2
#define k_ERROR_CODE_RENAME_STR_LONG        k_ERROR_CODE_RENAME + 3
#define k_ERROR_CODE_RENAME_CONN_ERROR      k_ERROR_CODE_RENAME + 4

#define k_ERROR_CODE_APPDATA                300
#define k_ERROR_CODE_APPDATA_WR_ID          k_ERROR_CODE_APPDATA + 1
#define k_ERROR_CODE_APPDATA_NULL           k_ERROR_CODE_APPDATA + 2
#define k_ERROR_CODE_APPDATA_BAD_FORMAT     k_ERROR_CODE_APPDATA + 3

#define k_ERROR_CODE_CALIBRATION             500
#define k_ERROR_CODE_CAL_ALLREADY_RUN        k_ERROR_CODE_CALIBRATION + 1
#define k_ERROR_CODE_CAL_NO_FADER_NOT        k_ERROR_CODE_CALIBRATION + 2
#define k_ERROR_CODE_CAL_NULL_POINTER        k_ERROR_CODE_CALIBRATION + 3
#define k_ERROR_CODE_CAL_BAD_CUT_VALUES      k_ERROR_CODE_CALIBRATION + 4
#define k_ERROR_CODE_CAL_CANCEL              k_ERROR_CODE_CALIBRATION + 5

#endif
