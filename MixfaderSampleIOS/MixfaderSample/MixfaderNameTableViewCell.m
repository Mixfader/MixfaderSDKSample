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
//  MixfaderNameTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderNameTableViewCell.h"

@interface MixfaderNameTableViewCell () <MXFRenameObserver>

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end


@implementation MixfaderNameTableViewCell

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
	   [self.interface removeRenameObserver:self];
	self.interface = interface;
	[self.interface addRenameObserver:self];
	self.nameLabel.text = self.interface.name;
}

- (IBAction)onRenameButton:(id)sender {
	UIAlertController * renameAlert=   [UIAlertController alertControllerWithTitle:@"Rename" message:@"Entre the new Mixfader name" preferredStyle:UIAlertControllerStyleAlert];
	UIAlertAction* ok = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
		[self.interface rename:[renameAlert.textFields[0] text]  completion:^(BOOL success, NSError * _Nullable error, NSString * _Nullable name) {
			if(error)
			{
				[renameAlert setMessage:error.localizedDescription];
				[self.parentVC presentViewController:renameAlert animated:YES completion:nil];
			}
		}];
	}];
	UIAlertAction* cancel = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleDefault handler:nil];
	[renameAlert addAction:ok];
	[renameAlert addAction:cancel];
	[renameAlert addTextFieldWithConfigurationHandler:^(UITextField *textField) {
		textField.placeholder = self.interface.name;
	}];
	
	[self.parentVC presentViewController:renameAlert animated:YES completion:nil];
}

#pragma mark - Rename observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didUpdateName:(NSString *)name
{
	self.nameLabel.text = name;
}

@end
