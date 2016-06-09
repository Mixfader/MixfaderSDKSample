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
 *  MXFTypes.h
 *  MXFSDK
 *
 *  Created by Jean-baptiste Fabre on 17/11/2015.
 *  Copyright © 2015 Jean-baptiste Fabre. All rights reserved.
 */

#if TARGET_OS_IPHONE
#import <UIKit/UIKit.h>
#define COLOR_CLASS UIColor
#else
#import <Cocoa/Cocoa.h>
#define COLOR_CLASS NSColor
#endif


@class MXFMixfaderInterface;

typedef NS_ENUM(NSUInteger, MXFConnectEvent) {
	CONNECT_START,
	CONNECT_SUCCESS,
	CONNECT_FAIL,
	DISCONNECT_START,
	DISCONNECT_END,
};

typedef NS_ENUM(NSUInteger, MXFRSSILevelType) {
    VERY_LOW,
    LOW,
    NORMAL,
    HIGH,
    VERY_HIGH,
};

typedef NS_ENUM(NSUInteger, MXFModeType) {
    NORMAL_M = 1,
    SHARED_M = 2,
};

typedef NS_ENUM(NSUInteger, MXFNotifType) {
    NOTIFICATION_OFF,
    NOTIFICATION_ON,
};

typedef NS_ENUM(NSUInteger, MXFButtonType) {
    RELEASE,
    PUSH,
};

typedef NS_ENUM(NSUInteger, MXFBatterieType) {
    NO_CHARGE,
    CHARGE,
};

typedef NS_ENUM(NSUInteger, MXFCalibrationStateType){
    CALIBRATION_IDLE,
    CALIBRATION_START,
    CALIBRATION_FAIL,
    CALIBRATION_CANCEL,
    CALIBRATION_END,
    CALIBRATION_LEFT_DONE,
    CALIBRATION_RIGHT_DONE,
};

typedef NS_ENUM(NSUInteger, MXFFaderNotificationModeType) {
    FADER_NOTIFY_ALWAYS,
    FADER_NOTIFY_ON_VALUE_CHANGE,
};

typedef struct
{
    uint16_t cutMin;
    uint16_t cutMax;
} MXFCutProfile;

typedef void(^MXFStatsCompletion)(BOOL success, NSError * _Nullable error,NSInteger crossfaderCycles, NSInteger batterieCycles, NSInteger buttonClicks, NSInteger connectionCount);

typedef void(^MXFRenameCompletion)(BOOL success, NSError * _Nullable error, NSString * _Nullable name);

typedef void(^MXFFaderCompletion)(BOOL succes, NSError * _Nullable error, BOOL state);

typedef void(^MXFModeCompletion)(BOOL succes, NSError * _Nullable error, MXFModeType mode);

typedef void(^MXFCalibrationCompletion)(BOOL succes, uint16_t cutMin, uint16_t cutMax, uint16_t calMin, uint16_t calMax, NSError * _Nullable error);

typedef void(^MXFBlinkLedCompletion)(NSError * _Nullable error);

typedef void(^MXFColorCompletion)(NSError * _Nullable error);

typedef void (^MXFConnectCompletion) (MXFConnectEvent event, NSError * _Nullable error);

typedef void (^MXFScanBlock) (MXFMixfaderInterface * _Nullable mixfader, NSError * _Nullable error);

typedef void (^MXFBatteryBlock) (NSInteger battery, BOOL charging, NSError * _Nullable error);



