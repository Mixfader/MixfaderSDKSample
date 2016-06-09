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
//  MixfaderColorTableViewCell.m
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 25/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import "MixfaderColorTableViewCell.h"

@interface MixfaderColorTableViewCell () <MXFColorObserver>

@property(nonatomic, weak) MXFMixfaderInterface *interface;

@end

@implementation MixfaderColorTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
	[self.colorView.layer setCornerRadius:12.5f];
	[self.colorView.layer setMasksToBounds:true];
}


- (void)updateWithInterface:(MXFMixfaderInterface *)interface
{
	if(self.interface)
		[self.interface removeColorObserver:self];
	self.interface = interface;
	[self.interface addColorObserver:self];
	self.colorView.backgroundColor = self.interface.color;
}

- (IBAction)onColorChange:(id)sender {
	CGFloat hue = ( arc4random() % 256 / 256.0 );
	CGFloat saturation = ( arc4random() % 128 / 256.0 ) + 0.5;
	CGFloat brightness = ( arc4random() % 128 / 256.0 ) + 0.5;
	UIColor *color = [UIColor colorWithHue:hue saturation:saturation brightness:brightness alpha:1];
	
	[self.interface updateColor:color completion:^(NSError * _Nullable error) {
		if(error)
		{
			NSLog(@"%@",error.localizedDescription);
		}
		self.colorView.backgroundColor = self.interface.color;
	}];
}

#pragma mark - Color observer

- (void)mixfader:(MXFMixfaderInterface *)mixfader didChangeColor:(UIColor *)color
{
	self.colorView.backgroundColor = color;
}

@end
