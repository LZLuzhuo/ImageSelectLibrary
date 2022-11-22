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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_image_select.R;
import me.luzhuo.lib_image_select.enums.Type;

/**
 * Description: 根据bit位来判断选择的媒体文件类型
 * @Author: Luzhuo
 * @Creation Date: 2021/4/26 22:35
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ImageSelectWithFlagsAdapter extends ImageSelectAdapter{
    private int flags;

    public ImageSelectWithFlagsAdapter(Activity activity, int flags, int maxCount, int layout_add, int layout_normal, boolean isOriginal, boolean showCamera) {
        super(activity, Type.ALL, maxCount, layout_add, layout_normal, isOriginal, showCamera);
        this.flags = flags;
    }

    public ImageSelectWithFlagsAdapter(Fragment fragment, int flags, int maxCount, int layout_add, int layout_normal, boolean isOriginal, boolean showCamera) {
        super(fragment, Type.ALL, maxCount, layout_add, layout_normal, isOriginal, showCamera);
        this.flags = flags;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ADD:
                return new MyAddHolder(LayoutInflater.from(parent.getContext()).inflate(layout_add, parent, false));
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    public class MyAddHolder extends AddHolder{

        public MyAddHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.image_select_btn) {
                select.openMediaWithFlags(flags, maxCount - mDatas.size(), showCamera);
            }
        }
    }
}
