package com.paycheckeasy.www.paycheck.AccountManagment;
import android.animation.Animator;
import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paycheckeasy.www.paycheck.*;
import com.paycheckeasy.www.paycheck.Animation.Flip_Card;
import com.paycheckeasy.www.paycheck.PublicClass.CircleImageView;

public class New_Cash_Activity extends AppCompatActivity
{
	// View
	private RelativeLayout Toolbar_Back_Button;
	private TextView Toolbar_Title_TextView;
	
	private RelativeLayout Rotate_Card_Button, Color_Select_Button, Save_Card_Button, Delete_Card_Button;

	private View Card_Front;
	private LinearLayout Card_Front_Background;
	private EditText Card_Name_EditText;

	private View Card_Black;
	private LinearLayout Card_Black_Background;
	private EditText Card_Limit_EditText, Card_Remark_EditText;

	private TextView Error_Message_TextView;

	private View Color_Manager;
	private CircleImageView Color_white, Color_black, Color_red, Color_green, Color_gold;

	// Animation
	private Flip_Card Flip_Card_Animation;
	private boolean Flip_Card_Flag = true;

	// Value
	private int Color_Code;
	private String Toolbar_Title_Text;
	private String Card_Name_Text, Holder_Name_Text, Card_Limit_Text, Remark_Text;
	private String DatabaseRef_Key;

