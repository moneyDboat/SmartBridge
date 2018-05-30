package com.captain.smartbridge.UI.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.captain.smartbridge.R;

/**
 * Created by captain on 18-5-30.
 */

public class RGBView extends View {
    Paint paint;

    public RGBView(Context context, AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getWidth()/2, getHeight()/2, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(getWidth()/2, 0, getWidth(), getHeight()/2, paint);
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(0, getHeight()/2, getWidth()/2, getHeight(), paint);
        paint.setColor(Color.RED);
        canvas.drawRect(getWidth()/2, getHeight()/2, getWidth(), getHeight(), paint);
    }

    public void setRGB(int a, int b, int c, int d){
        //重新绘制四个矩形
        //刷新界面
        invalidate();
    }
}
