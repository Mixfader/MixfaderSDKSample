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
//
//  MixfaderBatteryTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderBatteryTableViewCell.h"

@interface MixfaderBatteryTableViewCell () <MXFBatteryObserver>

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderBatteryTableViewCell

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
	   [self.interface removeBatteryObserver:self];
	self.interface = interface;
	[self.interface addBatteryObserver:self];
	[self.batteryProgressValue setProgress:self.interface.batteryValue/100.f];
	[self.batteryValueLabel setText:[NSString stringWithFormat:@"%ld %%",(long)self.interface.batteryValue]];
	[self.chargingSwitch setOn:self.interface.charging];
	[self.interface readBatteryLevelWithCompletion:^(NSInteger battery, BOOL charging, NSError * _Nullable error) {
		
	}];
}

#pragma mark - Battery observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateBatteryLevel:(NSInteger)battery
{
	[self.batteryProgressValue setProgress:battery/100.f];
	[self.batteryValueLabel setText:[NSString stringWithFormat:@"%ld %%",(long)battery]];
}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeBatteryChargeState:(BOOL)charge
{
	[self.chargingSwitch setOn:charge];
}

@end
