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
//  MixfaderDetailViewController.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 24/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderDetailViewController.h"
#import <MixfaderSDK_iOS/MixfaderSDK_iOS.h>
#import "MixfaderInfoTableViewCell.h"
#import "MixfaderFaderTableViewCell.h"
#import "MixfaderBatteryTableViewCell.h"
#import "MixfaderButtonTableViewCell.h"
#import "MixfaderRSSITableViewCell.h"
#import "MixfaderStatsTableViewCell.h"
#import "MixfaderColorTableViewCell.h"
#import "MixfaderLedTableViewCell.h"
#import "MixfaderNameTableViewCell.h"
#import "MixfaderCalibrationTableViewCell.h"
#import "MixfaderTableViewCell.h"

typedef enum : NSUInteger {
	informationsCell,
	faderCell,
	batteryCell,
	buttonCell,
	rssiCell,
	statsCell,
	colorCell,
	ledCell,
	nameCell,
	calibrationCell,
} Cells_t;

@interface MixfaderDetailViewController () <UITableViewDelegate,UITableViewDataSource,MXFConnectObserver,MXFFaderObserver>



@end

@implementation MixfaderDetailViewController

- (void)viewDidLoad {
	[super viewDidLoad];
	[self setupView];
	[self updateWithInterface:self.interface];
}

- (void)setupView
{
	[self.detailTableView setTranslatesAutoresizingMaskIntoConstraints:NO];
	[self.detailTableView setBackgroundColor:[UIColor whiteColor]];
	[self.detailTableView setDelegate:self];
	[self.detailTableView setDataSource:self];
	
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderInfoTableViewCell" bundle:nil] forCellReuseIdentifier:@"informationsCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderFaderTableViewCell" bundle:nil] forCellReuseIdentifier:@"faderCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderBatteryTableViewCell" bundle:nil] forCellReuseIdentifier:@"batteryCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderButtonTableViewCell" bundle:nil] forCellReuseIdentifier:@"buttonCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderRSSITableViewCell" bundle:nil] forCellReuseIdentifier:@"rssiCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderStatsTableViewCell" bundle:nil] forCellReuseIdentifier:@"statsCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderColorTableViewCell" bundle:nil] forCellReuseIdentifier:@"colorCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderLedTableViewCell" bundle:nil] forCellReuseIdentifier:@"ledCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderNameTableViewCell" bundle:nil] forCellReuseIdentifier:@"nameCell"];
	[self.detailTableView registerNib:[UINib nibWithNibName:@"MixfaderCalibrationTableViewCell" bundle:nil] forCellReuseIdentifier:@"calibrationCell"];

}

- (void)viewDidLayoutSubviews
{
	[self.detailTableView setContentInset:UIEdgeInsetsMake(self.headerView.frame.size.height, 0, self.connectView.frame.size.height, 0)];
}

- (IBAction)back
{
	[self.navigationController popViewControllerAnimated:true];
}

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(![self.interface isEqual:interface])
	{
		[self.interface removeConnectionObserver:self];
		[self.interface removeFaderObserver:self];
	}
	self.interface = interface;
	[self.interface addConnectionObserver:self];
	[self.interface addFaderObserver:self];
	[self.connectButton setSelected:self.interface.connected];
	[self.detailTableView reloadData];
}

