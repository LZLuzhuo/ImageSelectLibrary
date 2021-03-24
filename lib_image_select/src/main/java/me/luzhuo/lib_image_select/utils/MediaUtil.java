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
package me.luzhuo.lib_image_select.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.bean.ImageShowBean;
import me.luzhuo.lib_image_select.enums.Type;

public class MediaUtil {
    private MediaUtil(){}
    public static MediaUtil instance(){
        return Instance.instance;
    }
    private static class Instance{
        private static final MediaUtil instance = new MediaUtil();
    }

    public void showBigPhotoList(Activity activity, List<ImageShowBean> imageSelectBeans, ImageShowBean index) {
        if (index.type != Type.Images) return;

        List<String> list = new ArrayList<>();
        Iterator<ImageShowBean> iterator = imageSelectBeans.iterator();
        while (iterator.hasNext()) {
            ImageShowBean imageSelectBean = iterator.next();
            if (imageSelectBean.type == Type.Images)  list.add(imageSelectBean.imageUrl);
        }

        int indexInt = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == index.imageUrl) indexInt = i;
        }

        showBigPhotoList(activity, list, indexInt);
    }

    public void showBigPhotoList(Activity activity, List<ImageSelectBean> imageSelectBeans, ImageSelectBean index) {
        if (index.type != Type.Images) return;

        List<String> list = new ArrayList<>();
        Iterator<ImageSelectBean> iterator = imageSelectBeans.iterator();
        while (iterator.hasNext()) {
            ImageSelectBean imageSelectBean = iterator.next();
            if (imageSelectBean.type == Type.Images)  list.add(imageSelectBean.getPath());
        }

        int indexInt = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == index.getPath()) indexInt = i;
        }

        showBigPhotoList(activity, list, indexInt);
    }

    public void showBigPhotoList(Activity activity, List<String> images, int index) {
        ImagePreview
                .getInstance()
                .setContext(activity)
                .setIndex(index)
                .setImageList(images)
                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                // 缩放动画时长，单位ms
                .setZoomTransitionDuration(300)
                // 是否启用上拉/下拉关闭。默认不启用
                .setEnableDragClose(true)
                .start();
    }

    public void showBigPhotoList(Activity activity, List<String> images) {
        showBigPhotoList(activity, images, 0);
    }

    public void showVideo(Context context, String videoCover, String videoUrl) {
        // 视频
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(VideoPlayerActivity.CoverUrl, videoCover);
        intent.putExtra(VideoPlayerActivity.VideoUrl, videoUrl);
        context.startActivity(intent);
    }

}
