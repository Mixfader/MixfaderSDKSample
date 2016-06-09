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

package com.djit.mixfader.sample;

import android.util.SparseArray;

import com.djit.mixfader.MixFaderInterface;

public class MixFaderProvider {

    private static MixFaderProvider mMixFaderProvider = null;
    private static SparseArray<MixFaderInterface> mMixFaderDevicesList = null;

    private MixFaderProvider(){

    }

    public static MixFaderProvider getInstance(){
        if(mMixFaderProvider == null){
            mMixFaderProvider = new MixFaderProvider();
            mMixFaderDevicesList = new SparseArray<>();
        }
        return mMixFaderProvider;
    }

    public boolean addDevice(MixFaderInterface mixFaderInterface){
        if(mixFaderInterface.getName() != null){
            mMixFaderDevicesList.put(mixFaderInterface.getName().hashCode(), mixFaderInterface);
            return true;
        }
        return false;
    }

    public void removeDevice(MixFaderInterface mixFaderInterface){
        if(mixFaderInterface.getName() != null){
            mMixFaderDevicesList.remove(mixFaderInterface.getName().hashCode());
        }
    }

    public MixFaderInterface getDevice(int hashCode){
        return mMixFaderDevicesList.get(hashCode);
    }

    public static void release(){
        if(mMixFaderDevicesList != null){
            mMixFaderDevicesList.clear();
            mMixFaderDevicesList = null;
        }

        mMixFaderProvider = null;
    }
}
