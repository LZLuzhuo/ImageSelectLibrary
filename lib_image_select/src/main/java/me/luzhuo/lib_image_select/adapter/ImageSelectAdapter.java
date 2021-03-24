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

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.luzhuo.lib_image_select.utils.ImageSelectManager;
import me.luzhuo.lib_image_select.R;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageSelectCallback;
import me.luzhuo.lib_image_select.callback.OnImageShowCallback;
import me.luzhuo.lib_image_select.callback.SelectCallBack;
import me.luzhuo.lib_image_select.enums.Type;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/7 15:09
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ImageSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SelectCallBack {
    private Context context;
    private List<ImageSelectBean> mDatas = new ArrayList<>();
    private static final int TYPE_NOMAL = 1, TYPE_ADD = 2;
    private Type type;
    private ImageSelectManager select;
    private int maxCount;
    private int layout_add;
    private int layout_nomal;

    private OnImageShowCallback callback;
    private OnImageSelectCallback adapterSelectListener;

    public ImageSelectAdapter(Activity activity, Type type, int maxCount) {
        this(activity, type, maxCount, R.layout.image_item_select_add, R.layout.image_item_select_normal);
    }

    public ImageSelectAdapter(Activity activity, Type type, int maxCount, @LayoutRes int layout_add, @LayoutRes int layout_nomal) {
        this.context = activity.getBaseContext();
        this.type = type;
        this.maxCount = maxCount;
        this.layout_add = layout_add;
        this.layout_nomal = layout_nomal;
        this.select = new ImageSelectManager(activity).original(true).onSetCallbackListener(this);
    }

    public ImageSelectAdapter(Fragment fragment, Type type, int maxCount) {
        this(fragment, type, maxCount, R.layout.image_item_select_add, R.layout.image_item_select_normal);
    }

    public ImageSelectAdapter(Fragment fragment, Type type, int maxCount, @LayoutRes int layout_add, @LayoutRes int layout_nomal) {
        this.context = fragment.getContext();
        this.type = type;
        this.maxCount = maxCount;
        this.layout_add = layout_add;
        this.layout_nomal = layout_nomal;
        this.select = new ImageSelectManager(fragment).original(true).onSetCallbackListener(this);
    }

    public List<ImageSelectBean> getDatas(){
        return mDatas;
    }

    public void setDatas(List<ImageSelectBean> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ADD:
                return new AddHolder(LayoutInflater.from(parent.getContext()).inflate(layout_add, parent, false));
            case TYPE_NOMAL:
            default:
                return new NomalHolder(LayoutInflater.from(parent.getContext()).inflate(layout_nomal, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() >= maxCount ? mDatas.size() : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) return TYPE_ADD;
        else return TYPE_NOMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ADD:
                ((AddHolder) holder).bindData();
                break;
            case TYPE_NOMAL:
            default:
                ((NomalHolder) holder).bindData(mDatas.get(position));
                break;
        }
    }

    public class AddHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView image_select_type_content;
        public View image_select_btn;

        public AddHolder(View itemView) {
            super(itemView);
            image_select_type_content = itemView.findViewById(R.id.image_select_type_content);
            image_select_btn = itemView.findViewById(R.id.image_select_btn);

            image_select_btn.setOnClickListener(this);
        }

        public void bindData() {
            if (type == Type.Images) image_select_type_content.setText("上传图片");
            else if (type == Type.Videos) image_select_type_content.setText("上传视频");
            else if (type == Type.ALL) image_select_type_content.setText("上传文件");
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.image_select_btn) {
                if (type == Type.Images) select.openImage(maxCount - mDatas.size());
                else if (type == Type.Videos) select.openVideo(maxCount - mDatas.size());
                else if (type == Type.ALL) select.openAll(maxCount - mDatas.size());
            }
        }
    }

    @Override
    public void onSelect(List<ImageSelectBean> datas) {
        if(datas != null) mDatas.addAll(datas);
        notifyDataSetChanged();

        if(adapterSelectListener != null) adapterSelectListener.onSelect(datas);
    }

    @Override
    public void onCancel() { }

    private class NomalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image_select_delete;
        public ImageView image_select_iv;
        public ImageView image_select_play;

        public NomalHolder(View itemView) {
            super(itemView);
            image_select_delete = itemView.findViewById(R.id.image_select_delete);
            image_select_iv = itemView.findViewById(R.id.image_select_iv);
            image_select_play = itemView.findViewById(R.id.image_select_play);

            image_select_delete.setOnClickListener(this);
            image_select_iv.setOnClickListener(this);
        }

        public void bindData(ImageSelectBean data) {
            // 图片
            if(data.type == Type.Images) Glide.with(context).load(data.getPath()).into(image_select_iv);
            // 视频
            else if(data.type == Type.Videos) Glide.with(context).load(data.getPath()).into(image_select_iv);

            if (data.type == Type.Videos) image_select_play.setVisibility(View.VISIBLE);
            else image_select_play.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_select_delete) {
                ImageSelectBean remove = mDatas.remove(getLayoutPosition());
                notifyDataSetChanged();

                if(adapterSelectListener != null) adapterSelectListener.onDelete(remove);
            } else if (v.getId() == R.id.image_select_iv) {
                Type type = mDatas.get(getLayoutPosition()).type;

                if (Type.Images == type) {
                    if (callback != null) callback.onImageCallback(mDatas.get(getLayoutPosition()), mDatas);
                } else if (Type.Videos == type) {
                    if (callback != null) callback.onVideoCallback(mDatas.get(getLayoutPosition()));
                }
            }
        }
    }

    public void setOnSelectListener(OnImageSelectCallback listener) {
        this.adapterSelectListener = listener;
    }

    public void setOnImageShowCallback(OnImageShowCallback callback) {
        this.callback = callback;
    }
}