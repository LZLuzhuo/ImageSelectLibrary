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
package me.luzhuo.lib_image_select.callback;

import java.util.List;

import me.luzhuo.lib_image_select.bean.ImageSelectBean;

/**
 * Description: 图片展示回调
 * @Author: Luzhuo
 * @Creation Date: 2021/3/25 0:00
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public interface OnImageShowCallback {
    public boolean onImageCallback(ImageSelectBean image, List<ImageSelectBean> images);
    public boolean onVideoCallback(ImageSelectBean video);
}