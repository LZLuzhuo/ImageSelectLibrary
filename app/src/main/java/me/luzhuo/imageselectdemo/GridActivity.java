package me.luzhuo.imageselectdemo;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.luzhuo.lib_image_select.adapter.ImageSelectAdapter;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageSelectCallback;
import me.luzhuo.lib_image_select.enums.Type;


/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/7 15:04
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class GridActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity_recyclerview);

        RecyclerView rev = findViewById(R.id.rev);

        rev.setLayoutManager(new GridLayoutManager(this, 3));
        ImageSelectAdapter adapter = new ImageSelectAdapter(this, Type.ALL, 9);
        rev.setAdapter(adapter);

        adapter.setOnSelectListener(new OnImageSelectCallback() {
            @Override
            public void onSelect(List<ImageSelectBean> imageSelectBean) {
                Log.e("GridActivity", "选择了图片");
            }

            @Override
            public void onDelete(ImageSelectBean imageSelectBean) {

            }
        });
    }
}
