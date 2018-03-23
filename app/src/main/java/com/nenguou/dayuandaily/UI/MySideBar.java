package com.nenguou.dayuandaily.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.nenguou.dayuandaily.R;

/**
 * Created by binguner on 2018/3/6.
 */

public class MySideBar extends View {

    private static final String[] WeeksNum = {"N","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25"};
    //private static final String[] WeeksNum = {"A","N"};
    private int width;
    private int height;
    private float cellHeight;

    private Paint numberPaint;
    private Rect nnumberRect;

    private float touchX;
    private float touchY;

    private static String theSelectedOne;

    private onSeleceListener listener;

    public MySideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        nnumberRect = new Rect();
        numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
    }

    public static void setTheSelectedOne(String s){
        theSelectedOne = s;
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            cellHeight = height * 1.0f / WeeksNum.length;
            int textSize = (int) ((width>height?cellHeight:width)*(3.0f/4));
            numberPaint.setTextSize(textSize);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNumbers(canvas);
    }

    private void drawNumbers(Canvas canvas) {
        for(int i = 0 ; i < WeeksNum.length; i++){
            String s = WeeksNum[i];
            if(theSelectedOne != null && !theSelectedOne.equals("") && theSelectedOne.equals(s)){
                numberPaint.setColor(getResources().getColor(R.color.colorToolbar));
            }
            numberPaint.getTextBounds(s,0,s.length(),nnumberRect);
            canvas.drawText(s,(width-nnumberRect.width())/2f,cellHeight * i + (cellHeight + nnumberRect.height())/2f,numberPaint);
            numberPaint.setColor(Color.BLACK);
        }
    }

    private String getHint(){
        int index = (int) (touchY/cellHeight);
        if(index >= 0 && index < WeeksNum.length) {
            return WeeksNum[index];
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                touchY = event.getY();
                //Log.d("AtySchedulerTag1","TouchX is : "+ touchX +" TouchY is : " + touchY);
                if (listener != null && touchX > 0) {
                    listener.onSelect(getHint());
                }
                if (listener != null && touchX < 0) {
                    listener.onMoveUp(getHint());
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                touchY = event.getY();
                if (listener != null) {
                    listener.onMoveUp(getHint());
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    public interface onSeleceListener{
        void onSelect(String s);
        void onMoveUp(String s);
    }

    public void setOnSeleceListener(onSeleceListener listener){
        this.listener = listener;
    }
}
