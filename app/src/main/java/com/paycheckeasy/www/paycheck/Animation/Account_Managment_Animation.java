package com.paycheckeasy.www.paycheck.Animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.paycheckeasy.www.paycheck.R;
 
public class Account_Managment_Animation {


    private Context mContext;

    private Animation MainFAB_Normal_Animation, MainFAB_Rotate_Animation, CashFAB_Show_Animation, CashFAB_Hide_Animation, CardFAB_Show_Animation, CardFAB_Hide_Animation;

    public Account_Managment_Animation(Context mContext){
        this.mContext = mContext;
        MainFAB_Normal_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c1_fab_main_show);
        MainFAB_Rotate_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c1_fab_main_hide);
        CashFAB_Show_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c2_fab_cash_show);
        CashFAB_Hide_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c2_fab_cash_hide);
        CardFAB_Show_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c3_fab_card_show);
        CardFAB_Hide_Animation = AnimationUtils.loadAnimation(mContext, R.anim.c3_fab_card_hide);
    }





    public void Start_Animation_MainFAB_Normal(View view, int StartOffSet, final View TargetView){
        MainFAB_Normal_Animation.setStartOffset(StartOffSet);
        MainFAB_Normal_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(MainFAB_Normal_Animation);
    }

    public void Start_Animation_MainFAB_Rotate(View view, int StartOffSet, final View TargetView){
        MainFAB_Rotate_Animation.setStartOffset(StartOffSet);
        MainFAB_Rotate_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(MainFAB_Rotate_Animation);
    }




    public void Start_Animation_CashFAB_Show(View view, int StartOffSet, final View TargetView){
        CashFAB_Show_Animation.setStartOffset(StartOffSet);
        CashFAB_Show_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(CashFAB_Show_Animation);
    }

    public void Start_Animation_CashFAB_Hide(View view, int StartOffSet, final View TargetView){
        CashFAB_Hide_Animation.setStartOffset(StartOffSet);
        CashFAB_Hide_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
                TargetView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(CashFAB_Hide_Animation);
    }




    public void Start_Animation_CardFAB_Show(View view, int StartOffSet, final View TargetView){
        CardFAB_Show_Animation.setStartOffset(StartOffSet);
        CardFAB_Show_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(CardFAB_Show_Animation);
    }

    public void Start_Animation_CardFAB_Hide(View view, int StartOffSet, final View TargetView){
        CardFAB_Hide_Animation.setStartOffset(StartOffSet);
        CardFAB_Hide_Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                TargetView.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TargetView.setEnabled(true);
                TargetView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(CardFAB_Hide_Animation);
    }



}
