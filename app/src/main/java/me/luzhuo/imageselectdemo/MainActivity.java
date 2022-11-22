package me.luzhuo.imageselectdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import me.luzhuo.imageselectdemo.fragment.ImageSelectViewFragment;
import me.luzhuo.imageselectdemo.fragment.ImageShowViewFragment;

public class MainActivity extends AppCompatActivity {
//    ImageSelectManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ImageSelectViewFragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new ImageShowViewFragment()).commit();
    }

    public void onClick(View view) {
    }
}
