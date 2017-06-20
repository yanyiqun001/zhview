package com.zhview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout rv_bottom;
    private Zhview zhview;
    private float mHeight;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_bottom= (RelativeLayout) this.findViewById(R.id.rv_bottom);
        zhview= (Zhview) this.findViewById(R.id.zhview);
        image= (ImageView) this.findViewById(R.id.image);
        rv_bottom.post(new Runnable() {
            @Override
            public void run() {
                //获得底部的高度
                mHeight=rv_bottom.getHeight();
                //开始动画
                startAnimation();
                //延时加载图片
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).
                                load(R.drawable.timg).
                                centerCrop().
                                skipMemoryCache(true).
                                diskCacheStrategy(DiskCacheStrategy.NONE).
                                crossFade(500).
                                into(image)
                        ;
                    }
                },2000);
            }
        });
    }

    private void startAnimation() {
        //位移动画，从底部滑出，Y方向移动
        ObjectAnimator translationAnimator= ObjectAnimator.ofFloat(rv_bottom, "translationY", mHeight, 0f);
        //设置时长
        translationAnimator.setDuration(1000);
        //透明度渐变动画
        ObjectAnimator alphaAnimatorator = ObjectAnimator.ofFloat(rv_bottom, "alpha", 0f,1f);
        //设置时长
        alphaAnimatorator.setDuration(2500);
        //添加监听器，位移结束后，画圆弧开始
        translationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                zhview.startAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        //两个动画一起执行
        set.play(translationAnimator).with(alphaAnimatorator);
        //go
        set.start();
    }


}
