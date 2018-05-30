package com.captain.smartbridge.UI.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by captain on 18-5-30.
 */

public class TenView extends View {
    Paint paint;
    int width, height;

    public TenView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        //分为10份
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        width = getWidth()/10;
        height = getHeight()/10;
        paint.setColor(Color.GREEN);
        canvas.drawRect(width, height, width*2, height*3, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(width, height*4, width*2, height*6, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width, height*7, width*2, height*9, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width*3, height*2, width*4, height*4, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(width*3, height*5, width*4, height*7, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width*6, height*2, width*7, height*4, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width*6, height*5, width*7, height*7, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(width*8, height, width*9, height*3, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(width*8, height*4, width*9, height*6, paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(width*8, height*7, width*9, height*9, paint);

    }

    public void setTen(int a, int b, int c, int d){
        //一个百分比
        //重新绘制十个矩形
        //刷新界面
        invalidate();
    }
}