	// Firebase
	private FirebaseDatabase mFirebaseDatabase;
	private FirebaseAuth mFirebaseAuth;
	private FirebaseUser mFirebaseUser;
	private DatabaseReference mDatabaseReference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.d4_newcash_activity);

		mFirebaseAuth = FirebaseAuth.getInstance();

		mFirebaseUser = mFirebaseAuth.getCurrentUser();

		mFirebaseDatabase = FirebaseDatabase.getInstance();

		mDatabaseReference = mFirebaseDatabase.getReference();
		
		Init_Value();

		Loan_Animation();
		
		Find_View();
		
	}
	
	
	/* 
	 *	初始化資料
	 */
	 private void Init_Value(){
		 
		 Intent Value_Intent = getIntent();

		 DatabaseRef_Key = Value_Intent.getStringExtra("DatabaseRef_Key");

		 if (DatabaseRef_Key != null){

			 // (1) Recycleview 按下時會取出用戶所選取資料, 作修改用途
			 Toolbar_Title_Text = "修改現金帳戶";
			 Color_Code = Value_Intent.getIntExtra("Color_Code", 0);
			 Card_Name_Text = Value_Intent.getStringExtra("Card_Name");
			 Holder_Name_Text = Value_Intent.getStringExtra("Holder_Name");
			 Card_Limit_Text = Value_Intent.getStringExtra("Card_Limit");
			 Remark_Text = Value_Intent.getStringExtra("Remark");

			 mDatabaseReference = mFirebaseDatabase.getReference().child("User Information").child(mFirebaseUser.getUid()).child("Account").child(DatabaseRef_Key);

		 }else {

			 // (2) 全新資料
			 Toolbar_Title_Text = "新增現金帳戶";
			 Holder_Name_Text = mFirebaseUser.getDisplayName();

			 mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("User Information").child(mFirebaseUser.getUid()).child("Account").push();
		 }

	 }
	
	
	/*
	 *
	 */
	private void Find_View(){
		
		// Toolbar
        Toolbar_Back_Button = (RelativeLayout) findViewById(R.id.toolbar_back_button);
        Toolbar_Back_Button.setOnClickListener(Item_Onclick_Listener);

        Toolbar_Title_TextView = (TextView) findViewById(R.id.toolbar_title_textview);
        Toolbar_Title_TextView.setText(Toolbar_Title_Text);

        // Action Button
        Rotate_Card_Button = (RelativeLayout) findViewById(R.id.rotate_card_button);
        Rotate_Card_Button.setOnClickListener(Item_Onclick_Listener);

        Color_Select_Button = (RelativeLayout) findViewById(R.id.color_select_button);
        Color_Select_Button.setOnClickListener(Item_Onclick_Listener);

        Save_Card_Button = (RelativeLayout) findViewById(R.id.save_card_button);
        Save_Card_Button.setOnClickListener(Item_Onclick_Listener);

        Delete_Card_Button = (RelativeLayout) findViewById(R.id.delete_card_button);
        Delete_Card_Button.setOnClickListener(Item_Onclick_Listener);
        if (DatabaseRef_Key == null){
            Delete_Card_Button.setVisibility(View.GONE);
        }

        Card_Front = findViewById(R.id.card_front);

        // Card_Front
		Card_Front_Background = Card_Front.findViewById(R.id.card_front_background);
		Card_Front_Background.setOnClickListener(Item_Onclick_Listener);

        Card_Name_EditText = (EditText) findViewById(R.id.card_name);
        Card_Name_EditText.setText(Card_Name_Text);

        // Card Black
		Card_Black = findViewById(R.id.card_black);

		Card_Black_Background = Card_Black.findViewById(R.id.card_black_background);
		Card_Black_Background.setOnClickListener(Item_Onclick_Listener);

		Card_Limit_EditText = (EditText) findViewById(R.id.card_remark);
		Card_Limit_EditText.setText(Card_Limit_Text);

		Card_Remark_EditText = (EditText) findViewById(R.id.card_remark);
		Card_Remark_EditText.setText(Remark_Text);

		Error_Message_TextView = (TextView) findViewById(R.id.error_message_textview);
        Error_Message_TextView.setText("");

        Color_Manager = findViewById(R.id.color_manager);
        Color_Manager.setVisibility(View.INVISIBLE);

		// Color Manager
		Color_Manager = findViewById(R.id.color_manager);
		Color_Manager.setVisibility(View.INVISIBLE);

		Color_white = Color_Manager.findViewById(R.id.ac_managment_card_color_white);
		Color_white.setOnClickListener(ColorManager_Listener);

		Color_black = Color_Manager.findViewById(R.id.ac_managment_card_color_black);
		Color_black.setOnClickListener(ColorManager_Listener);

		Color_red = Color_Manager.findViewById(R.id.ac_managment_card_color_red);
		Color_red.setOnClickListener(ColorManager_Listener);

		Color_green = Color_Manager.findViewById(R.id.ac_managment_card_color_green);
		Color_green.setOnClickListener(ColorManager_Listener);

		Color_gold = Color_Manager.findViewById(R.id.ac_managment_card_color_gold);
		Color_gold.setOnClickListener(ColorManager_Listener);

        Change_Card_Color(Color_Code);
	}

	
	/*
	 *	點擊使用
	 */
	private View.OnClickListener Item_Onclick_Listener = new View.OnClickListener(){

		@Override
		public void onClick(View view)
		{
			// TODO: Implement this method
			switch(view.getId()){
				
				case R.id.toolbar_back_button:
					Show_Exit_Dialog();
					break;
					
				case R.id.rotate_card_button:
					Flip_Card();
					break;
				
				case R.id.color_select_button:
					Show_Color_Manager();
					break;
					
				case R.id.save_card_button:
					Save_Card();
					break;
					
				case R.id.delete_card_button:
					Delete_Card();
					break;

				case R.id.card_front_background:
					Flip_Card();
					break;

				case R.id.card_black_background:
					Flip_Card();
					break;

			}
		}
		 
	 };

	/*
	 *  攔截返回鍵
	 *  當用戶按下返回鍵時, 提示用戶所輸入資料末保仔存
	 */
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		Show_Exit_Dialog();
	}


	 /*
	  *	新增資料
	  */
	  private void Save_Card(){

		  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		  imm.hideSoftInputFromWindow(Card_Name_EditText.getWindowToken(), 0);

		  Card_Name_Text = Card_Name_EditText.getText().toString();
		  Card_Limit_Text = Card_Limit_EditText.getText().toString();
		  Remark_Text = Card_Remark_EditText.getText().toString();

		  if (Card_Name_Text == "" | Card_Name_Text == null | Card_Name_Text.isEmpty()){
			  Error_Message_TextView.setText("Name is null");
			  return;
		  }

		  if (DatabaseRef_Key == null){
			  DatabaseRef_Key = mFirebaseDatabase.getReference().push().getKey();
		  }

		  Account_Model mAccount_Data = new Account_Model(
				  Color_Code,
				  Card_Name_Text,
				  "Cash",
				  Card_Limit_Text,
				  Remark_Text);

		  mDatabaseReference = mFirebaseDatabase.getReference().child("Account").child(DatabaseRef_Key);
		  mDatabaseReference.setValue(mAccount_Data);

		  mDatabaseReference = mFirebaseDatabase.getReference().child("Account").child(DatabaseRef_Key).child(mFirebaseUser.getUid());
//		  mDatabaseReference.setValue(mAccount_Data.getLast_TimeStamp());

		  Log.e("Firebase Action", "新增一條紀錄至 Firebase");
		  finish();

	  }
	 
	 

    /*
     *  Done
     *  當用戶按下返回鍵時, 提示用戶所輸入資料末保仔存
     */
    private void Show_Exit_Dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("確定退出? 所有資料將不會儲存");

        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});

        builder.setNegativeButton("取消", null);

        builder.show();
    }
	
	
	/*
     *  Done
     *  刪除用戶所選取的資料
     */
    private void Delete_Card(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("確定刪除?");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mDatabaseReference.removeValue();
					finish();
				}
			});
        builder.setNegativeButton("取消", null);
        builder.show();

    }


	/*
	 *  Done
	 *  1. 當用戶按下反轉按鈕, 進行反轉動畫
	 *  2. 當用戶按下卡片時, 進行反轉動畫
	 */
	private void Flip_Card(){

		Card_Front.setVisibility(View.VISIBLE);
		Card_Black.setVisibility(View.VISIBLE);

		if(Flip_Card_Flag){

			Flip_Card_Animation.Start_Card_RightIn(Card_Black);
			Flip_Card_Animation.Start_Card_RightOut(Card_Front);
			Flip_Card_Flag = false;
//			Remark_EditText.requestFocus();

		}else {

			Flip_Card_Animation.Start_Card_LeftIn(Card_Black);
			Flip_Card_Animation.Start_Card_LeftOut(Card_Front);
			Flip_Card_Flag = true;
			Card_Name_EditText.requestFocus();

		}

	}


	/*
	 *  Done
	 *  顯示顏色選區, 供用戶改變卡片顏色
	 */
	private void Show_Color_Manager(){

		// 關閉鍵盤
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(Card_Name_EditText.getWindowToken(), 0);

		if (Color_Manager.getVisibility() == View.VISIBLE){

			Color_Manager.setVisibility(View.INVISIBLE);

		}else {

			Color_Manager.setVisibility(View.VISIBLE);

		}

	}


	/*
	 *  Done
	 *  預載動畫
	 */
	private void Loan_Animation(){

		// 卡片反轉動畫
		Flip_Card_Animation = new Flip_Card(this);
	}


	/*
	 *  Done
	 *  改變卡顏色特效
	 */
	private void Card_Background_Anim(View view){
		/*
		 http://anjithsasindran.in/blog/2015/08/15/material-sharing-card/
		*/
		int centerX = (view.getLeft() + view.getRight()) / 2;
		int centerY = (view.getTop() + view.getBottom()) / 2;

		int startRadius = 0;
		// get the final radius for the clipping circle
		int endRadius = Math.max(view.getWidth(), view.getHeight());

		// create the animator for this view (the start radius is zero)
		Animator anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

		// make the view visible and start the animation
		view.setVisibility(View.VISIBLE);
		anim.start();
	}


	/*
	 *  Done
	 *  改變卡顏色
	 */
	private View.OnClickListener ColorManager_Listener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

			switch (view.getId()){

				case R.id.ac_managment_card_color_white:
					Card_Background_Anim(Card_Front_Background);
					Card_Background_Anim(Card_Black_Background);
					Color_Code = 0;
					Change_Card_Color(Color_Code);
					break;

				case R.id.ac_managment_card_color_black:
					Card_Background_Anim(Card_Front_Background);
					Card_Background_Anim(Card_Black_Background);
					Color_Code = 1;
					Change_Card_Color(Color_Code);
					break;

				case R.id.ac_managment_card_color_red:
					Card_Background_Anim(Card_Front_Background);
					Card_Background_Anim(Card_Black_Background);
					Color_Code = 2;
					Change_Card_Color(Color_Code);
					break;

				case R.id.ac_managment_card_color_green:
					Card_Background_Anim(Card_Front_Background);
					Card_Background_Anim(Card_Black_Background);
					Color_Code = 3;
					Change_Card_Color(Color_Code);
					break;

				case R.id.ac_managment_card_color_gold:
					Card_Background_Anim(Card_Front_Background);
					Card_Background_Anim(Card_Black_Background);
					Color_Code = 4;
					Change_Card_Color(Color_Code);
					break;
			}
		}
	};


	/*
	 *  Done
	 *  改變卡顏色
	 */
	private void Change_Card_Color(int Code){

		switch(Code){

			case 0:
				Card_Front_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_white));
				Card_Black_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_white));
				break;

			case 1:
				Card_Front_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_black));
				Card_Black_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_black));
				break;

			case 2:
				Card_Front_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_red));
				Card_Black_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_red));
				break;

			case 3:
				Card_Front_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_green));
				Card_Black_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_green));
				break;

			case 4:
				Card_Front_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_gold));
				Card_Black_Background.setBackground(getResources().getDrawable(R.drawable.radius_gradient_gold));
				break;

			case 5:
				break;
		}
	}




}
