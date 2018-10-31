package com.paycheckeasy.www.paycheck.AccountManagment;

import android.view.View;
 
/* 
 Handling Item Click on RecycleView
 http://android.mskurt.net/2015/12/28/handling-item-clicks-on-recyclerview/
 */
 
public interface RecycleViewItemClickListener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
}
