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
//  MixfaderFaderTableViewCell.h
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 24/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//
#import "MixfaderTableViewCell.h"

@interface MixfaderFaderTableViewCell : MixfaderTableViewCell

@property (weak, nonatomic) IBOutlet UISwitch *enableNotificationSwitch;
@property (weak, nonatomic) IBOutlet UISwitch *reverseCrossfaderSwitch;
@property (weak, nonatomic) IBOutlet UISegmentedControl *modeSegmentControl;

@end
