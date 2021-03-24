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
package me.luzhuo.lib_image_select.callback.impl;

import java.util.List;

import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageShowCallback;

public class OnImageShowCallbackImpl implements OnImageShowCallback {
    /**
     * 展示图片的回调
     * @param image 图片文件
     * @param images 图片文件列表
     * @return false 执行默认操作, true不执行默认操作
     */
    @Override
    public boolean onImageCallback(ImageSelectBean image, List<ImageSelectBean> images) {
        return false;
    }

    /**
     * 展示视频的回调
     * @param video 视频文件
     * @return false 执行默认操作, true不执行默认操作
     */
    @Override
    public boolean onVideoCallback(ImageSelectBean video) {
        return false;
    }
}
