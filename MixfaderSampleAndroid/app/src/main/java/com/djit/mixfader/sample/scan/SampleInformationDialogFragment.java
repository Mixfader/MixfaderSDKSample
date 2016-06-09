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

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.djit.mixfader.sample.R;

/**
 * A {@link DialogFragment} to give the user some information about what is going on.
 */
@SuppressWarnings("WeakerAccess")
public class SampleInformationDialogFragment extends DialogFragment {

    /**
     * Create a new instance of {@link SampleInformationDialogFragment}.
     *
     * @return the newly created {@link SampleInformationDialogFragment}.
     */
    public static SampleInformationDialogFragment newInstance() {
        return new SampleInformationDialogFragment();
    }

    /**
     * Default Constructor.
     * <p/>
     * lint [ValidFragment]
     * http://developer.android.com/reference/android/app/Fragment.html#Fragment()
     * Every fragment must have an empty constructor, so it can be instantiated when restoring its activity's state.
     */
    public SampleInformationDialogFragment() {
        // Default constructor.
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context context = getContext();

        @SuppressLint("InflateParams")
        final View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_information, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(rootView)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        return alertDialog;
    }
}
