package com.paycheckeasy.www.paycheck.Animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View; 

import com.paycheckeasy.www.paycheck.R;

public class Flip_Card { 

    public Context mContext;

    public Animator Card_Right_in, Card_Right_out, Card_Left_in, Card_Left_out;

    @SuppressLint("ResourceType")
    public Flip_Card(Context mContext){
        this.mContext = mContext;
        Card_Right_in = AnimatorInflater.loadAnimator(mContext, R.anim.c5_filpcard_right_in);
        Card_Right_out = AnimatorInflater.loadAnimator(mContext, R.anim.c5_filpcard_right_out);
        Card_Left_in = AnimatorInflater.loadAnimator(mContext, R.anim.c4_filpcard_left_in);
        Card_Left_out = AnimatorInflater.loadAnimator(mContext, R.anim.c4_filpcard_left_out);
    }

    public void Start_Card_RightIn(View Target_View){
        Card_Right_in.setTarget(Target_View);
        Card_Right_in.start();
        Card_Right_in.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    public void Start_Card_RightOut(final View Target_View){
        Card_Right_out.setTarget(Target_View);
        Card_Right_out.start();
        Card_Right_out.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Target_View.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void Start_Card_LeftIn(final View Target_View){
        Card_Left_in.setTarget(Target_View);
        Card_Left_in.start();
        Card_Left_in.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Target_View.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void Start_Card_LeftOut(final View Target_View){
        Card_Left_out.setTarget(Target_View);
        Card_Left_out.start();
        Card_Left_out.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


}
