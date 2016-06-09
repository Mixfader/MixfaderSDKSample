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
 *  MXFDefinesHeader.h
 *  MixFaderSDK
 *
 *  Created by Jean-baptiste Fabre on 07/08/2015.
 *  Copyright (c) 2015 DJiT. All rights reserved.
 */

#ifndef MixFaderSDK_MXFDefinesHeader_h
#define MixFaderSDK_MXFDefinesHeader_h

#define MXF_DATA_MAIN_SERVICE_UUID                  @"0100FAB1-0DF1-EE99-4044-CE17A517DADE"
#define MXF_DATA_MAIN_NOTIF_CHAR_UUID               @"0100FAB2-0DF1-EE99-4044-CE17A517DADE"
#define MXF_DATA_MAIN_WRITE_CHAR_UUID               @"0100FAB3-0DF1-EE99-4044-CE17A517DADE"

#define MXF_DATA_STATE_SERVICE_UUID                 @"0100FAB4-0DF1-EE99-4044-CE17A517DADE"
#define MXF_DATA_STATE_READ_NOTIF_CHAR_UUID         @"0100FAB5-0DF1-EE99-4044-CE17A517DADE"

#define MXF_DATA_STATS_SERVICE_UUID                 @"0100FAB6-0DF1-EE99-4044-CE17A517DADE"
#define MXF_DATA_STATS_READ_CHAR_UUID               @"0100FAB7-0DF1-EE99-4044-CE17A517DADE"

#define MXF_DFU_SERVICE_UUID                        @"00001530-1212-EFDE-1523-785FEABCD123"
#define MXF_DFU_CONTROL_POINT_CHAR_UUID             @"00001531-1212-EFDE-1523-785FEABCD123"
#define MXF_DFU_PACKET_CHAR_UUID                    @"00001532-1212-EFDE-1523-785FEABCD123"
#define MXF_DFU_VERSION_CHAR_UUID                   @"00001534-1212-EFDE-1523-785FEABCD123"

//#define MXF_DFU_SERVICE_UUID                        @"1530"
//#define MXF_DFU_CONTROL_POINT_CHAR_UUID             @"1531"
//#define MXF_DFU_PACKET_CHAR_UUID                    @"1532"
//#define MXF_DFU_VERSION_CHAR_UUID                   @"1534"


#define MXF_DFU_MANIFEST_FIRM_KEY                   @"mixfaderFirmware"
#define MXF_DFU_MANIFEST_MIN_HARD_KEY               @"hardwareMinVersion"
#define MXF_DFU_MANIFEST_SOFT_VERSION_KEY           @"softVersion"
#define MXF_DFU_MANIFEST_MASTER_SECTION_KEY         @"masterSection"
#define MXF_DFU_MANIFEST_SLAVE_SECTION_KEY          @"slaveSection"

#define MXF_DFU_MANIFEST_FOLDER_NAME_KEY            @"folder_name"
#define MXF_DFU_MANIFEST_BIN_PATH_KEY               @"bin_file"
#define MXF_DFU_MANIFEST_DAT_PATH_KEY               @"dat_file"
#define MXF_DFU_MANIFEST_PACKET_DATA_KEY            @"init_packet_data"

//#define

/** PERIPHERAL mode define */
#define PERIPHERAL_MODE_LOW_POWER      (0UL)
#define PERIPHERAL_MODE_NORMAL         (1UL)
#define PERIPHERAL_MODE_SHARED         (2UL)
#define PRL_MODE_MASK                  0b00011000
#define PRL_MODE_POS                   3

/** PERIPHERAL state define */
#define PERIPHERAL_STATE_WAKE_UP       (0UL)
#define PERIPHERAL_STATE_ADV           (1UL)
#define PERIPHERAL_STATE_CONNECTED     (2UL)
#define PERIPHERAL_STATE_GO_SLEEP      (4UL)
#define PRL_STATE_MASK                 0b00000111
#define PRL_STATE_POS                  0

/** PERIPHERAL crossfader notifications*/
#define PERIPHERAL_NOTIF_DISABLE       (0UL)
#define PERIPHERAL_NOTIF_ENABLE        (1UL)
#define PRL_NOTIF_MASK                 0b01000000
#define PRL_NOTIF_POS                  6

/** PERIPHERAL batteire */
#define PERIPHERAL_BATTERIE_CHARGING   (1UL)
#define PERIPHERAL_BATTERIE_BATTERIE   (0UL)
#define PRL_BATTERIE_MASK              0b10000000
#define PRL_BATTERIE_POS               7

/** PERIPHERAL button define */

#define PERIPHERAL_BUTTON_PUSHED       (1UL)
#define PERIPHERAL_BUTTON_RELEASED     (0UL)
#define PRL_BUTTON_MASK                0b00100000
#define PRL_BUTTON_POS                 5


typedef enum {
    MASTER_ROLE,
    SLAVE_ROLE,
} MXFPeripheralRole_t;

#define MAX_SYSTEM_ID 20

