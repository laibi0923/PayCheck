package com.paycheckeasy.www.paycheck.AccountManagment;

import android.content.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.support.v7.widget.RecyclerView.*;
 
public class Account_Managment_RecycleView extends RecyclerView {
	 
	private View emptyView;
	private static final String TAG = "EmptyRecyclerView";

    public Account_Managment_RecycleView(Context context) {
        super(context);
    }


    public Account_Managment_RecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public Account_Managment_RecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


	final private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver(){

		@Override
		public void onChanged()
		{
			// TODO: Implement this method
			check_Empty();
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount)
		{
			// TODO: Implement this method
			check_Empty();
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount)
		{
			// TODO: Implement this method
			check_Empty();
		}
	};


	private void check_Empty(){
		if (emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
	}


	@Override
	public void setAdapter(RecyclerView.Adapter adapter) {

		// TODO: Implement this method
		final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
		
		//check_Empty();
	}


	//设置没有内容时，提示用户的空布局
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        check_Empty();
    }
	
	
	
}
