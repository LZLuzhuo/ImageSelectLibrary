package me.luzhuo.imageselectdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.luzhuo.imageselectdemo.R;
import me.luzhuo.lib_image_select.ImageShowView;
import me.luzhuo.lib_image_select.bean.ImageShowBean;
import me.luzhuo.lib_image_select.callback.impl.OnImageTitleShowCallbackImpl;

public class ImageShowViewFragment extends Fragment {
    final static List<ImageShowBean> images = new ArrayList<>();

    static {
        images.add(new ImageShowBean("xxx", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201311%2F01%2F215828tpmddz2d2bfcz5pk.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619197782&t=8ec66be3937ab86f0e7edab59df271ca"));
        images.add(new ImageShowBean("666", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201410%2F25%2F220832wlwzqq6ble9ql6rd.jpg&refer=http%3A%2F%2Fattach.bbs.miui.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1619197782&t=0631fd64899882e134049c4631e2eef8"));
        images.add(new ImageShowBean("video", null, "https://vd3.bdstatic.com/mda-khtuhgzs96xrn7sa/mda-khtuhgzs96xrn7sa.mp4?v_from_s=sz_videoui_4135&auth_key=1616607729-0-0-c8772210059111f2e9738b9eaa2b2138&bcevod_channel=searchbox_feed&pd=1&pt=3&abtest=3000156_3"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_image_show_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageShowView imageshow = getView().findViewById(R.id.imageshow);
        imageshow.setDatas(images);
        imageshow.setOnShowListener(new OnImageTitleShowCallbackImpl(){
            @Override
            public boolean onImageCallback(ImageShowBean image, List<ImageShowBean> images) {
                Toast.makeText(requireContext(), "使用自己的图片展示框架", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }
}
