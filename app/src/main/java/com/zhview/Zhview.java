package com.zhview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2017/6/17 0017.
 */

public class Zhview extends View {
    private Paint mPaint1;  //圆弧画笔
    private Paint mPaint2;  //外框画笔
    //圆弧宽度
    private int mBorderWidth1=dipToPx(5);
    //外框宽度
    private int mBorderWidth2=dipToPx(1.5f);
    //扫过的范围
    private float mCurrentRadian=0;
    //动画持续时长
    private int mDuration=1500;

    public Zhview(Context context) {
        this(context,null);
    }

    public Zhview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public Zhview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        initPaint();
    }

    private void initPaint() {
        mPaint1 = new Paint();
       //设置画笔颜色
        mPaint1.setColor(Color.WHITE);
        // 设置画笔的样式为圆形
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        // 设置画笔的填充样式为描边
        mPaint1.setStyle(Paint.Style.STROKE);
        //抗锯齿
        mPaint1.setAntiAlias(true);
        //设置画笔宽度
        mPaint1.setStrokeWidth(mBorderWidth1);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.WHITE);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(mBorderWidth2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        //矩形轮廓，圆弧在内部，给予一定的内边距
        RectF rectF1 = new RectF(mBorderWidth1/2+dipToPx(8), mBorderWidth1/2+dipToPx(8), getWidth() -mBorderWidth1/2-dipToPx(8),getWidth()-mBorderWidth1/2-dipToPx(8) );
        //画圆弧 参数1：矩形轮廓 参数2：起始位置 参数3：扫过的范围，初始为0 参数4：是否连接圆心
        canvas.drawArc(rectF1, 90, mCurrentRadian, false, mPaint1);
        //矩形轮廓
        RectF rectF2 = new RectF(mBorderWidth2/2,mBorderWidth2/2,getWidth()-mBorderWidth2/2,getWidth()-mBorderWidth2/2);
        //画圆角矩形边框 参数2 3设置x,y方向的圆角corner 都要设置
        canvas.drawRoundRect(rectF2,dipToPx(8),dipToPx(8),mPaint2);

    }

    private void startAnimationDraw() {
        //圆弧扫过范围为270度
        ValueAnimator valueAnimator=new ValueAnimator().ofFloat(0,270);
        //动画持续时间
        valueAnimator.setDuration(mDuration);
        //设置插值器，中间快两头慢
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //添加状态监听器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断增大圆弧扫过的范围，并重绘来实现动画效果
                mCurrentRadian= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
    //开始动画
    public void startAnimation(){
        startAnimationDraw();
    }
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
