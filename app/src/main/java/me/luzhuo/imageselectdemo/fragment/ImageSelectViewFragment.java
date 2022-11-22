package me.luzhuo.imageselectdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.luzhuo.imageselectdemo.R;
import me.luzhuo.lib_image_select.ImageSelectView;
import me.luzhuo.lib_image_select.bean.ImageSelectBean;
import me.luzhuo.lib_image_select.callback.OnImageSelectCallback;
import me.luzhuo.lib_image_select.callback.impl.OnImageShowCallbackImpl;

public class ImageSelectViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_image_select_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageSelectView selectView1 = getView().findViewById(R.id.image1);
        ImageSelectView selectView2 = getView().findViewById(R.id.image2);
        ImageSelectView selectView3 = getView().findViewById(R.id.image3);

        selectView1.setOnSelectListener(new OnImageSelectCallback() {
            @Override
            public void onSelect(List<ImageSelectBean> imageSelectBean) {
                selectView2.setDatas(selectView1.getDatas());
                selectView3.setDatas(selectView1.getDatas());
            }

            @Override
            public void onDelete(ImageSelectBean imageSelectBean) { }
        });
        selectView1.setOnShowListener(new OnImageShowCallbackImpl(){
            @Override
            public boolean onImageCallback(ImageSelectBean image, List<ImageSelectBean> images) {
                Toast.makeText(requireContext(), "用自己的图片展示控件", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
