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
//  ViewController.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 23/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderListeViewController.h"
#import "MixfaderListCellTableViewCell.h"
#import "MixfaderDetailViewController.h"

#import <MixfaderSDK_iOS/MixfaderSDK_iOS.h>

@interface MixfaderListeViewController () 

@property (nonatomic, weak) MXFMixfaderInterface *currentMixfader;

@end

@implementation MixfaderListeViewController

- (void)viewDidLoad {
	[super viewDidLoad];
	[[MXFSession defaultSession] startSession:true];
}

- (void)viewWillAppear:(BOOL)animated
{
	[super viewWillAppear:animated];
	[self.mixfadersListe reloadData];
}

- (IBAction)onScanButtonAction:(UIButton *)sender
{
	if(sender.selected)
	{
		[sender setSelected:false];
		[[MXFSession defaultSession] stopScanPeripheral:^(NSArray * _Nonnull mixfaders) {
			[self.mixfadersListe reloadData];
			[self.scanIndicatorView stopAnimating];
		}];
	}
	else
	{
		[sender setSelected:true];
		[self.scanIndicatorView startAnimating];
		[[MXFSession defaultSession] startScanWithDuration:0 change:^(MXFMixfaderInterface * _Nullable mixfader, NSError * _Nullable error) {
			if(error)
			{
				[[MXFSession defaultSession] stopScanPeripheral:nil];
				[self.scanIndicatorView stopAnimating];
				[sender setSelected:false];
				UIAlertController *alertController = [UIAlertController
													  alertControllerWithTitle:@"Scan error"
													  message:error.localizedDescription
													  preferredStyle:UIAlertControllerStyleAlert];
				UIAlertAction *cancelAction = [UIAlertAction
											   actionWithTitle:@"ok"
											   style:UIAlertActionStyleCancel
											   handler:nil];
				[alertController addAction:cancelAction];
				[self presentViewController:alertController animated:true completion:nil];
			}
			[self.mixfadersListe reloadData];
		}];
	}
}

#pragma mark - Uitableview datasource / delegate


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
	return [[[MXFSession defaultSession] mixfaders] count];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
	return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
	return 90;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
	
	MXFMixfaderInterface *device = [[[MXFSession defaultSession] mixfaders] objectAtIndex:indexPath.row];
	MixfaderListCellTableViewCell *cell = (MixfaderListCellTableViewCell *)[tableView dequeueReusableCellWithIdentifier:@"mixfaderCell"];
	[cell setSelectionStyle:UITableViewCellSelectionStyleNone];
	[cell updateWithInterface:device];
	return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
	self.currentMixfader =[[[MXFSession defaultSession] mixfaders] objectAtIndex:indexPath.row];
	[self performSegueWithIdentifier:@"detailPush" sender:self];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
	MixfaderDetailViewController *vc = segue.destinationViewController;
	vc.interface = self.currentMixfader;
}

@end
