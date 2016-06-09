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
//  MixfaderRSSITableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderRSSITableViewCell.h"

@interface MixfaderRSSITableViewCell () <MXFRSSIObserver>

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderRSSITableViewCell


- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
		[self.interface removeRSSIObserver:self];
	self.interface = interface;
	[self.interface addRSSIObserver:self];
	self.rssiLabel.text = [NSString stringWithFormat:@"%d dB",[self.interface.rssi intValue]];
	self.rssiMeanLabel.text = [NSString stringWithFormat:@"%d dB",[[self.interface meanRssi] intValue]];
	[self.rssiValueProgress setProgress:-30.f/[self.interface.rssi intValue]];
	[self.rssiMeanProgress setProgress:-30.f/[self.interface.meanRssi intValue]];
	
	switch (self.interface.rssiLevel) {
		case HIGH:
			self.rssiLevelLabel.text = @"HIGH";
			break;
		case LOW:
			self.rssiLevelLabel.text = @"LOW";
			break;
		case NORMAL:
			self.rssiLevelLabel.text = @"NORMAL";
			break;
		case VERY_HIGH:
			self.rssiLevelLabel.text = @"VERY_HIGH";
			break;
		case VERY_LOW:
			self.rssiLevelLabel.text = @"VERY_LOW";
			break;
	}
}

#pragma mark - RSSI observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateRssi:(NSNumber *)rssi
{
	self.rssiLabel.text = [NSString stringWithFormat:@"%d dB",[mixfader.rssi intValue]];
	self.rssiMeanLabel.text = [NSString stringWithFormat:@"%d dB",[[mixfader meanRssi] intValue]];
	[self.rssiValueProgress setProgress:-30.f/[mixfader.rssi intValue]];
	[self.rssiMeanProgress setProgress:-30.f/[mixfader.meanRssi intValue]];
	switch (mixfader.rssiLevel) {
		case HIGH:
			self.rssiLevelLabel.text = @"HIGH";
			break;
		case LOW:
			self.rssiLevelLabel.text = @"LOW";
			break;
		case NORMAL:
			self.rssiLevelLabel.text = @"NORMAL";
			break;
		case VERY_HIGH:
			self.rssiLevelLabel.text = @"VERY_HIGH";
			break;
		case VERY_LOW:
			self.rssiLevelLabel.text = @"VERY_LOW";
			break;
	}
}

@end
