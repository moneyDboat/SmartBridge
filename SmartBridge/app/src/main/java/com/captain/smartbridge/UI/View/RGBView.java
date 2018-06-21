package com.captain.smartbridge.UI.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.CpuUsageInfo;
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

        //将传感器的数值转换成颜色
        //k表示粒度
        int k = 10;
        int col1 = covRGB(a);
        int col2 = covRGB(b);
        int col3 = covRGB(c);
        int col4 = covRGB(d);
        int a1 = Color.red(col1), a2 = Color.green(col1), a3 = Color.blue(col1);
        int b1 = Color.red(col2), b2 = Color.green(col2), b3 = Color.blue(col2);
        int c1 = Color.red(col3), c2 = Color.green(col3), c3 = Color.blue(col3);
        int d1 = Color.red(col4), d2 = Color.green(col4), d3 = Color.blue(col4);


        for (int i = 0; i < getHeight() - k; i += k) {
            //绘制左侧颜色块
            int l1 = a1 + (b1 - a1) * i / getHeight();
            int l2 = a2 + (b2 - a2) * i / getHeight();
            int l3 = a3 + (b3 - a3) * i / getHeight();
            paint.setColor(Color.rgb(l1, l2, l3));
            canvas.drawRect(0, i, k, i + k, paint);
            //绘制右侧颜色块
            int r1 = c1 + (d1 - c1) * i / getHeight();
            int r2 = c2 + (d2 - c2) * i / getHeight();
            int r3 = c3 + (d3 - c3) * i / getHeight();
            paint.setColor(Color.rgb(r1, r2, r3));
            canvas.drawRect(getWidth() - k, i, getWidth(), i + k, paint);

            //根据左右侧颜色块横向绘制渐变颜色
            for (int j = k; j < getWidth() - k; j += k) {
                int t1 = l1 + (r1 - l1) * j / getWidth();
                int t2 = l2 + (r2 - l2) * j / getWidth();
                int t3 = l3 + (r3 - l3) * j / getWidth();
                paint.setColor(Color.rgb(t1, t2, t3));
                canvas.drawRect(j, i, j+k, i+k, paint);
            }
        }
    }

    public void setRGB(float thre, float a, float b, float c, float d) {
        this.thre = thre;
        this.a = reValue(a);
        this.b = reValue(b);
        this.c = reValue(c);
        this.d = reValue(d);

        //刷新界面
        //调用onDraw()
        invalidate();
    }

    //修正传感器数值
    private float reValue(float value) {
        if (value < 0)
            value *= -1;
        if (value > thre)
            value = thre;
        return value;
    }

    //将传感器数值转换成RGB值
    private int covRGB(float value) {
        switch ((int) (value * 5 / thre)) {
            case 0:
                return Color.parseColor("#008000");
            case 1:
                return Color.parseColor("#32CD32");
            case 2:
                return Color.parseColor("#e67f2b");
            case 3:
                return Color.parseColor("#FFA500");
            case 4:
                return Color.parseColor("#FF0000");
            case 5:
                return Color.parseColor("#FF0000");
        }
        return Color.parseColor("#008000");
    }
}
