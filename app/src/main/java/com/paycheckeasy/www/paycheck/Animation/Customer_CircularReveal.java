package com.paycheckeasy.www.paycheck.Animation;
import android.animation.*;
import android.view.*;
 
/*
 *	 1. http://anjithsasindran.in/blog/2015/08/15/material-sharing-card/
 *	 2. https://yuweiguocn.github.io/circular-reveal-animation/
 */ 

public class Customer_CircularReveal
{
	public Customer_CircularReveal(){}
	
	public void enter_Reveal(View view, int Duration_Time, int Delay_Time){

		int centerX = (view.getLeft() + view.getRight()) / 2;
		int centerY = (view.getTop() + view.getBottom());
		//int centerX = view.getMeasuredWidth() / 2;
		//int centerY = view.getMeasuredHeight() / 2;

		int startRadius = 0;
		// get the final radius for the clipping circle
		int endRadius = Math.max(view.getWidth(), view.getHeight());

		// create the animator for this view (the start radius is zero)
		Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

		// make the view visible and start the animation
		view.setVisibility(View.VISIBLE);
		
		anim.setDuration(Duration_Time);
		anim.setStartDelay(Delay_Time);
		anim.start();
	}
	
	
	public void exit_Reveal(View view, int Duration_Time, int Delay_Time){
		
		final View x1 = view;
		
		//int centerX = view.getMeasuredWidth() / 2;
		//int centerY = view.getMeasuredHeight() / 2;
		int centerX = (view.getLeft() + view.getRight()) / 2;
		int centerY = (view.getTop() + view.getBottom());

		int startRadius = 0;
		// get the final radius for the clipping circle
		int endRadius = view.getWidth();
		

		// create the animator for this view (the start radius is zero)
		Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, endRadius, startRadius);

		// make the view visible and start the animation
		anim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					x1.setVisibility(View.GONE);
				}
			});
		
		anim.setDuration(Duration_Time);
		anim.setStartDelay(Delay_Time);
		anim.start();
	}
	
}
