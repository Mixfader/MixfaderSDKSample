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
 *  MXFSession.h
 *  MixFaderSDK
 *
 *  Created by Jean-baptiste Fabre on 13/08/2015.
 *  Copyright (c) 2015 DJiT. All rights reserved.
 */

#import <Foundation/Foundation.h>
#import "MXFMixfaderInterface.h"
#import "MXFErrorDefine.h"
#import <CoreBluetooth/CoreBluetooth.h>


@class MXFSession;

@protocol MXFSessionScanObserver <NSObject>
@optional

- (void)sessionStartScan:(nonnull MXFSession *)session;

- (void)session:(nonnull MXFSession *)session didEndScan:(nonnull NSArray *)mixfaders;

- (void)session:(nonnull MXFSession *)session didScanMixfader:(nonnull MXFMixfaderInterface *)mixfader;

- (void)session:(nonnull MXFSession *)session didUpdateBLEState:(CBCentralManagerState)state;

@end


@interface MXFSession : NSObject

/*!
 * @discussion the singleton for the current mixfader session
 */
+ (nonnull instancetype)defaultSession;

/*!
 * @discussion start the session
 * @param showBluetoothAlert allow the session to display the bluetooth power alert
 */
- (void)startSession:(BOOL)showBluetoothAlert;

/*!
 * @discussion terminate the current session clean all object and delete the current corebluetooth session
 */
- (void)terminateSession;

/*!
 * @discussion start the ble discovering process
 * @param scanDuration the duration of the scanning operation (use 0 for unlimited scan, not recommended)
 * @param scanBlock the scan block will be fired each time a new MXFMixfaderInterface is found.
 */
- (void)startScanWithDuration:(NSUInteger)scanDuration change:(nullable MXFScanBlock)scanBlock;

/*!
 * @discussion stop the discovering process
 * @param completion this block will be call after the process end.
 */
- (void)stopScanPeripheral:(void (^_Nullable)(NSArray  * _Nonnull  mixfaders))completion;


/*!
 * @discussion get current CBCentralManager state
 * @return NSTimeInterval
 */
- (CBCentralManagerState)bleState;

/*!
 * @discussion return an array of MXFMixfaderInterface which contains all mixfader found durring the session sorted by name
 * @return NSArray (nonnull)
 */
- (nonnull NSArray <MXFMixfaderInterface *> *)mixfaders;

/*!
 * @discussion add scan observer @see MXFSessionScanObserver
 */
- (void)addScanObserver:(nonnull id <MXFSessionScanObserver>)observer;

/*!
 * @discussion remove scan observer @see MXFSessionScanObserver
 */
- (void)removeScanObserver:(nonnull id <MXFSessionScanObserver>)observer;


@end
