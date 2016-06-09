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
//  MixfaderStatsTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderStatsTableViewCell.h"

@interface MixfaderStatsTableViewCell ()

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end


@implementation MixfaderStatsTableViewCell


- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	self.interface = interface;
	self.crossfaderStatLabel.text = [NSString stringWithFormat:@"Crossfader cycles: %d",(int)interface.crossfaderCycles];
	self.connectionStatLabel.text = [NSString stringWithFormat:@"Connction count: %d",(int)interface.connectionCount];
	self.batteryStatLabel.text = [NSString stringWithFormat:@"Battery cycles: %d",(int)interface.batterieCycles];
	self.buttonStatLabel.text = [NSString stringWithFormat:@"Button clicks: %d",(int)interface.buttonClicks];
}
- (IBAction)onRefreshButton:(id)sender {
	[self.interface readStatistics:^(BOOL success, NSError * _Nullable error, NSInteger crossfaderCycles, NSInteger batterieCycles, NSInteger buttonClicks, NSInteger connectionCount) {
		self.crossfaderStatLabel.text = [NSString stringWithFormat:@"Crossfader cycles: %d",(int)crossfaderCycles];
		self.connectionStatLabel.text = [NSString stringWithFormat:@"Connction count: %d",(int)connectionCount];
		self.batteryStatLabel.text = [NSString stringWithFormat:@"Battery cycles: %d",(int)batterieCycles];
		self.buttonStatLabel.text = [NSString stringWithFormat:@"Button clicks: %d",(int)buttonClicks];
	}];
}

@end
