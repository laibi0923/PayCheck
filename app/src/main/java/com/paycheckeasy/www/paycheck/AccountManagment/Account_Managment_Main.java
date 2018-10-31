package com.paycheckeasy.www.paycheck.AccountManagment;
import android.content.Intent;
import java.text.DateFormat;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paycheckeasy.www.paycheck.Animation.Account_Managment_Animation;
import com.paycheckeasy.www.paycheck.Animation.Customer_CircularReveal;
import com.paycheckeasy.www.paycheck.MainActivity;
import com.paycheckeasy.www.paycheck.R;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.widget.*;


public class Account_Managment_Main extends Fragment
{
	private Account_Managment_RecycleView mRecyclerView;
	private View mRecycle_EmptyView;
	private ProgressBar mRecycleView_ProgressBar;
	private ArrayList<Account_Model> mRecyclerView_List;

	private LinearLayout new_ac_mask_layout, new_cash_layout, new_card_layout;
	private FloatingActionButton Main_FAB, Cash_FAB, Card_FAB;

	private Customer_CircularReveal mCustomer_CircularReveal;
	private Account_Managment_Animation mAccount_Managment_Animation;

	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference mDatabaseReference;
	private FirebaseAdapter mFirebaseAdapter;

	private void Find_View(View v){

	    ((MainActivity) getContext()).mToolbar_Title.setText("帳戶管理");

		mRecycleView_ProgressBar = v.findViewById(R.id.progress_bar);
		mRecycleView_ProgressBar.setVisibility(View.VISIBLE);
		
        mRecyclerView = v.findViewById(R.id.ac_selector_RecycleView);
        mRecyclerView.addOnScrollListener(RecyclerView_OnScrollListener);
		
		mRecycle_EmptyView = v.findViewById(R.id.ac_managment_emptyview);

		new_ac_mask_layout = v.findViewById(R.id.new_ac_mask);
		new_ac_mask_layout.setVisibility(View.INVISIBLE);
		new_cash_layout = v.findViewById(R.id.new_cash_layout);
		new_card_layout = v.findViewById(R.id.new_card_layout);

		Main_FAB = v.findViewById(R.id.main_FAB);
		Main_FAB.setOnClickListener(Item_OnclickListener);

		Cash_FAB = v.findViewById(R.id.cash_FAB);
		Cash_FAB.setTag("Cash_Dialog");
		Cash_FAB.setOnClickListener(Item_OnclickListener);

		Card_FAB = v.findViewById(R.id.card_FAB);
		Card_FAB.setTag("Card_Dialog");
		Card_FAB.setOnClickListener(Item_OnclickListener);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mFirebaseDatabase = FirebaseDatabase.getInstance();

		mDatabaseReference = mFirebaseDatabase.getReference();

		mRecyclerView_List = new ArrayList<>();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// TODO: Implement this method
		View v = inflater.inflate(R.layout.c1_ac_managment_main, container, false);
		Find_View(v);
		init_recycleview(mRecyclerView_List);
		// 動畫
		mCustomer_CircularReveal = new Customer_CircularReveal();
		mAccount_Managment_Animation = new Account_Managment_Animation(getContext());
		return v;
	}


    private void init_recycleview(final ArrayList<Account_Model> mRecyclerView_List){

        LinearLayoutManager mLayout = new LinearLayoutManager(this.getActivity());
		
		// 水平布局
        mLayout.setOrientation(LinearLayoutManager.VERTICAL);
		
		// 反轉列表
		mLayout.setStackFromEnd(true); 
		
		// 由底部顯示
		mLayout.setReverseLayout(true); 
		
        mRecyclerView.setLayoutManager(mLayout);
		
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		
        // 設置下劃線 android 5.0
		// mRecyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL));

        // 查詢條件
		Query query = mDatabaseReference
                .child("Account")
                .orderByChild( ((MainActivity)getActivity()).Uid_Text );

        // Firebase Recycler Adapter
        mFirebaseAdapter = new FirebaseAdapter(Account_Model.class, R.layout.c2_ac_managment_recycleitem_card, Account_ViewHolder.class, query, getContext());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mRecycleView_ProgressBar.setVisibility(View.GONE);
                mRecyclerView.setAdapter(mFirebaseAdapter);
                mRecyclerView.setEmptyView(mRecycle_EmptyView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


	private RecyclerView.OnScrollListener RecyclerView_OnScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0 && Main_FAB.getVisibility() == View.VISIBLE) {
				Main_FAB.setEnabled(false);
				Main_FAB.hide();
            } else if (dy <= 0  && Main_FAB.getVisibility() != View.VISIBLE) {
				Main_FAB.setEnabled(true);
				Main_FAB.show();
            }
        }
    };

	
    private View.OnClickListener Item_OnclickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v)
        {
            // TODO: Implement this method
            switch(v.getId()){

                case R.id.main_FAB:
                    if(new_ac_mask_layout.getVisibility() == View.VISIBLE){
                        Close_TypeMenu();
                    }else{
                        Show_TypeMenu();
                    }
                    break;

                case R.id.cash_FAB:
					
					Close_TypeMenu();
					
                    Intent NewCash_Intent = new Intent(getActivity(), New_Cash_Activity.class);
					
                    NewCash_Intent.putExtra("UserID", ((MainActivity)getActivity()).Uid_Text);
                    NewCash_Intent.putExtra("UserName", ((MainActivity)getActivity()).DisplayName_Text);
					
                    startActivityForResult(NewCash_Intent, 117);
					
					
                    break;

                case R.id.card_FAB:
					
                    Close_TypeMenu();
					
                    Intent NewCard_Intent = new Intent(getActivity(), New_Card_Activity.class);
					
                    startActivity(NewCard_Intent);
					
                    break;

            }
        }
    };


    public String get_current_DateAndTime(){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(Calendar.getInstance().getTime());
        return time ;
    }


    private void Show_TypeMenu(){

        mCustomer_CircularReveal.enter_Reveal(new_ac_mask_layout, 100, 0);

        new_cash_layout.setVisibility(View.VISIBLE);
        new_card_layout.setVisibility(View.VISIBLE);
        Cash_FAB.setVisibility(View.VISIBLE);
        Card_FAB.setVisibility(View.VISIBLE);

        mAccount_Managment_Animation.Start_Animation_MainFAB_Rotate(Main_FAB,150, Main_FAB);
        mAccount_Managment_Animation.Start_Animation_CashFAB_Show(new_cash_layout, 350, Cash_FAB);
        mAccount_Managment_Animation.Start_Animation_CardFAB_Show(new_card_layout, 350, Card_FAB);
    }


    public void Close_TypeMenu(){

    	mAccount_Managment_Animation.Start_Animation_CashFAB_Hide(new_cash_layout, 0, Cash_FAB);
    	mAccount_Managment_Animation.Start_Animation_CardFAB_Hide(new_card_layout, 0, Card_FAB);
    	mAccount_Managment_Animation.Start_Animation_MainFAB_Normal(Main_FAB, 150, Main_FAB);

        mCustomer_CircularReveal.exit_Reveal(new_ac_mask_layout, 100, 350);

    }

}
