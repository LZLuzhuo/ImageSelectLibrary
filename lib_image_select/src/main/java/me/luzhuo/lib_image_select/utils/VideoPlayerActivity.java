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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.VodControlView;

import me.luzhuo.lib_core.ui.calculation.UICalculation;
import me.luzhuo.lib_image_select.R;

/**
 * Description: 简单的视频播放
 * @Author: Luzhuo
 * @Creation Date: 2021/3/25 0:20
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class VideoPlayerActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private ImageView video_return;
    public static final String CoverUrl = "CoverUrl", VideoUrl = "VideoUrl";

    public static void start(Context context, String coverUrl, String videoUrl) {
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra(CoverUrl, coverUrl);
        intent.putExtra(VideoUrl, videoUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mVideoView = findViewById(R.id.player);
        video_return = findViewById(R.id.video_return);
        int statusBarHeight = new UICalculation(this).getStatusBarHeight(video_return);
        ((ViewGroup.MarginLayoutParams) video_return.getLayoutParams()).topMargin = statusBarHeight;

        initVideoView();

        video_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initVideoView() {
        String coverUrl;
        String videoUrl;
        try {
            coverUrl = getIntent().getStringExtra(CoverUrl);
            videoUrl = getIntent().getStringExtra(VideoUrl);
        }catch (Exception e){
            Toast.makeText(this, "数据异常", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (TextUtils.isEmpty(videoUrl)) {
            Toast.makeText(this, "视频地址错误", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        PrepareView prepareView = new PrepareView(this);//准备播放界面
        ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
        Glide.with(this).load(coverUrl != null ? coverUrl : videoUrl).into(thumb);

        StandardVideoController controller = new StandardVideoController(this);
        controller.addControlComponent(prepareView);
        controller.addControlComponent(new CompleteView(this));//自动完成播放界面
        controller.addControlComponent(new ErrorView(this));//错误界面
        VodControlView vodControlView = new VodControlView(this);//点播控制条
        vodControlView.findViewById(R.id.fullscreen).setVisibility(View.GONE);
        controller.addControlComponent(vodControlView);

        //如果你不想要UI，不要设置控制器即可
        mVideoView.setVideoController(controller);

        mVideoView.setUrl(videoUrl);
        //播放状态监听
        mVideoView.start();
    }

    public void qiehuanVideo(){
        mVideoView.release();
        mVideoView.setUrl("切换视频");
        mVideoView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }

    @Override
    public void onBackPressed() {
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
