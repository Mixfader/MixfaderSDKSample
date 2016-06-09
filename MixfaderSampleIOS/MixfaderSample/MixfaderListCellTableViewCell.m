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
//  MixfaderListCellTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 23/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderListCellTableViewCell.h"

@interface MixfaderListCellTableViewCell () <MXFColorObserver,MXFConnectObserver,MXFRenameObserver,MXFRSSIObserver>

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderListCellTableViewCell

- (void)updateConnectedLabel:(MXFMixfaderInterface *)interface
{
	[self.mixfaderConnectedLabel setFont:[UIFont systemFontOfSize:12]];
	if(interface.connected)
	{
		self.mixfaderConnectedLabel.text = @"Connected";
		[self.mixfaderConnectedLabel setTextColor:[UIColor colorWithRed:0.17 green:0.73 blue:0.51 alpha:1.00]];
	}
	else if (interface.available)
	{
		self.mixfaderConnectedLabel.text = @"Available";
		[self.mixfaderConnectedLabel setTextColor:[UIColor colorWithRed:1.00 green:0.82 blue:0.27 alpha:1.00]];
	}
	else
	{
		self.mixfaderConnectedLabel.text = @"Not available";
		[self.mixfaderConnectedLabel setTextColor:[UIColor colorWithRed:0.91 green:0.31 blue:0.37 alpha:1.00]];
	}
}

- (void)updateWithInterface:(MXFMixfaderInterface*)interface
{
	if(![self.interface isEqual:interface])
	{
		[self.interface removeColorObserver:self];
		[self.interface removeConnectionObserver:self];
		[self.interface removeRSSIObserver:self];
		[self.interface removeRenameObserver:self];
	}
	self.interface = interface;
	
	[self.interface addColorObserver:self];
	[self.interface addRSSIObserver:self];
	[self.interface addRenameObserver:self];
	[self.interface addConnectionObserver:self];
	
	[self.mixfaderColorView setBackgroundColor:[interface color]];
	
	[self.mixfaderNameLabel setTextColor:[UIColor whiteColor]];
	[self.mixfaderNameLabel setFont:[UIFont boldSystemFontOfSize:20]];
	self.mixfaderNameLabel.text = interface.name;
	
	[self updateConnectedLabel:interface];
	
	[self.mixfaderRSSILabel setTextColor:[UIColor whiteColor]];
	[self.mixfaderRSSILabel setFont:[UIFont boldSystemFontOfSize:15]];
	self.mixfaderRSSILabel.text = [NSString stringWithFormat:@"%d dB",[interface.rssi intValue]];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

#pragma mark - Color observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeColor:(UIColor *)color
{
	[self.mixfaderColorView setBackgroundColor:[mixfader color]];
}

#pragma mark - Connection observer

- (void)mixfaderDidStartConnect:(MXFMixfaderInterface *)mixfader{}

- (void)mixfaderDidConnect:(MXFMixfaderInterface *)mixfader
{
	[self updateConnectedLabel:mixfader];
}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didDisconnect:(NSError *)error
{
	[self updateConnectedLabel:mixfader];
}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didFailConnect:(NSError *)error
{
	[self updateConnectedLabel:mixfader];
}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeAvailability:(BOOL)available
{
	[self updateConnectedLabel:mixfader];
}

#pragma mark - Rename observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateName:(NSString *)name
{
	self.mixfaderNameLabel.text = mixfader.name;
}

#pragma mark - RSSI observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateRssi:(NSNumber *)rssi
{
	self.mixfaderRSSILabel.text = [NSString stringWithFormat:@"%d dB",[mixfader.meanRssi intValue]];
}

@end
