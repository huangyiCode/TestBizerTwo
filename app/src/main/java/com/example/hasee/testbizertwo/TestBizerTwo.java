package com.example.hasee.testbizertwo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hasee on 2017/8/12.
 */

public class TestBizerTwo extends View {

    //红色画笔
    private Paint mRedPaint;

    //蓝色画笔
    private Paint mBiuePaint;

    /**
     * 红色曲线起始点的坐标
     */
    private static int RED_PAINT_START_X_POINT=-200;
    private static int RED_PAINT_START_Y_POINT=200;

    /**
     * 蓝色曲线起始点的坐标
     */
    private static int BLUE_PAINT_START_X_POINT=-100;
    private static int BLUE_PAINT_START_Y_POINT=200;

    /**
     * 波长度
     */
    private static int WAVE_LENGTH=300;

    /**
     *波峰高度
     */
    private static int WAVE_HEIGHT=50;


    /**
     *屏幕宽高
     */
    private static int width;
    private static int heigth;



    public TestBizerTwo(Context context) {
        this(context,null);
    }

    public TestBizerTwo(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestBizerTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化画笔
     */
    public  void init(){

        mRedPaint=new Paint();
        mBiuePaint=new Paint();

        mRedPaint.setColor(0x55FA4675);
        mBiuePaint.setColor(0xcc5FBAD7);

        mRedPaint.setAntiAlias(true);
        mBiuePaint.setAntiAlias(true);

        mRedPaint.setStrokeWidth(15);
        mBiuePaint.setStrokeWidth(15);

        mRedPaint.setStyle(Paint.Style.FILL);
        mBiuePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        heigth=MeasureSpec.getSize(heightMeasureSpec);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //1.初始化一个Path路径
        Path p=new Path();
        //2.设置路径的起始点(Bizer的起始点)
        p.moveTo(RED_PAINT_START_X_POINT,RED_PAINT_START_Y_POINT);
        //3.绘制一个波长的贝塞尔曲线
//        //控制点坐标:WAVE_LENGTH/4,START_Y_POINT-WAVE_HEIGHT
//        //结束点坐标:WAVE_LENGTH/2,START_Y_POINT
//        p.quadTo(WAVE_LENGTH/4,START_Y_POINT-WAVE_HEIGHT,WAVE_LENGTH/2,START_Y_POINT);
//        //以上一次的结束点为起始点
//        p.quadTo(WAVE_LENGTH*3/4,START_Y_POINT+WAVE_HEIGHT,WAVE_LENGTH,START_Y_POINT);

        for (int i = 0; i <20 ; i++) {
            p.quadTo(WAVE_LENGTH*(i*2+1)/4+RED_PAINT_START_X_POINT,i%2==0?RED_PAINT_START_Y_POINT-WAVE_HEIGHT:RED_PAINT_START_Y_POINT+WAVE_HEIGHT,
                     WAVE_LENGTH*(i+1)/2+RED_PAINT_START_X_POINT,RED_PAINT_START_Y_POINT);
        }

        p.lineTo(width,heigth);

        p.lineTo(0,heigth);

        p.close();

        //4.绘制路径
        canvas.drawPath(p,mRedPaint);


        //1.初始化一个Path路径
        Path p1=new Path();
        //2.设置路径的起始点(Bizer的起始点)
        p1.moveTo(BLUE_PAINT_START_X_POINT,BLUE_PAINT_START_Y_POINT);
        //3.绘制一个波长的贝塞尔曲线
//        //控制点坐标:WAVE_LENGTH/4,START_Y_POINT-WAVE_HEIGHT
//        //结束点坐标:WAVE_LENGTH/2,START_Y_POINT
//        p.quadTo(WAVE_LENGTH/4,START_Y_POINT-WAVE_HEIGHT,WAVE_LENGTH/2,START_Y_POINT);
//        //以上一次的结束点为起始点
//        p.quadTo(WAVE_LENGTH*3/4,START_Y_POINT+WAVE_HEIGHT,WAVE_LENGTH,START_Y_POINT);

        for (int i = 0; i <20 ; i++) {
            p1.quadTo(WAVE_LENGTH*(i*2+1)/4+BLUE_PAINT_START_X_POINT,i%2==0?BLUE_PAINT_START_Y_POINT-WAVE_HEIGHT:BLUE_PAINT_START_Y_POINT+WAVE_HEIGHT,
                    WAVE_LENGTH*(i+1)/2+BLUE_PAINT_START_X_POINT,BLUE_PAINT_START_Y_POINT);
        }

        p1.lineTo(width,heigth);

        p1.lineTo(0,heigth);

        p1.close();

        //4.绘制路径
        canvas.drawPath(p1,mBiuePaint);




    }
}
