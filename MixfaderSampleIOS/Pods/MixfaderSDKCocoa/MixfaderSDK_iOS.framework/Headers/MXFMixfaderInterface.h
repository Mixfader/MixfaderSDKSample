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
 *
 *  MXFMixfaderInterface.h
 *  MXFSDK
 *
 *  Created by Jean-baptiste Fabre on 16/11/2015.
 *  Copyright © 2015 Jean-baptiste Fabre. All rights reserved.
 */

#import <Foundation/Foundation.h>
#import "MXFTypes.h"


@class MXFMixfaderInterface;

@protocol MXFConnectObserver <NSObject>
@optional
- (void)mixfaderDidStartConnect:(nonnull MXFMixfaderInterface *)mixfader;

- (void)mixfaderDidConnect:(nonnull MXFMixfaderInterface *)mixfader;

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didDisconnect:(nullable NSError *)error;

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didFailConnect:(nullable NSError *)error;

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didChangeAvailability:(BOOL)available;

@end

@protocol MXFRSSIObserver <NSObject>
@optional
- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didUpdateRssi:(nonnull NSNumber*)rssi;

@end

@protocol MXFBatteryObserver <NSObject>
@optional

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didChangeBatteryChargeState:(BOOL)charge;

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didUpdateBatteryLevel:(NSInteger)battery;

@end

@protocol MXFRenameObserver <NSObject>
@optional
- (void)mixfader:(nonnull MXFMixfaderInterface*)mixfader didChangeName:(nonnull NSString *)name;

@end

@protocol MXFFaderObserver <NSObject>
@optional
- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didUpdateFaderValue:(float)faderValue;

@end

@protocol MXFButtonObserver <NSObject>
@optional
- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didChangeButtonState:(BOOL)buttonPush;

@end

@protocol MXFCalibrationObserver <NSObject>
@optional

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didChangeCalibrationState:(MXFCalibrationStateType)state;

@end

@protocol  MXFColorObserver <NSObject>
@optional
- (void)mixfader:(nonnull MXFMixfaderInterface*)mixfader didChangeColor:(nonnull COLOR_CLASS *)color;

@end



@interface MXFMixfaderInterface : NSObject

//-----------------------
#pragma mark - Connection
/*!
 * @discussion add an observer for any connection event of the MXFConnectObserver protocol
 * @param observer an object that conform to MXFConnectObserver protocol
 * @see MXFConnectObserver
 */
- (void)addConnectionObserver:(nonnull id <MXFConnectObserver>)observer;

/*!
 * @discussion remove an observer from the connection observer list
 * @param observer an object that conform to MXFConnectObserver protocol
 * @see MXFConnectObserver
 */
- (void)removeConnectionObserver:(nonnull id <MXFConnectObserver>)observer;

/*!
 * @discussion connect the current mixfader interface
 * @param completion a completion block indicating the current state of the connection process and any error
 */
- (void)connectWithCompletion:(nullable MXFConnectCompletion)completion;

/*!
 * @discussion disconnect the current mixfader interface
 * @param completion a completion block indicating the current state of the disconnection process and any error
 */
- (void)disconnectWithCompletion:(nullable MXFConnectCompletion)completion;

/*!
 * @discussion flag indcating if the current interface is connected
 * @return connected BOOL
 */
- (BOOL)connected;

/*!
 * @discussion flag indcating if the current interface is availbale for connection
 * @return available BOOL
 */
- (BOOL)available;

/*!
 * @discussion flag indcating if the current interface has start a connection process
 * @return connecting BOOL
 */
- (BOOL)connecting;


//-----------------
#pragma mark - RSSI

/*!
 * @discussion add an observer for any RSSI value change event
 * @param observer an object that conform to MXFRSSIObserver
 * @see MXFRSSIObserver
 */
- (void)addRSSIObserver:(nonnull id <MXFRSSIObserver>)observer;

/*!
 * @discussion remove an observer from the RSSI observer list
 * @param observer an object that conform to MXFRSSIObserver
 * @see MXFRSSIObserver
 */
- (void)removeRSSIObserver:(nonnull id <MXFRSSIObserver>)observer;

/*!
 * @discussion the current RSSI raw value (@b -100 to @b 0 db, @b 127 --> unknow rssi value)
 * @return rssi NSNumber
 */
- (nonnull NSNumber *)rssi;

/*!
 * @discussion the mean value of rssi calculated over 70 values of rssi
 * @return meanRssi NSNumber
 */
- (nonnull NSNumber *)meanRssi;

/*!
 * @discussion This method return a MXFRSSILevelType which is a convinent descriptor of rssi value
 * @see MXFRSSILevelType
 * @return rssiLevel MXFRSSILevelType
 */
- (MXFRSSILevelType)rssiLevel;


//------------------
#pragma mark - Stats

/*!
 * @discussion This method perform a read of the latest value of the statisitics store in the mixfader
 * @param completion the completion block for the operation
 * @see MXFStatsCompletion
 */
- (void)readStatistics:(nullable MXFStatsCompletion)completion;

