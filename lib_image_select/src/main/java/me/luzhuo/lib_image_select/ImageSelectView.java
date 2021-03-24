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
package me.luzhuo.lib_image_select;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.luzhuo.lib_image_select.adapter.ImageSelectAdapter;
import me.luzhuo.lib_image_select.adapter.ImageShowAdapter;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageSelectCallback;
import me.luzhuo.lib_image_select.callback.OnImageShowCallback;
import me.luzhuo.lib_image_select.callback.impl.OnImageShowCallbackImpl;
import me.luzhuo.lib_image_select.enums.Type;
import me.luzhuo.lib_image_select.utils.MediaUtil;

/**
 * Description: 图片选择View
 * @Author: Luzhuo
 * @Creation Date: 2021/3/24 23:37
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ImageSelectView extends RecyclerView implements OnImageShowCallback {

    public ImageSelectView(@NonNull Context context) {
        this(context, null);
    }
    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ImageSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr);
    }

    private Adapter adapter;
    private OnImageShowCallbackImpl listener;

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ImageSelectView, defStyleAttr, 0);
        final Activity activity = (Activity) getContext();
        int spanCount = 3;
        int maxCount = 9;
        Type type = Type.Images;
        boolean onlyShow = false;
        int layout_select_add;
        int layout_select_normal;
        int layout_show_normal;

        try {
            spanCount = typedArray.getInt(R.styleable.ImageSelectView_image_span_count, 3);
            maxCount = typedArray.getInt(R.styleable.ImageSelectView_image_max_count, 9);
            int typeInt = typedArray.getInt(R.styleable.ImageSelectView_image_type, 0);
            onlyShow = typedArray.getBoolean(R.styleable.ImageSelectView_image_only_show, false);
            layout_select_add = typedArray.getResourceId(R.styleable.ImageSelectView_image_layout_select_add, -1);
            layout_select_normal = typedArray.getResourceId(R.styleable.ImageSelectView_image_layout_select_normal, -1);
            layout_show_normal = typedArray.getResourceId(R.styleable.ImageSelectView_image_layout_show_normal, -1);

            if (typeInt == 0) type = Type.Images;
            else if (typeInt == 1) type = Type.Videos;
            else if (typeInt == 2) type = Type.ALL;

        } finally {
            typedArray.recycle();
        }

        if (onlyShow) {
            if (layout_show_normal > 0) adapter = new ImageShowAdapter(layout_show_normal);
            else adapter = new ImageShowAdapter();
        } else {
            if (layout_select_add > 0 && layout_select_normal > 0) adapter = new ImageSelectAdapter(activity, type, maxCount, layout_select_add, layout_select_normal);
            else adapter = new ImageSelectAdapter(activity, type, maxCount);
        }
        this.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        this.setAdapter(adapter);

        if (adapter instanceof ImageShowAdapter) ((ImageShowAdapter) adapter).setOnImageShowCallback(this);
        else if (adapter instanceof ImageSelectAdapter) ((ImageSelectAdapter) adapter).setOnImageShowCallback(this);
    }

    /**
     * 设置图片选择的监听
     * 仅对 非只显示 模式有效
     * @param listener OnImageSelectCallback
     */
    public void setOnSelectListener(OnImageSelectCallback listener) {
        if (!(adapter instanceof ImageSelectAdapter)) return;

        ((ImageSelectAdapter) adapter).setOnSelectListener(listener);
    }

    /**
     * 显示的回调
     * @param listener OnImageShowCallbackImpl
     */
    public void setOnShowListener(OnImageShowCallbackImpl listener) {
        this.listener = listener;
    }

    /**
     * 设置数据
     */
    public void setDatas(List<ImageSelectBean> datas) {
        if (adapter instanceof ImageSelectAdapter) {
            ((ImageSelectAdapter) adapter).setDatas(datas);
        } else if (adapter instanceof ImageShowAdapter) {
            ((ImageShowAdapter) adapter).setDatas(datas);
        }
    }

    /**
     * 获取数据
     */
    public List<ImageSelectBean> getDatas() {
        if (adapter instanceof ImageSelectAdapter) {
            return ((ImageSelectAdapter) adapter).getDatas();
        } else if (adapter instanceof ImageShowAdapter) {
            return ((ImageShowAdapter) adapter).getDatas();
        }
        return null;
    }

    @Override
    public boolean onImageCallback(ImageSelectBean image, List<ImageSelectBean> images) {
        if (listener != null && listener.onImageCallback(image, images)) return true;

        MediaUtil.instance().showBigPhotoList((FragmentActivity)getContext(), images, image);
        return false;
    }

    @Override
    public boolean onVideoCallback(ImageSelectBean video) {
        if (listener != null && listener.onVideoCallback(video)) return true;

        MediaUtil.instance().showVideo(getContext(), null, video.getPath());
        return false;
    }
}
