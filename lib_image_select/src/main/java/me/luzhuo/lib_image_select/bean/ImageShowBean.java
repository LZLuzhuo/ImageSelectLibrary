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
package me.luzhuo.lib_image_select.bean;

import me.luzhuo.lib_image_select.enums.Type;

/**
 * Description:
 * @Author: Luzhuo
 * @Creation Date: 2021/3/25 0:24
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ImageShowBean {
    /**
     * 标题
     */
    public String title;

    /**
     * 资源类型
     */
    public Type type;

    /**
     * 图片的网络路径
     */
    public String imageUrl;

    /**
     * 视频封面的网络路径
     */
    public String coverUrl;

    /**
     * 视频的网路路径
     */
    public String videoUrl;

    public Object tag;

    /**
     * 图片构造函数
     * @param title 标题
     * @param imageUrl 网路图片路径
     */
    public ImageShowBean(String title, String imageUrl) {
        this.type = Type.Images;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    /**
     * 视频构造函数
     * @param title 标题
     * @param coverUrl 网咯视频封面路径
     * @param videoUrl 网咯视频路径
     */
    public ImageShowBean(String title, String coverUrl, String videoUrl) {
        this.type = Type.Videos;
        this.title = title;
        this.coverUrl = coverUrl;
        this.videoUrl = videoUrl;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
