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

package com.djit.mixfader.sample.scan;

import android.content.Context;

import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.scanner.MixFaderScanner;

/* Package */ interface ScannerMvp {

    /**
     * {@link com.djit.mixfader.scanner.MixFaderScanner) is our "model" in this case :)
     */
    //interface Model {
    //
    //}

    /**
     * Represents a view that displays Scanner data
     */
    interface View {
        void display(MixFaderInterface mixFaderInterface);

        void hide(MixFaderInterface mixFaderInterface);
    }

    /**
     * Represents the business class that receive UI inputs and deals
     * with {@link com.djit.mixfader.scanner.MixFaderScanner} instance.
     */
    interface Presenter {
        MixFaderScanner.InitResult init(Context context);

        void onResume();

        void onPause();

        void setScannerState(boolean enable);
    }
}
