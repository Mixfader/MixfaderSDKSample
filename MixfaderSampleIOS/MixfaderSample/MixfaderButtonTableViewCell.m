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
//  MixfaderButtonTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderButtonTableViewCell.h"

@interface MixfaderButtonTableViewCell () <MXFButtonObserver>

@property (nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderButtonTableViewCell

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
		[self.interface removeButtonObserver:self];
	self.interface = interface;
	[self.interface addButtonObserver:self];
	[self.buttonSwitch setOn:self.interface.buttonPushed];
}

#pragma mark - Button observer

- (void)mixfader:(nonnull MXFMixfaderInterface *)mixfader didChangeButtonState:(BOOL)buttonPush
{
	[self.buttonSwitch setOn:buttonPush];
}

@end
