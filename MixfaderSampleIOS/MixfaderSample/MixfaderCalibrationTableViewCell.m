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
//  MixfaderCalibrationTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderCalibrationTableViewCell.h"

@interface MixfaderCalibrationTableViewCell () <MXFCalibrationObserver>

@property(nonatomic, weak) MXFMixfaderInterface *interface;

@end
@implementation MixfaderCalibrationTableViewCell


- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
		[self.interface removeCalibrationObserver:self];
	self.interface = interface;
	[self.interface addCalibrationObserver:self];
	
	if(!self.interface.calibrating)
	{
		self.calibrationStateLabel.text = [NSString stringWithFormat:@"Current state : IDLE"];
	}
}
- (IBAction)onStartButton:(id)sender
{
	[self.interface startNewCalibrationWithCompletion:^(BOOL succes, uint16_t cutMin, uint16_t cutMax, uint16_t calMin, uint16_t calMax, NSError * _Nullable error) {
		if(succes)
		{
			dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
				self.calibrationStateLabel.text = [NSString stringWithFormat:@"Current state : IDLE"];
			});
		}
		else
		{
			UIAlertController * alert=   [UIAlertController alertControllerWithTitle:@"Calibration" message:error.localizedDescription preferredStyle:UIAlertControllerStyleAlert];
			UIAlertAction* ok = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:nil];
			[alert addAction:ok];
			[self.parentVC presentViewController:alert animated:YES completion:nil];
		}
	}];
}
- (IBAction)onCancelButton:(id)sender
{
	[self.interface cancelCalibration];
}
- (IBAction)onResetButton:(id)sender
{
	[self.interface resetCalibration];
}

#pragma mark - Calibration oberver

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeCalibrationState:(MXFCalibrationStateType)state
{
	switch (state) {
		case CALIBRATION_START:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_START";
			break;
		case CALIBRATION_LEFT_DONE:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_LEFT_DONE";
			break;
		case CALIBRATION_RIGHT_DONE:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_RIGHT_DONE";
			break;
		case CALIBRATION_IDLE:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_IDLE";
			break;
		case CALIBRATION_CANCEL:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_CANCEL";
			break;
		case CALIBRATION_END:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_END";
			break;
		case CALIBRATION_FAIL:
			self.calibrationStateLabel.text = @"Current state : CALIBRATION_FAIL";
			break;
	}
}

@end
