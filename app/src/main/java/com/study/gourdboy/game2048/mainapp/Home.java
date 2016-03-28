package com.study.gourdboy.game2048.mainapp;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.study.gourdboy.game2048.R;
import com.study.gourdboy.game2048.view.GameView;
import com.study.gourdboy.game2048.view.ItemSquare;

import javax.microedition.khronos.opengles.GL;

public class Home extends AppCompatActivity
{
    RelativeLayout rl_home_content;
    private boolean flag = false;
    private static Activity mHome;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rl_home_content = (RelativeLayout) findViewById(R.id.rl_home_content);
        mHome = this;
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenWidth = dm.widthPixels;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        if(!flag)
        {
            super.onWindowFocusChanged(hasFocus);
            int parentWidth = rl_home_content.getWidth();
            GameView gv = new GameView(this,4,4,parentWidth);
            rl_home_content.addView(gv);
            flag = true;
        }
    }
    public static Activity getActivity()
    {
        return mHome;
    }
}
