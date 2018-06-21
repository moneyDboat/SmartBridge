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
    float thre;
    float a, b, c, d;

    public RGBView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //重新绘制四个矩形
        //绘图的粒度
        int k = 50;
        int l1, l2;
        int r1, r2;
        int a1 = (int) (255 * (a / thre));
        int a2 = 255 - a1;
        int b1 = (int) (255 * (b / thre));
        int b2 = 255 - b1;
        int c1 = (int) (255 * (c / thre));
        int c2 = 255 - c1;
        int d1 = (int) (255 * (d / thre));
        int d2 = 255 - d1;

        for (int i = 0; i < getHeight() - k; i += k) {
            l1 = a1 + (b1 - a1) * i / getHeight();
            l2 = a2 + (b2 - a2) * i / getHeight();
            paint.setColor(Color.rgb(l1, l2, 0));
            canvas.drawRect(0, i, k, i + k, paint);

            r1 = c1 + (d1 - c1) * i / getHeight();
            r2 = c2 + (d2 - c2) * i / getHeight();
            paint.setColor(Color.rgb(r1, r2, 0));
            canvas.drawRect(getWidth() - k, i, getWidth(), i + k, paint);

            for (int j = k; j < getWidth() - k; j += k) {
                paint.setColor(Color.rgb(l1 + (r1 - l1) * j / getWidth(),
                        l2 + (r2 - l2) * j / getHeight(), 0));
                canvas.drawRect(j, i, j + k, i + k, paint);
            }
        }
    }

    public void setRGB(float thre, float a, float b, float c, float d) {
        this.thre = thre;

        if (a < 0)
            a *= -1;
        if (a > thre)
            a = thre;
        if (b < 0)
            b *= -1;
        if (b > thre)
            b = thre;
        if (c < 0)
            c *= -1;
        if (c > thre)
            c = thre;
        if (d < 0)
            d *= -1;
        if (d > thre)
            d = thre;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

        //刷新界面
        //调用onDraw()
        invalidate();
    }
}
