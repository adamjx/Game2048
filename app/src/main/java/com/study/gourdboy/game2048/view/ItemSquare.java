package com.study.gourdboy.game2048.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.study.gourdboy.game2048.R;

public class ItemSquare extends FrameLayout
{
    private TextView mTv;
    private int number;
    public ItemSquare(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(0);
    }
    public ItemSquare(Context context)
    {
        super(context);
        initView(0);
    }
    private void initView(int number)
    {
        mTv = new TextView(getContext());
        this.number = number;
        if(number!=0)
        {
            mTv.setText(Integer.toString(number));
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(6, 6, 6, 6);
        mTv.setGravity(Gravity.CENTER);
        mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_square));
        addView(mTv, params);
    }
    public void setNumber(int number)
    {
        if(number!=0)
        {
            this.mTv.setText(String.valueOf(number));
            switch (number)
            {
                case 2:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num2));
                    break;
                case 4:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num4));
                    break;
                case 8:
                case 16:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num8));
                    break;
                case 32:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num32));
                    break;
                case 64:
                case 128:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num64));
                    break;
                case 256:
                case 512:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num256));
                    break;
                case 1024:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num1024));
                    break;
                case 2048:
                case 4096:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_num1024));
                    break;
                default:
                    mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_square));
            }
        }
        else
        {
            mTv.setText("");
            mTv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_square));
        }
        this.number = number;
    }
    public int getNumber()
    {
        return number;
    }
}