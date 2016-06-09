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
//  MixfaderInfoTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 24/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderInfoTableViewCell.h"

@implementation MixfaderInfoTableViewCell

- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	self.softwareVersionLabel.text = [NSString stringWithFormat:@"Software version : %@",interface.softwareVersion];
	self.hardwareVersionLabel.text = [NSString stringWithFormat:@"Hardware version : %@",interface.hardwareVersion];
	self.serialNumberLabel.text = [NSString stringWithFormat:@"Serial : %@",interface.serialNumber];
}

@end
