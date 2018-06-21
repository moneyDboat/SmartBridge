package com.captain.smartbridge.UI.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by captain on 18-5-30.
 */

public class TenView extends View {
    Paint paint;
    float thre;
    float[] val = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int height;
    int width;
    int green = Color.parseColor("#008000");
    int red = Color.parseColor("#FF0000");

    public TenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //分为10份
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //十个矩形的位置，后面会修改成传感器的位置
        width = getWidth() / 10;
        height = getHeight() / 10;
        int[] x = {width, width, width, width * 3, width * 3, width * 6, width * 6, width * 8, width * 8, width * 8};
        int[] y = {height, height * 4, height * 7, height * 2, height * 5, height * 2, height * 5, height, height * 4, height * 7};

        //绘制十个矩形
        for (int i = 0; i < 10; i++) {
            LinearGradient gra = new LinearGradient(x[i], y[i], x[i], y[i] + 2 * height, red, green, LinearGradient.TileMode.CLAMP);
            paint.setShader(gra);
            canvas.drawRect(x[i], y[i] + covHei(val[i]), x[i] + width, y[i] + 2 * height, paint);
        }
    }

    public void setTen(float thre, float[] val) {
        //刷新界面
        this.thre = thre;
        this.val = val;
        invalidate();
    }

    //将传感器数值转变成需要遮挡的数值
    private int covHei(float v) {
        return 2 * height - (int) (2 * height * v / thre);
    }
}