typedef enum {
	APP_DATA_SYSTEM_ID,		// 1
	APP_DATA_BLE_ID,		// 2
	APP_DATA_RENAME_ID,		// 3
    APP_DATA_RADIO_ID,//--->// deprecated
	APP_DATA_BUTTON_ID,		// 4
	APP_DATA_BATTERIE_ID,	// 5
	APP_DATA_GPIO_ID,		// 6
    APP_DATA_FADER_ID,
    APP_DATA_ADC_ID,
    APP_DATA_PEER_ADDRESS_ID,
    APP_DATA_ADV_DATA_ID,
    APP_DATA_STORAGE_ID,
    APP_DATA_USB_ID, //--->// deprecated
    APP_DATA_SLAVE_STATE_ID,
    APP_DATA_DFU_ID,
    APP_DATA_CALIBRATION_ID,
    APP_DATA_COLOR_ID,
    APP_DATA_ADV_START_ID,
    APP_DATA_CONFIG_ID,
    APP_DATA_PING_ID,
} app_data_identifier_t;

typedef enum{
    APP_ORIGIN_BLE,
    APP_ORIGIN_UART,
    APP_ORIGIN_BUTTON,
    APP_ORIGIN_BATTERY,
    APP_ORIGIN_MANAGER,
    APP_ORIGIN_RADIO,
    APP_ORIGIN_GPIO,
    APP_ORIGIN_ADC,
    APP_ORIGIN_USB,
    APP_ORIGIN_STORAGE,
    APP_ORIGIN_CORE,
    APP_ORIGIN_LED,
} app_data_origin_t;

typedef enum {
    APP_STORAGE_UPDATE_NAME,
    APP_STORAGE_SAVE,
    APP_STORAGE_SAVE_END,
    APP_STORAGE_FETCH,
} app_storage_evt_data_t;

typedef enum {
    APP_BLE_ADV_START_EVT,
    APP_BLE_CONNECTED_EVT,
    APP_BLE_DISCONNECT_EVT,
    APP_BLE_TIME_OUT_EVT,
    APP_BLE_CONN_INTERVAL_UPT_EVT,
} app_ble_evt_data_t;

typedef enum {
    APP_SYSTEM_WAKEUP_EVT,
    APP_SYSTEM_SLEEP_EVT,
    APP_SYSTEM_NORMAL_MODE_EVT,
    APP_SYSTEM_LOW_POWER_MODE_EVT, //- DEPRECATED -//
    APP_SYSTEM_SHARED_MODE_EVT,
    APP_SYSTEM_POWER_SAVE_EVT,
    APP_SYSTEM_INFO_UPDATE, //- DEPRECATED -//
    APP_SYSTEM_DISCONNECT_REQUEST,
} app_system_evt_data_t;

/*----------------------*/
//FADER
typedef enum{
    APP_FADER_VALUE,
    APP_FADER_NOTIFICATION_ON,
    APP_FADER_NOTIFICATION_OFF,
    APP_FADER_PACKET_NUMBER,
} app_fader_event_t;

/*----------------------*/
//GPIO BUTTON BATTERIE LED
typedef enum{
    APP_GPIO_UPT_TICKS_EVT,
    APP_GPIO_LED_MODE_EVT,
    APP_GPIO_BUTTON_EVALUATE_EVT,
} app_gpio_evt_t;

typedef enum {
    APP_BUTTON_RELEASED_EVT,
    APP_BUTTON_PUSHED_EVT,
    APP_BUTTON_LONG_TOUCH_EVT,
    APP_BUTTON_IDLE,
} app_button_evt_data_t;

typedef enum{
    APP_BATTERIE_CHARGE,
    APP_BATTERIE_NO_CHARGE,
} app_batterie_evt_t;

typedef enum {
    APP_LED_UPD,
    APP_LED_OFF,
    APP_LED_ON,
    APP_LED_ADVERTISE,
    APP_LED_CONNECTED,
    APP_LED_BATTERIE_CHARGE,
    APP_LED_FLASH,
    APP_LED_FLASH_BLUE,
    APP_LED_FLASH_RED,
    APP_LED_FLASH_BOTH,
    APP_LED_FLASH_OFF,
    APP_LED_TURN_OFF,
} app_gpio_led_mode_t;

/*----------------------*/
//SLAVE - MASTER COM
typedef enum
{
    APP_SLAVE_WAKE,
    APP_SLAVE_SLEEP,
    APP_SLAVE_ADVERTISE,
    APP_SLAVE_CONNECTED,
    APP_SLAVE_REC_COLOR,
    APP_SLAVE_REC_NAME,
    APP_SLAVE_REC_ADDR,
    APP_SLAVE_REC_CAL,
    APP_SLAVE_UPT_TICKS_EVT,
} app_slave_state_evt_t;

typedef enum{
    APP_PING_CONNECTED_EVT,
    APP_PING_CONNECTED_TRUE_EVT,
    APP_PING_CONNECTED_FALSE_EVT,
    APP_PING_AWAKE_EVT,
    APP_PING_AWAKE_ACK_EVT
}app_ping_evt_t;

/*----------------------*/
//Calibration
typedef enum{
    APP_CAL_GET_EVT,
    APP_CAL_SET_EVT,
    APP_CAL_DATA_EVT,
} app_cal_evt_t;

/*----------------------*/
//Color
typedef enum{
    APP_COLOR_GET_EVT,
    APP_COLOR_SET_EVT,
    APP_COLOR_DATA_EVT,
} app_color_evt_t;

/*----------------------*/
//Bonding
typedef enum{
    APP_CONFIG_CLEAR_BONDING_TABLE_EVT,
} app_config_evt_t;


#endif
