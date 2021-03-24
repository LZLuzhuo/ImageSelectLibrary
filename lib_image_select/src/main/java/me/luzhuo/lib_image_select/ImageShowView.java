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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_image_select.adapter.ImageTitleShowAdapter;
import me.luzhuo.lib_image_select.bean.ImageShowBean;
import me.luzhuo.lib_image_select.callback.OnImageTitleShowCallback;
import me.luzhuo.lib_image_select.callback.impl.OnImageTitleShowCallbackImpl;
import me.luzhuo.lib_image_select.utils.MediaUtil;

/**
 * Description: 图片展示View
 * @Author: Luzhuo
 * @Creation Date: 2021/3/24 23:37
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ImageShowView extends RecyclerView implements OnImageTitleShowCallback {

    public ImageShowView(@NonNull Context context) {
        this(context, null);
    }
    public ImageShowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ImageShowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr);
    }

    private Adapter adapter;
    private OnImageTitleShowCallbackImpl listener;

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ImageShowView, defStyleAttr, 0);
        int spanCount = 3;
        int layout_show_normal;

        try {
            spanCount = typedArray.getInt(R.styleable.ImageShowView_image_span_count, 3);
            layout_show_normal = typedArray.getResourceId(R.styleable.ImageShowView_image_layout_show_normal, -1);
        } finally {
            typedArray.recycle();
        }

        if (layout_show_normal > 0) adapter = new ImageTitleShowAdapter(layout_show_normal);
        else adapter = new ImageTitleShowAdapter();
        this.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        this.setAdapter(adapter);

        ((ImageTitleShowAdapter) adapter).setOnImageShowCallback(this);
    }

    /**
     * 显示的回调
     * @param listener OnImageShowCallbackImpl
     */
    public void setOnShowListener(OnImageTitleShowCallbackImpl listener) {
        this.listener = listener;
    }

    /**
     * 设置数据
     */
    public void setDatas(List<ImageShowBean> datas) {
        if (adapter instanceof ImageTitleShowAdapter) {
            ((ImageTitleShowAdapter) adapter).setDatas(datas);
        }
    }

    /**
     * 获取数据
     */
    public List<ImageShowBean> getDatas() {
        if (adapter instanceof ImageTitleShowAdapter) {
            return ((ImageTitleShowAdapter) adapter).getDatas();
        }
        return null;
    }

    @Override
    public boolean onImageCallback(ImageShowBean image, List<ImageShowBean> images) {
        if (listener != null && listener.onImageCallback(image, images)) return true;

        MediaUtil.instance().showBigPhotoList((FragmentActivity)getContext(), images, image);
        return false;
    }

    @Override
    public boolean onVideoCallback(ImageShowBean video) {
        if (listener != null && listener.onVideoCallback(video)) return true;

        MediaUtil.instance().showVideo(getContext(), video.coverUrl, video.videoUrl);
        return false;
    }
}
