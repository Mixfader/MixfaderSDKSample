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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djit.mixfader.MixFaderInterface;
import com.djit.mixfader.sample.R;

import java.util.List;

/* Package */ class MixFaderAdapter extends RecyclerView.Adapter<MixFaderAdapter.ViewHolder> {

    private List<MixFaderInterface> mixfaders;
    private OnItemClickListener onItemClickListener;

    /**
     * The {@link RecyclerView.ViewHolder} that retains our views.
     */
    /* Package */ static class ViewHolder extends RecyclerView.ViewHolder {
        /* Package */ TextView mixfaderNameTextView;

        /* Package */ ViewHolder(View v) {
            super(v);
            mixfaderNameTextView = (TextView) v.findViewById(R.id.mxf_mixfader_name);
        }
    }

    /**
     * Builds the {@link RecyclerView.Adapter}.
     *
     * @param mixfaders the {@link MixFaderInterface} list to display.
     */
    /* Package */ MixFaderAdapter(List<MixFaderInterface> mixfaders) {
        this.mixfaders = mixfaders;
    }

    @Override
    public MixFaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mixfader_item_view, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, vh.getAdapterPosition());
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mixfaderNameTextView.setText(mixfaders.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mixfaders.size();
    }

    /* Package */ void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /* Package */ interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
