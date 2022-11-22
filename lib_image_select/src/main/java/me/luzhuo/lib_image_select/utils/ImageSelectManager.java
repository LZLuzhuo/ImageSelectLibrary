/* Copyright 2020 Luzhuo. All rights reserved.
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
import android.content.pm.ActivityInfo;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.ArrayList;
import java.util.List;

import me.luzhuo.lib_core.math.bit.BitFilter;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.SelectCallBack;
import me.luzhuo.lib_image_select.enums.Type;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/7 10:38
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ImageSelectManager {
    private Context context;
    private Activity activity;
    private Fragment fragment;
    private boolean original = true;
    private SelectCallBack callBack;

    public ImageSelectManager(Activity activity) {
        this.activity = activity;
        this.context = activity.getBaseContext();
    }

    public ImageSelectManager(Fragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    private PictureSelector getEngin() {
        return activity != null ? PictureSelector.create(activity) : PictureSelector.create(fragment);
    }

    /**
     * 是否支持原图
     * @param isOriginal 默认支持true
     */
    public ImageSelectManager original(boolean isOriginal) {
        this.original = isOriginal;
        return this;
    }

    public ImageSelectManager onSetCallbackListener(SelectCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 选择头像的图片
     * 单图片选择 + 圆形裁图
     */
    public void openHeaderImage(boolean showCamera){
        getEngin()
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(showCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(true)// 是否裁剪
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(new MyResultCallback(callBack));
    }

    public void openHeaderImage(){
        openHeaderImage(false);
    }

    /**
     * 选择的图片会压缩之后返回
     */
    public void openImage(int maxCount, boolean showCamera){
        getEngin()
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isUseCustomCamera(true)// 是否使用自定义相机
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxCount)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(original)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(maxCount <= 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isCamera(showCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isGif(false)
                .minimumCompressSize(100)
                .forResult(new MyResultCallback(callBack));
    }

    public void openImage(int maxCount){
        openImage(maxCount, false);
    }

    private BitFilter bit = new BitFilter();
    public static final int Images = 0x02;
    public static final int Videos = 0x04;
    public static final int Gif = 0x08;
    public static final int Audio = 0x10;
    /**
     * 根据标记选择类型
     */
    public void openMediaWithFlags(int flags, int maxCount, boolean showCamera) {
        int gallery = PictureMimeType.ofAll();
        if (bit.check(flags, 1) && bit.check(flags, 2)) gallery = PictureMimeType.ofAll(); // Images|Videos
        else if (Images == flags) gallery = PictureMimeType.ofImage();
        else if (Videos == flags) gallery = PictureMimeType.ofVideo();
        else if (Gif == flags) gallery = PictureMimeType.ofImage();
        else if ((Images|Gif) == flags) gallery = PictureMimeType.ofImage();
        else if (Audio == flags) gallery = PictureMimeType.ofAudio();

        getEngin()
                .openGallery(gallery)
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isUseCustomCamera(true)// 是否使用自定义相机
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxCount)// 最大图片选择数量
                .maxVideoSelectNum(maxCount)
                .minSelectNum(0)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(original)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(maxCount <= 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isCamera(showCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isGif(bit.check(Gif, 3))
                .minimumCompressSize(100)
                .forResult(new MyResultCallback(callBack));
    }

    public void openMediaWithFlags(int flags, int maxCount) {
        openMediaWithFlags(flags, maxCount, false);
    }

    /**
     * 选择的视频不具有压缩功能
     */
    public void openVideo(int maxCount, boolean showCamera){
        getEngin()
                .openGallery(PictureMimeType.ofVideo())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxVideoSelectNum(maxCount) // 视频最大选择数量
                .minVideoSelectNum(1)// 视频最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(maxCount <= 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                .isCamera(showCamera)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isGif(false)// 是否显示gif图片
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(new MyResultCallback(callBack));
    }

    public void openVideo(int maxCount){
        openVideo(maxCount, false);
    }

    /**
     * 只支持选择图片 视频 音频
     */
    public void openAll(int maxCount, boolean showCamera){
        getEngin()
                .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .isWeChatStyle(true)// 是否开启微信图片选择风格
                .isUseCustomCamera(true)// 是否使用自定义相机
                .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .maxSelectNum(maxCount)// 最大图片选择数量
                .maxVideoSelectNum(maxCount) // 视频最大选择数量
                .imageSpanCount(4)// 每行显示个数
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(original)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                .selectionMode(maxCount <= 1 ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isPreviewVideo(true)// 是否可预览视频
                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
                .isEnablePreviewAudio(true) // 是否可播放音频
                .isCamera(showCamera)// 是否显示拍照按钮
                //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .isGif(true)// 是否显示gif图片
                .isOpenClickSound(false)// 是否开启点击声音
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于多少kb的图片不压缩
                .forResult(new MyResultCallback(callBack));
    }

    public void openAll(int maxCount){
        openAll(maxCount, false);
    }

    static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private SelectCallBack callBack;

        public MyResultCallback(SelectCallBack callBack) {
            super();
            this.callBack = callBack;
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            if(callBack != null) callBack.onSelect(getFiles(result));
        }

        @Override
        public void onCancel() {
            if(callBack != null) callBack.onCancel();
        }

        public List<ImageSelectBean> getFiles(List<LocalMedia> mediaList) {
            if(mediaList == null) return null;

            List<ImageSelectBean> files = new ArrayList<>();
            for (LocalMedia media : mediaList) {
                files.add(new ImageSelectBean(media.getPath(), media.getOriginalPath(), media.getCutPath(), media.getCompressPath(), media.getAndroidQToPath(), media.getWidth(), media.getHeight(), media.getDuration(), getType(media.getMimeType())));
            }
            return files;
        }

        private Type getType(String mimeType) {
            if(mimeType.equals("image/jpeg")) return Type.Images;
            if(mimeType.equals("image/gif")) return Type.Images;
            if(mimeType.equals("video/mp4")) return Type.Videos;
            return Type.Images;
        }
    }
}
