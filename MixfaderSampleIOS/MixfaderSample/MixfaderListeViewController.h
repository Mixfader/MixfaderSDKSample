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
//  ViewController.h
//  MixfaderSample
//
//  Created by Jean-baptiste Fabre on 23/05/2016.
//  Copyright © 2016 djit. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MixfaderListeViewController : UIViewController <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, weak) IBOutlet UITableView *mixfadersListe;
@property (nonatomic, weak) IBOutlet UIView *scanView;
@property (nonatomic, weak) IBOutlet UIActivityIndicatorView *scanIndicatorView;
@property (nonatomic, weak) IBOutlet UIButton *scanButton;

@end

