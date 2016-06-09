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
import com.djit.mixfader.sample.MixFaderProvider;
import com.djit.mixfader.scanner.MixFaderScanner;

/* Package */ class Scanner implements ScannerMvp.Presenter {

    private MixFaderScanner mixfaderScanner;
    private ScannerMvp.View scannerView;

    /* Package */ Scanner(ScannerMvp.View scannerView) {
        this.scannerView = scannerView;

        // Init scanner
        mixfaderScanner = MixFaderScanner.getInstance();
    }

    @Override
    public MixFaderScanner.InitResult init(Context context) {
        return mixfaderScanner.initialize(context);
    }

    @Override
    public void onResume() {
        mixfaderScanner.setScannerEventsListener(scannerEventsListener);
    }

    @Override
    public void onPause() {
        mixfaderScanner.setScannerEventsListener(null);
    }

    /**
     * Deals with {@link MixFaderScanner} current state
     * @param enable the new state to handle
     */
    @Override
    public void setScannerState(boolean enable) {
        if(enable && !mixfaderScanner.isScanning()) {
            mixfaderScanner.startScan();
        } else if(!enable && mixfaderScanner.isScanning()) {
            mixfaderScanner.stopScan();
        }
    }

    /**
     * Listens to {@link MixFaderScanner} events.
     */
    private MixFaderScanner.ScannerEventsListener scannerEventsListener = new MixFaderScanner.ScannerEventsListener() {
        @Override
        public void onMixFaderFound(MixFaderInterface mixFaderInterface) {
            if(MixFaderProvider.getInstance().addDevice(mixFaderInterface)) {
               scannerView.display(mixFaderInterface);
            }
        }

        @Override
        public void onMixfaderLost(MixFaderInterface mixFaderInterface) {
            scannerView.hide(mixFaderInterface);
            MixFaderProvider.getInstance().removeDevice(mixFaderInterface);
        }
    };
}
