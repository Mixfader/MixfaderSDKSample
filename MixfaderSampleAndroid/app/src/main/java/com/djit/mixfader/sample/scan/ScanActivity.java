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

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.sample.BaseActivity;
import com.djit.mixfader.sample.R;
import com.djit.mixfader.sample.mixfader.MixFaderActivity;
import com.djit.mixfader.sample.mixfader.SampleUtils;
import com.djit.mixfader.scanner.MixFaderScanner;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends BaseActivity implements ScannerMvp.View {

    public static final String EXTRA_MIXFADER_NAME = "MixFaderActivity.extras.mixfader.hashcode";

    private List<MixFaderInterface> mixfaders;
    private MixFaderAdapter mixfaderAdapter;
    private Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        /**
         * Bluetooth scanner
         */
        scanner = new Scanner(this);

        /**
         * Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * UI components
         */
        Switch scannerSwitch = (Switch) findViewById(R.id.mxf_scanner_switch);
        assert scannerSwitch != null;
        scannerSwitch.setOnCheckedChangeListener(onScannerSwitchCheckedChangeListener);

        /**
         * FAB action
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(onFabClickListener);

        /**
         * RecyclerView
         */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mxf_recycler_view);
        assert recyclerView != null;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        this.mixfaders = new ArrayList<>();
        mixfaderAdapter = new MixFaderAdapter(mixfaders);
        mixfaderAdapter.setOnItemClickListener(onMixFaderClickListener);
        recyclerView.setAdapter(mixfaderAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.onResume();
    }

    @Override
    protected void onPause() {
        scanner.onPause();
        super.onPause();
    }

    /**
     * Handles MixFader items cliks
     */
    private MixFaderAdapter.OnItemClickListener onMixFaderClickListener = new MixFaderAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            final MixFaderInterface mixFaderInterface = mixfaders.get(position);
            MixFaderActivity.startForMixFader(ScanActivity.this.getApplicationContext(), mixFaderInterface);
        }
    };

    /**
     * Launches the information popup
     */
    private View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SampleInformationDialogFragment.newInstance().show(ScanActivity.this.getSupportFragmentManager(), "SampleInformationDialogFragment");
        }
    };

    /**
     * Enables / disables bluetooth scanning
     */
    private Switch.OnCheckedChangeListener onScannerSwitchCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // checks for the proper scanner initialization in case of enabling request
            if(isChecked) {
                final MixFaderScanner.InitResult initResult = scanner.init(getApplicationContext());
                if (initResult != MixFaderScanner.InitResult.SUCCESS) {
                    Toast.makeText(ScanActivity.this, "MixFaderScanner could not be initialized properly... error = " + SampleUtils.getScannerErrorString(initResult), Toast.LENGTH_LONG).show();
                }
            }

            scanner.setScannerState(isChecked);
        }
    };

    /**
     * Adds a {@link MixFaderInterface} to the list
     *
     * @param mixFaderInterface the instance to add
     */
    @Override
    public void display(MixFaderInterface mixFaderInterface) {
        mixfaders.add(mixFaderInterface);
        mixfaderAdapter.notifyDataSetChanged();
    }

    /**
     * Hides a {@link MixFaderInterface} from the list
     *
     * @param mixFaderInterface the instance to hide
     */
    @Override
    public void hide(MixFaderInterface mixFaderInterface) {
        mixfaders.remove(mixFaderInterface);
        mixfaderAdapter.notifyDataSetChanged();
    }
}