/*!
 * @discussion This method return the number of crossfader cycles (counted only when the mixfader is awake) since the fisrt day
 * @return crossfaderCycles NSInteger
 */
- (NSInteger)crossfaderCycles;

/*!
 * @discussion This method return the number of batterie recharge cycles (counted only when the mixfader is awake, the counter is incremented each time a power source is pluged) since the fisrt day
 * @return batterieCycles NSInteger
 */
- (NSInteger)batterieCycles;

/*!
 * @discussion This method return the number of button clicks (counted only when the mixfader is awake) since the fisrt day
 * @return buttonClicks NSInteger
 */
- (NSInteger)buttonClicks;

/*!
 * @discussion This method return the number of connection process since the fisrt day
 * @return connectionCount NSInteger
 */
- (NSInteger)connectionCount;


//---------------------
#pragma mark - Batterie

/*!
 * @discussion add an observer for any battery event of the MXFBatteryObserver protocol
 * @param observer an object that conform to MXFBatteryObserver protocol
 * @see MXFBatteryObserver
 */
- (void)addBatteryObserver:(nonnull id <MXFBatteryObserver>)observer;

/*!
 * @discussion remove an observer from the battery observer list
 * @param observer an object that conform to MXFBatteryObserver protocol
 * @see MXFBatteryObserver
 */
- (void)removeBatteryObserver:(nonnull id <MXFBatteryObserver>)observer;

/*!
 * @discussion This method read and update the current battery state
 * @param completion MXFBatteryBlock completion block
 */
- (void)readBatteryLevelWithCompletion:(nullable MXFBatteryBlock)completion;

/*!
 * @discussion this method return the last battery value read from the mixfader
 * @return batteryValue NSInteger
 */
- (NSInteger)batteryValue;

/*!
 * @discussion this method return a flag that indicate the current charging state of the battery
 * @return charging BOOL
 */
- (BOOL)charging;


//-------------------
#pragma mark - Rename

/*!
 * @discussion add an observer for any rename event of the MXFRenameObserver protocol
 * @param observer an object that conform to MXFRenameObserver protocol
 * @see MXFRenameObserver
 */
- (void)addRenameObserver:(nonnull id <MXFRenameObserver>)observer;

/*!
 * @discussion remove an observer from the rename observer list
 * @param observer an object that conform to MXFRenameObserver protocol
 * @see MXFRenameObserver
 */
- (void)removeRenameObserver:(nonnull id <MXFRenameObserver>)observer;

/*!
 * @discussion This method return the name of the mixfader
 * @return name NSString
 */
- (nonnull NSString *)name;

/*!
 * @discussion This method perform a renaming request to the mixfader. The new name must be compliant with the name rules : 
 - shorter than 18 chars
 - longer than 3 chars
 * @param completion MXFRenameCompletion completion block
 */
- (void)rename:(nonnull NSString *)newName completion:(nullable MXFRenameCompletion)completion;

/*!
 * @discussion This method validate the new name format
 * @param newName NSString the new name
 * @param error NSError
 */
- (BOOL)validateName:(nonnull NSString *)newName error:(NSError *_Nullable __autoreleasing *_Nullable)error;


//------------------
#pragma mark - Fader

/*!
 * @discussion add an observer for any fader event of the MXFFaderObserver protocol
 * @param observer an object that conform to MXFFaderObserver protocol
 * @see MXFFaderObserver
 */
- (void)addFaderObserver:(nonnull id <MXFFaderObserver>)observer;

/*!
 * @discussion remove an observer from the fader observer list
 * @param observer an object that conform to MXFFaderObserver protocol
 * @see MXFFaderObserver
 */
- (void)removeFaderObserver:(nonnull id <MXFFaderObserver>)observer;

/*!
 * @discussion This method return a boolean indicating the current state of fader notification
 * @return faderNotificationEnable BOOL
 */
- (BOOL)faderNotificationEnable;

/*!
 * @discussion This method return a boolean indicating the current orrientaion of the fader (Hammster style)
 * @return faderOrientationReverse BOOL
 */
- (BOOL)faderOrientationReverse;

/*!
 * @discussion This method return the current fader value (last received)
 * @return faderValue float
 */
- (float)faderValue;

/*!
 * @discussion This method return the current notification mode of the fader : 
 FADER_NOTIFY_ALWAYS: the interface call continuously the didUpdateFader method
 FADER_NOTIFY_ON_VALUE_CHANGE: the interface call the didUpdateFader method only when the fader value change
 * @return faderNotificationMode MXFFaderNotificationModeType
 * @see MXFFaderNotificationModeType
 */
- (MXFFaderNotificationModeType)faderNotificationMode;

/*!
 * @discussion This method change the MXFFaderNotificationModeType mode
 * @param mode MXFFaderNotificationModeType
 * @note The change are made only on local for the current session and the current interface.
 */
- (void)changeFaderNotificationMode:(MXFFaderNotificationModeType)mode;

/*!
 * @discussion This method change the orrientation mode of the fader
 * @param reverse BOOL
 * @note The change are made only on local for the current session and the current interface.
 */
