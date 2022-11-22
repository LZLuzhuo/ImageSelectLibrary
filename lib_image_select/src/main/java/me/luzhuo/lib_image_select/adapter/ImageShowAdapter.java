/* Copyright 2021 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_image_select.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_image_select.R;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageShowCallback;
import me.luzhuo.lib_image_select.enums.Type;

public class ImageShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageSelectBean> mDatas = new ArrayList<>();
    private Context context;
    private int layout_show;
    private OnImageShowCallback callback;

    public List<ImageSelectBean> getDatas(){
        return mDatas;
    }

    public void setDatas(List<ImageSelectBean> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public ImageShowAdapter() {
        this(R.layout.image_item_select_show);
    }

    public ImageShowAdapter(@LayoutRes int layout_show) {
        this.layout_show = layout_show == -1 ? R.layout.image_item_select_show : layout_show;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new RecyclerHolder(LayoutInflater.from(context).inflate(layout_show, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).bindData(mDatas.get(position));
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        public ImageView image_select_iv;
        public ImageView image_select_play;

        public RecyclerHolder(View itemView) {
            super(itemView);
            image_select_iv = itemView.findViewById(R.id.image_select_iv);
            image_select_play = itemView.findViewById(R.id.image_select_play);

            image_select_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Type type = mDatas.get(getLayoutPosition()).type;

                    if (Type.Images == type) {
                        if (callback != null) callback.onImageCallback(mDatas.get(getLayoutPosition()), mDatas);
                    } else if (Type.Videos == type) {
                        if (callback != null) callback.onVideoCallback(mDatas.get(getLayoutPosition()));
                    }
                }
            });
        }

        public void bindData(ImageSelectBean data) {
            Glide.with(context).load(data.getPath()).into(image_select_iv);

            if (data.type == Type.Videos) image_select_play.setVisibility(View.VISIBLE);
            else image_select_play.setVisibility(View.GONE);
        }
    }

    public void setOnImageShowCallback(OnImageShowCallback callback) {
        this.callback = callback;
    }
}