#pragma mark - UItablview data source / delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
	return self.interface.connected ? 10 : 1;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
	return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
	CGFloat height = 0;
	switch ((Cells_t)indexPath.row) {
		case informationsCell:
			height = 105;
			break;
		case faderCell:
			height = 230;
			break;
		case batteryCell:
			height = 135;
			break;
		case buttonCell:
			height = 90;
			break;
		case rssiCell:
			height = 150;
			break;
		case statsCell:
			height = 190;
			break;
		case colorCell:
			height = 130;
			break;
		case ledCell:
			height = 100;
			break;
		case nameCell:
			height = 130;
			break;
		case calibrationCell:
			height = 150;
			break;
	}
	return height;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
	MixfaderTableViewCell *cell;
	switch ((Cells_t)indexPath.row) {
		case informationsCell:
			cell = (MixfaderInfoTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"informationsCell"];
			break;
		case faderCell:
			cell = (MixfaderFaderTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"faderCell"];
			break;
		case batteryCell:
			cell = (MixfaderBatteryTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"batteryCell"];
			break;
		case buttonCell:
			cell = (MixfaderButtonTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"buttonCell"];
			break;
		case rssiCell:
			cell = (MixfaderRSSITableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"rssiCell"];
			break;
		case statsCell:
			cell = (MixfaderStatsTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"statsCell"];
			break;
		case colorCell:
			cell = (MixfaderColorTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"colorCell"];
			break;
		case ledCell:
			cell = (MixfaderLedTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"ledCell"];
			break;
		case nameCell:
			cell = (MixfaderNameTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"nameCell"];
			cell.parentVC = self;
			break;
		case calibrationCell:
			cell = (MixfaderCalibrationTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"calibrationCell"];
			cell.parentVC = self;
			break;
	}
	[cell setSelectionStyle:UITableViewCellSelectionStyleNone];
	[cell updateWithInterface:self.interface];
	return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
	[tableView deselectRowAtIndexPath:indexPath animated:true];
}

#pragma mark - Fader

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateFaderValue:(float)faderValue
{
	float fader = (1-faderValue) - 0.5;
	[self.knobCenterConstraint setConstant:fader * 130];
	[self.view layoutIfNeeded];
}

#pragma mark - Connection

- (IBAction)onConnectButton:(UIButton*)sender
{
	if(sender.selected)
	{
		[self.interface disconnectWithCompletion:^(MXFConnectEvent event, NSError * _Nullable error) {
			if(error)
			{
				UIAlertController *alertController = [UIAlertController
													  alertControllerWithTitle:@"Disconnect"
													  message:error.localizedDescription
													  preferredStyle:UIAlertControllerStyleAlert];
				UIAlertAction *cancelAction = [UIAlertAction
											   actionWithTitle:@"ok"
											   style:UIAlertActionStyleCancel
											   handler:nil];
				[alertController addAction:cancelAction];
				[self presentViewController:alertController animated:true completion:nil];
			}
			else
			{
				[self.detailTableView reloadData];
				[sender setSelected:false];
			}
		}];
	}
	else
	{
		[self.interface connectWithCompletion:^(MXFConnectEvent event, NSError * _Nullable error) {
			if(error)
			{
				UIAlertController *alertController = [UIAlertController
													  alertControllerWithTitle:@"Connect"
													  message:error.localizedDescription
													  preferredStyle:UIAlertControllerStyleAlert];
				UIAlertAction *cancelAction = [UIAlertAction
											   actionWithTitle:@"ok"
											   style:UIAlertActionStyleCancel
											   handler:nil];
				[alertController addAction:cancelAction];
				[self presentViewController:alertController animated:true completion:nil];
				return;
			}
			
			
			if(event == CONNECT_SUCCESS)
			{
				[sender setSelected:true];
			}
		}];
	}
}

- (void)mixfaderDidConnect:(MXFMixfaderInterface *)mixfader
{
	[self.detailTableView reloadData];
}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didFailConnect:(NSError *)error{}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeAvailability:(BOOL)available{}

- (void)mixfader:(MXFMixfaderInterface *)mixfader didDisconnect:(NSError *)error
{
	UIAlertController *alertController = [UIAlertController
										  alertControllerWithTitle:@"Disconnect"
										  message:error.localizedDescription
										  preferredStyle:UIAlertControllerStyleAlert];
	UIAlertAction *cancelAction = [UIAlertAction
								   actionWithTitle:@"ok"
								   style:UIAlertActionStyleCancel
								   handler:nil];
	[alertController addAction:cancelAction];
	[self presentViewController:alertController animated:true completion:^{
		[self.connectButton setSelected:false];
	}];
	[self.detailTableView reloadData];
}



@end