- (void)reverseFaderOrrientation:(BOOL)reverse;

/*!
 * @discussion This method enable the fader notification
 * @param enable BOOL
 * @param completion MXFFaderCompletion
 * @note The change are made for the current connection session and this interface.
 */
- (void)faderNotificationEnable:(BOOL)enable completion:(nullable MXFFaderCompletion)completion;


//-------------------
#pragma mark - Button

/*!
 * @discussion add an observer for any button event of the MXFButtonObserver protocol
 * @param observer an object that conform to MXFButtonObserver protocol
 * @see MXFButtonObserver
 */
- (void)addButtonObserver:(nonnull id <MXFButtonObserver>)observer;

/*!
 * @discussion remove an observer from the connection observer list
 * @param observer an object that conform to MXFButtonObserver protocol
 * @see MXFButtonObserver
 */
- (void)removeButtonObserver:(nonnull id <MXFButtonObserver>)observer;

/*!
 * @discussion This method return a boolean telling if the button is push
 * @return buttonPushed BOOL
 */
- (BOOL)buttonPushed;


//----------------
#pragma mark - LED

/*!
 * @discussion This method make the onboard led blink (red-blue-red-blue) (the blink will auto stop after 30 sec
 * @param completion MXFBlinkLedCompletion
 * @see MXFBlinkLedCompletion
 */
- (void)blinkLedWithCompletion:(nullable MXFBlinkLedCompletion)completion;

//------------------------
#pragma mark - Calibration

/*!
 * @discussion add an observer for any calibration event of the MXFCalibrationObserver protocol
 * @param observer an object that conform to MXFCalibrationObserver protocol
 * @see MXFCalibrationObserver
 */
- (void)addCalibrationObserver:(nonnull id <MXFCalibrationObserver>)observer;

/*!
 * @discussion remove an observer from the calibration observer list
 * @param observer an object that conform to MXFCalibrationObserver protocol
 * @see MXFCalibrationObserver
 */
- (void)removeCalibrationObserver:(nonnull id <MXFCalibrationObserver>)observer;

/*!
 * @discussion This method return a boolean
 * @return calibrating boolean
 */
- (BOOL)calibrating;

/*!
 * @discussion This method start a new calibration process
 * @param completion MXFCalibrationCompletion
 * @see MXFCalibrationCompletion
 */
- (void)startNewCalibrationWithCompletion:(nullable MXFCalibrationCompletion)completion;

/*!
 * @discussion This method cancel the current calibration process
 */
- (void)cancelCalibration;

/*!
 * @discussion This method reset to factory settings the mifader calibration
 */
- (void)resetCalibration;

/*!
 * @discussion This return the value of calMin
 * @return calMin uint16_t
 */
- (uint16_t)calMin;

/*!
 * @discussion This return the value of calMax
 * @return calMax uint16_t
 */
- (uint16_t)calMax;

/*!
 * @discussion This return the value of calMin
 * @return cutMin uint16_t
 */
- (uint16_t)cutMin;

/*!
 * @discussion This return the value of cutMax
 * @return cutMax uint16_t
 */
- (uint16_t)cutMax;

/*!
 * @discussion This method update the cut profile (cutMin and cutMax must be lower than 64)
 * @param cutProfile MXFCutProfile
 * @param completion MXFCalibrationCompletion
 * @see MXFCutProfile
 * @see MXFCalibrationCompletion
 */
- (void)updateCutProfile:(nullable MXFCutProfile *)cutProfile completion:(nullable MXFCalibrationCompletion)completion;


//------------------
#pragma mark - Color

/*!
 * @discussion add an observer for any color event of the MXFColorObserver protocol
 * @param observer an object that conform to MXFColorObserver protocol
 * @see MXFColorObserver
 */
- (void)addColorObserver:(nonnull id <MXFColorObserver>)observer;

/*!
 * @discussion remove an observer from the color observer list
 * @param observer an object that conform to MXFColorObserver protocol
 * @see MXFColorObserver
 */
- (void)removeColorObserver:(nonnull id <MXFColorObserver>)observer;

/*!
 * @discussion This methode return the color of the mixfader
 * @return color (NSColor or UIColor)
 */
- (nonnull COLOR_CLASS *)color;

/*!
 * @discussion This method change the color of the mixfader
 * @param color (UIColor or NSColor)
 * @param completion MXFColorCompletion
 */
- (void)updateColor:(nullable COLOR_CLASS*)color completion:(nullable MXFColorCompletion)completion;

//------------------
#pragma mark - Infos

/*!
 * @discussion This method return the serial number of the interface
 * @return serialNumber NSString
 */
- (nonnull NSString*)serialNumber;

/*!
 * @discussion This method return the software version of the interface
 * @return softwareVersion NSString
 */
- (nonnull NSString*)softwareVersion;

/*!
 * @discussion This method return the hardware verion of the interface
 * @return hardwareVersion NSString
 */
- (nonnull NSString*)hardwareVersion;


@end
