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
//  MixfaderFaderTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 24/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderFaderTableViewCell.h"

@interface MixfaderFaderTableViewCell ()

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderFaderTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	self.interface = interface;
	[self.enableNotificationSwitch setOn:[self.interface faderNotificationEnable]];
	[self.reverseCrossfaderSwitch setOn:[self.interface faderOrientationReverse]];
	[self.modeSegmentControl setSelectedSegmentIndex:[self.interface faderNotificationMode]];
}

- (IBAction)onFaderNotificationEnable:(id)sender {
	[self.interface faderNotificationEnable:[(UISwitch*)sender isOn] completion:^(BOOL succes, NSError * _Nullable error, BOOL state) {
		if(error)
		{
			NSLog(@"%@",error.localizedDescription);
		}
		[self.enableNotificationSwitch setOn:state];
	}];
}

- (IBAction)onFaderReverse:(id)sender {
	[self.interface reverseFaderOrrientation:[(UISwitch*)sender isOn]];
}

- (IBAction)onFaderNotificationModeChange:(id)sender {
	[self.interface changeFaderNotificationMode:self.modeSegmentControl.selectedSegmentIndex];
}



@end
