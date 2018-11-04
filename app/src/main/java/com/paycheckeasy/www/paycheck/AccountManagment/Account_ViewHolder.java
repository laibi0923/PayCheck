package com.paycheckeasy.www.paycheck.AccountManagment;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paycheckeasy.www.paycheck.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Account_ViewHolder extends RecyclerView.ViewHolder{

    public int position = 0;
	public DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");


	// Card
    public TextView Last_Modift_TextView;
    public LinearLayout Card_Front_Background;
    public EditText Bank_Name_EditTextView, Bank_No_First_EditText, Bank_No_Last_EditText, Holder_Name_EditText, Expiry_Date_EditText;
    public ImageView BankCard_Associations;
    public RelativeLayout Edit_Button, ViewAll_Button, NewRecord_Button;
    public LinearLayout ViewLastest_Button;
    public TextView Card_Message_TextView;


    private String message_1 = "本月共新增";
    private String message_2 = "條紀錄, 已使用共";
    private String message_3 = "簽帳額, 餘額為";


	// Cash
    public EditText Cash_Name_EditText;


    public Account_ViewHolder(View view, String Type){

        super(view);

        if (Type == "Cash"){

            // Cash
            Last_Modift_TextView = view.findViewById(R.id.last_modify_textview);

            Card_Front_Background = view.findViewById(R.id.card_front_background);

            Cash_Name_EditText = view.findViewById(R.id.card_name);
            Cash_Name_EditText.setFocusable(false);
            Cash_Name_EditText.setFocusableInTouchMode(false);

            Edit_Button = view.findViewById(R.id.card_edit_button);

            ViewAll_Button = view.findViewById(R.id.viewall_button);

            NewRecord_Button = view.findViewById(R.id.new_record_button);

            ViewLastest_Button = view.findViewById(R.id.viewlatest_button);

            Card_Message_TextView = view.findViewById(R.id.card_message);

        }else {

            Last_Modift_TextView = view.findViewById(R.id.last_modify_textview);

            Card_Front_Background = view.findViewById(R.id.card_front_background);

            Bank_Name_EditTextView = view.findViewById(R.id.bank_name);
            Bank_Name_EditTextView.setFocusable(false);
            Bank_Name_EditTextView.setFocusableInTouchMode(false);

            Bank_No_First_EditText = view.findViewById(R.id.bank_no_first);
            Bank_No_First_EditText.setFocusable(false);
            Bank_No_First_EditText.setFocusableInTouchMode(false);

            Bank_No_Last_EditText = view.findViewById(R.id.bank_no_last);
            Bank_No_Last_EditText.setFocusable(false);
            Bank_No_Last_EditText.setFocusableInTouchMode(false);

            Holder_Name_EditText = view.findViewById(R.id.holder_name);
            Holder_Name_EditText.setFocusable(false);
            Holder_Name_EditText.setFocusableInTouchMode(false);

            Expiry_Date_EditText = view.findViewById(R.id.expiry_date);
            Expiry_Date_EditText.setFocusable(false);
            Expiry_Date_EditText.setFocusableInTouchMode(false);

            BankCard_Associations = view.findViewById(R.id.bankcard_associations);

            Edit_Button = view.findViewById(R.id.card_edit_button);

            ViewAll_Button = view.findViewById(R.id.viewall_button);

            NewRecord_Button = view.findViewById(R.id.new_record_button);

            ViewLastest_Button = view.findViewById(R.id.viewlatest_button);

            Card_Message_TextView = view.findViewById(R.id.card_message);
        }

    }


    public void setCardValue(Account_Model value) {

        Last_Modift_TextView.setText(formatter.format(value.getLast_TimeStampLong()));

        int Color_Code = value.getColor_Code();
        Drawable Background_Color = null;

        if(Color_Code == 0){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_white);
        }else if(Color_Code == 1){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_black);
        }else if(Color_Code == 2){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_red);
        }else if(Color_Code == 3){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_green);
        }else if(Color_Code == 4){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_gold);
        }

        Card_Front_Background.setBackground(Background_Color);

        Bank_Name_EditTextView.setText(value.getCard_Name());
        Bank_No_First_EditText.setText(value.getFirst_Num());
        Bank_No_Last_EditText.setText(value.getLast_Num());
        Holder_Name_EditText.setText(value.getHolder_Name());
        Expiry_Date_EditText.setText(value.getExpiry_Date());
        
        String Bankcard_Associations = value.getType();
        
        if (Bankcard_Associations.equals("VISA")){
            BankCard_Associations.setImageResource(R.drawable.visa_card_logo);
        }else if (Bankcard_Associations.equals("MASTER")){
            BankCard_Associations.setImageResource(R.drawable.master_card_logo);
        }else if (Bankcard_Associations.equals("AE")){
            BankCard_Associations.setImageResource(R.drawable.ae_card_logo);
        }else if (Bankcard_Associations.equals("JCB")){
            BankCard_Associations.setImageResource(R.drawable.jcb_card_logo);
        }else if (Bankcard_Associations.equals("UNION")){
            BankCard_Associations.setImageResource(R.drawable.unionpay_card_logo);
        }

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

        // 查詢路徑
        Query query = mDatabaseReference.child("History");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               int record = (int) dataSnapshot.getChildrenCount();
               Card_Message_TextView.setText(message_1 + String.valueOf(record) + message_2 + message_3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


	public void setCashValue(Account_Model value){

		Last_Modift_TextView.setText(formatter.format(value.getLast_TimeStampLong()));

        int Color_Code = value.getColor_Code();
        Drawable Background_Color = null;

        if(Color_Code == 0){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_white);
        }else if(Color_Code == 1){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_black);
        }else if(Color_Code == 2){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_red);
        }else if(Color_Code == 3){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_green);
        }else if(Color_Code == 4){
            Background_Color = itemView.getContext().getResources().getDrawable(R.drawable.radius_gradient_gold);
        }

        Card_Front_Background.setBackground(Background_Color);
		
		Cash_Name_EditText.setText(value.getCard_Name());

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();

        // 查詢路徑
        Query query = mDatabaseReference.child("History");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int record = (int) dataSnapshot.getChildrenCount();
                Card_Message_TextView.setText(message_1 + String.valueOf(record) + message_2 + message_3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
	}


}
