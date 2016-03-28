package com.study.gourdboy.game2048.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.study.gourdboy.game2048.R;
import com.study.gourdboy.game2048.mainapp.Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gourdboy on 2016/3/22.
 */
public class GameView extends GridLayout
{
    private ItemSquare[][] itemMatrix;
    private List<Point> listBlankSquare;
    private Activity mHome;
    private static final int GAME_OVER = 0;
    private static final int GAME_DONE = 1;
    private static final int GAME_REVERSE = -1;
    private int rowCount;
    private int columnCount;
    private int parentWidth;
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    public GameView(Context context,int rowCount,int columnCount,int parentWidth)
    {
        super(context);
        this.parentWidth = parentWidth;
        init(rowCount,columnCount,parentWidth);
    }

    public GameView(Context context, AttributeSet attrs,int rowCount,int columnCount,int parentWidth)
    {
        super(context, attrs);
        this.parentWidth = parentWidth;
        init(rowCount, columnCount, parentWidth);
    }

    private void init(int rowCount, int columnCount,int parentWidth)
    {
        this.mHome = Home.getActivity();
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        itemMatrix = new ItemSquare[columnCount][rowCount];
        listBlankSquare = new ArrayList();
        setColumnCount(columnCount);
        setRowCount(rowCount);
        setPadding(3, 3, 3, 3);
        setOrientation(HORIZONTAL);
        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_content));
        int mparentWidth = parentWidth - 6;
        for (int i = 0; i < rowCount; i++)
        {
            for (int j = 0; j < columnCount; j++)
            {
                ItemSquare item = new ItemSquare(getContext());
                addView(item, mparentWidth / columnCount, mparentWidth / rowCount);
                itemMatrix[j][i] = item;
                listBlankSquare.add(new Point(j,i));
            }
        }
        addRandomSquare();
        addRandomSquare();
        addRandomSquare();
    }
    private boolean addRandomSquare()
    {
        if(!isFull())
        {
            Point point = getRandomIndex();
            int indexX = point.x;
            int indexY = point.y;
            int num = getRandomNum();
            itemMatrix[indexX][indexY].setNumber(num);
            return true;
        }
        return false;
    }
    private Point getRandomIndex()
    {
        if(!isFull())
        {
            int size = listBlankSquare.size() - 1;
            int index = (int) (Math.random() * size);
            Point p = listBlankSquare.get(index);
            listBlankSquare.remove(index);
            return p;
        }
        return null;
    }
    private  int getRandomNum()
    {
        return Math.random()>0.5?2:4;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                stopX = event.getX();
                stopY = event.getY();
                judgeDirection(startX, startY, stopX, stopY);
                if(!isFull())
                {
                    addRandomSquare();
                }
                else
                {
                    if(isGameOver())
                    {
                        resultDispose(GAME_OVER);
                    }
                    else
                    {
                        addRandomSquare();
                    }
                }
//                Log.i("FULL???",listBlankSquare.size()+"");
//                for(int i=0;i<listBlankSquare.size();i++)
//                {
//                    Point p = listBlankSquare.get(i);
//                    Log.i("slideRight",p.x+" , "+p.y);
//                }
                break;
        }
        return true;
    }
    private  boolean isGameOver()
    {
        for(int i=0;i<rowCount-1;i++)
        {
            for(int j=0;j<columnCount-1;j++)
            {
                if(itemMatrix[j][i]==itemMatrix[j][i+1]||itemMatrix[j][i]==itemMatrix[j+1][i])
                {
                    return false;
                }
            }
        }
        return true;
    }
    private void resultDispose(int resultCode)
    {
        switch (resultCode)
        {
            case GAME_OVER:
                new AlertDialog.Builder(getContext()).setTitle("失败")
                        .setMessage("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        restart();
                    }
                }).setNegativeButton("退出游戏", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mHome.finish();
                    }
                }).show();
                break;
            case GAME_DONE:
                new AlertDialog.Builder(getContext()).setTitle("恭喜！")
                        .setMessage("您已达到2048！").setPositiveButton("重新开始", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        restart();
                    }
                }).setNegativeButton("继续游戏", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).show();
                break;

        }
    }

    private void restart()
    {
        listBlankSquare.clear();
        this.removeAllViews();
        init(rowCount, columnCount, parentWidth);
    }
    private boolean isFull()
    {
        if(listBlankSquare.size()>0)
        {
            return false;
        }
        return true;
    }
    private void judgeDirection(float startX,float startY,float stopX,float stopY)
    {
        boolean flag = Math.abs(startX-stopX)>Math.abs(startY-stopY)?true:false;
        if(flag)
        {
            if(stopX>startX)
            {
                slideRight();
            }
            else
            {
                slideLeft();
            }
        }
        else
        {
            if(stopY>startY)
            {
                slideDown();
            }
            else
            {
                slideUp();
            }
        }
    }
    private void slideUp()
    {
        int preNum = -1;
        int[] rowNum;
        listBlankSquare.clear();
        for(int i=0;i<columnCount;i++)
        {
            rowNum = new int[rowCount];
            int z = 0;
            for(int j=0;j<rowCount;j++)
            {
                if(itemMatrix[i][j].getNumber()==preNum)
                {
                    rowNum[z-1] = preNum*2;
                    preNum = -1;
                }
                else if(itemMatrix[i][j].getNumber()!=0)
                {
                    preNum = itemMatrix[i][j].getNumber();
                    rowNum[z++] = preNum;
                }
            }
            for(int j=0;j<rowCount;j++)
            {
                itemMatrix[i][j].setNumber(rowNum[j]);
                if(rowNum[j]==0)
                {
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    listBlankSquare.add(point);
                }
            }
            preNum = -1;
        }
    }
    private void slideDown()
    {
        int preNum = -1;
        int[] rowNum;
        listBlankSquare.clear();
        for(int i=0;i<columnCount;i++)
        {
            rowNum = new int[rowCount];
            int z = rowCount-1;
            for(int j=rowCount-1;j>=0;j--)
            {
                if(itemMatrix[i][j].getNumber()==preNum)
                {
                    rowNum[z+1] = preNum*2;
                    preNum = -1;
                }
                else if(itemMatrix[i][j].getNumber()!=0)
                {
                    preNum = itemMatrix[i][j].getNumber();
                    rowNum[z--] = preNum;
                }
            }
            for(int j=0;j<rowCount;j++)
            {
                itemMatrix[i][j].setNumber(rowNum[j]);
                if(rowNum[j]==0)
                {
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    listBlankSquare.add(point);
                }
            }
            preNum = -1;
        }
    }
    private void slideLeft()
    {
        int preNum = -1;
        int[] rowNum;
        listBlankSquare.clear();
        for(int i=0;i<rowCount;i++)
        {
            rowNum = new int[columnCount];
            int z = 0;
            for(int j=0;j<columnCount;j++)
            {
                if(itemMatrix[j][i].getNumber()==preNum)
                {
                    rowNum[z-1] = preNum*2;
                    preNum = -1;
                }
                else if(itemMatrix[j][i].getNumber()!=0)
                {
                    preNum = itemMatrix[j][i].getNumber();
                    rowNum[z++] = preNum;
                }
            }
            for(int j=0;j<columnCount;j++)
            {
                itemMatrix[j][i].setNumber(rowNum[j]);
                if(rowNum[j]==0)
                {
                    Point point = new Point();
                    point.x = j;
                    point.y = i;
                    listBlankSquare.add(point);
                }
            }
            preNum = -1;
        }
    }
    private void slideRight()
    {
        int preNum = -1;
        int[] rowNum;
        listBlankSquare.clear();
        for(int i=0;i<rowCount;i++)
        {
            rowNum = new int[columnCount];
            int z = columnCount-1;
            for(int j=columnCount-1;j>=0;j--)
            {
                if(itemMatrix[j][i].getNumber()==preNum)
                {
                    rowNum[z+1] = preNum*2;
                    preNum = -1;
                }
                else if(itemMatrix[j][i].getNumber()!=0)
                {
                    preNum = itemMatrix[j][i].getNumber();
                    rowNum[z--] = preNum;
                }
            }
            for(int j=0;j<columnCount;j++)
            {
                itemMatrix[j][i].setNumber(rowNum[j]);
                if(rowNum[j]==0)
                {
                    Point point = new Point();
                    point.x = j;
                    point.y = i;
                    listBlankSquare.add(point);
                }
            }
            preNum = -1;
        }
    }
}
