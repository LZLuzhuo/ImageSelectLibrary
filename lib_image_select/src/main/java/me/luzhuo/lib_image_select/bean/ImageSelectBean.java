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
package me.luzhuo.lib_image_select.bean;

import androidx.annotation.Nullable;

import me.luzhuo.lib_image_select.enums.Type;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/7 14:05
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ImageSelectBean {
    // 压缩图片地址
    private String compressPath;
    // 剪裁图片地址
    private String cutPath;
    // 原图地址
    private String originalPath;
    // 原始图片地址
    private String path;
    // Android Q 特有Path
    private String androidQToPath;
    public int width, height;
    // 音视频文件的播放时长
    public long duration;
    public Type type;

    // 附加数据
    public Object tag;

    /**
     * 内部调用
     * 最完整
     * @param path 图片原始地址
     * @param originalPath 图片原始地址
     * @param cutPath 剪裁图片地址
     * @param compressPath 压缩图片地址
     * @param androidQToPath Android Q 特有的地址
     * @param width 宽度
     * @param height 高度
     * @param duration 音视频文件的播放时长
     * @param type 文件类型
     */
    public ImageSelectBean(String path, @Nullable String originalPath, @Nullable String cutPath, @Nullable String compressPath, @Nullable String androidQToPath,
                           int width, int height, long duration, Type type) {
        this.path = path;
        this.originalPath = originalPath;
        this.cutPath = cutPath;
        this.compressPath = compressPath;
        this.androidQToPath = androidQToPath;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.type = type;
    }

    /**
     * 供外部设置图片, 仅用于显示模式
     * @param path 本地 或 网络图片
     */
    public ImageSelectBean(String path, Object tag) {
        this(path, null, null, null, null, 0, 0, 0, Type.Images);
        this.tag = tag;
    }

    public String getPath(){
        String currentPath;
        if (cutPath != null) {
            currentPath = cutPath;
        }else if (originalPath != null) {
            currentPath = originalPath;
        } else if (compressPath != null) {
            currentPath = compressPath;
        } else {
            currentPath = path;
        }

        if(currentPath == null){
            currentPath = androidQToPath;
        }

        return currentPath;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "compressPath='" + compressPath + '\'' +
                ", cutPath='" + cutPath + '\'' +
                ", originalPath='" + originalPath + '\'' +
                ", path='" + path + '\'' +
                ", androidQToPath='" + androidQToPath + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", duration=" + duration +
                ", type=" + type +
                ", tag=" + tag +
                '}';
    }
}
