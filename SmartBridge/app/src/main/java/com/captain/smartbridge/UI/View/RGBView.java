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
        int a1=244, b1=154, c1=60;
        int a2=255, b2=82, c2=47;
        int a3=255, b3=100, c3=100;
        int a4=255, b4=0, c4=0;
        int l1, l2, l3;
        int r1, r2, r3;
        //绘图的粒度
        int k = 10;
        for (int i=0; i<getHeight()-k; i+=k){
            l1 = a1+(a2-a1)*i/getHeight();
            l2 = b1+(b2-b1)*i/getHeight();
            l3 = c1+(c2-c1)*i/getHeight();
            paint.setColor(Color.rgb(l1, l2, l3));
            canvas.drawRect(0, i, k, i+k, paint);
            r1 = a3+(a4-a3)*i/getHeight();
            r2 = b3+(b4-b3)*i/getHeight();
            r3 = c3+(c4-c3)*i/getHeight();
            paint.setColor(Color.rgb(r1, r2, r3));
            canvas.drawRect(getWidth()-k, i, getWidth(), i+k, paint);

            for(int j=k; j<getWidth()-k; j+=k){
                paint.setColor(Color.rgb(l1+(r1-l1)*j/getWidth(), l2+(r2-l2)*j/getWidth(),
                        l3+(r3-l3)*j/getWidth()));
                canvas.drawRect(j, i, j+k, i+k, paint);
            }
        }
    }

    public void setRGB(int a, int b, int c, int d){
        //重新绘制四个矩形
        //刷新界面
        invalidate();
    }
}
