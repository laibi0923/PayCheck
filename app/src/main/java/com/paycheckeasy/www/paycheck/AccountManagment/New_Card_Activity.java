package com.paycheckeasy.www.paycheck.AccountManagment;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paycheckeasy.www.paycheck.Animation.Flip_Card;
import com.paycheckeasy.www.paycheck.PublicClass.CircleImageView;
import com.paycheckeasy.www.paycheck.R;

import java.util.HashMap;
import java.util.Map;


public class New_Card_Activity extends AppCompatActivity {

    // View
    private RelativeLayout Toolbar_Back_Button;
    private TextView Toolbar_Title_TextView;

    private RelativeLayout Rotate_Card_Button, Color_Select_Button, Save_Card_Button, Delete_Card_Button;

    private View Card_Front;
    private LinearLayout Card_Front_Background;
    private EditText Bank_Name_EditText, Bank_No_First_EditText, Bank_No_Last_EditText, Holder_Name_EditText, Expiry_Date_EditText;
    private ImageView BankCard_Associations;

    private View Card_Black;
    private LinearLayout Card_Black_Background;
    private EditText Credit_Limit_EditText, Remark_EditText;

    private TextView Error_Message_TextView;

    private View Color_Manager;
    private CircleImageView Color_white, Color_black, Color_red, Color_green, Color_gold;

    // Animation
    private Flip_Card Flip_Card_Animation;
    private boolean Flip_Card_Flag = true;

    // Value
    private int Color_Code;
    private String Toolbar_Title_Text;
    private String Bank_Name_Text, Bank_No_First_Text, Bank_No_Last_Text, Holder_Name_Text, Expiry_Date_Text, BankCard_Associations_Text, Credit_Limit_Text, Remark_Text;
    private String DatabaseRef_Key;

    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseFirestore mFirebaseFirestore;
    private DocumentReference mDocumentReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.d1_newcard_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        Init_Value();

        Loan_Animation();

        Find_View();

    }


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
     *  1. Recycleview 按下時會取出用戶所選取資料, 作修改用途
     *  2. 全新資料
     */
    private void Init_Value(){

        Intent Value_Intent  = getIntent();

        DatabaseRef_Key = Value_Intent.getStringExtra("DatabaseRef_Key");

        if (DatabaseRef_Key != null){

            // (1) Recycleview 按下時會取出用戶所選取資料, 作修改用途
            Toolbar_Title_Text = "修改信用卡帳戶";
            Color_Code = Value_Intent.getIntExtra("Color_Code", 0);
            Bank_Name_Text = Value_Intent.getStringExtra("Bank_Name");
            Bank_No_First_Text = Value_Intent.getStringExtra("Bank_No_First");
            Bank_No_Last_Text = Value_Intent.getStringExtra("Bank_No_Last");
            Holder_Name_Text = Value_Intent.getStringExtra("Holder_Name");
            Expiry_Date_Text = Value_Intent.getStringExtra("Expiry_Date");
            BankCard_Associations_Text = Value_Intent.getStringExtra("BankCard_Associations");
            Credit_Limit_Text = Value_Intent.getStringExtra("Credit_Limit");
            Remark_Text = Value_Intent.getStringExtra("Remark");
			
			mDocumentReference = mFirebaseFirestore.collection("Account").document(DatabaseRef_Key);

        }else {

            // (2) 全新資料
            Toolbar_Title_Text = "新增信用卡帳戶";
            Holder_Name_Text = mFirebaseUser.getDisplayName();
            BankCard_Associations_Text = "";

			mDocumentReference = mFirebaseFirestore.collection("Account").document();
        }
		
    }


    /*
     *  Find View
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

        // Card Front
        Card_Front = findViewById(R.id.card_front);

        Card_Front_Background = Card_Front.findViewById(R.id.card_front_background);
        Card_Front_Background.setOnClickListener(Item_Onclick_Listener);

        Bank_Name_EditText = Card_Front.findViewById(R.id.bank_name);
        Bank_Name_EditText.setText(Bank_Name_Text);

        Bank_No_First_EditText = Card_Front.findViewById(R.id.bank_no_first);
        Bank_No_First_EditText.setText(Bank_No_First_Text);
        Bank_No_First_EditText.addTextChangedListener(Bank_No_First_TextWatcher);

        Bank_No_Last_EditText = Card_Front.findViewById(R.id.bank_no_last);
        Bank_No_Last_EditText.setText(Bank_No_Last_Text);
        Bank_No_Last_EditText.addTextChangedListener(Bank_No_Last_TextWatcher);

        Holder_Name_EditText = Card_Front.findViewById(R.id.holder_name);
        Holder_Name_EditText.setText(Holder_Name_Text);

        Expiry_Date_EditText = Card_Front.findViewById(R.id.expiry_date);
        Expiry_Date_EditText.setText(Expiry_Date_Text);
        Expiry_Date_EditText.addTextChangedListener(ExpiryDate_TextWatcher);

        BankCard_Associations = Card_Front.findViewById(R.id.bankcard_associations);
        Bank_Associations_Selector(BankCard_Associations_Text);

        // Card Black
        Card_Black = findViewById(R.id.card_black);

        Card_Black_Background = Card_Black.findViewById(R.id.card_black_background);
        Card_Black_Background.setOnClickListener(Item_Onclick_Listener);

        Credit_Limit_EditText = Card_Black.findViewById(R.id.credit_limit_edittext);
        Credit_Limit_EditText.setText(Credit_Limit_Text);

        Remark_EditText = Card_Black.findViewById(R.id.remark);
        Remark_EditText.setText(Remark_Text);

        // Error Messgae
        Error_Message_TextView = (TextView) findViewById(R.id.error_message_textview);
        Error_Message_TextView.setText("");

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
     *  Done
     *  點撃事件
     */
    private View.OnClickListener Item_Onclick_Listener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            switch (view.getId()){

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
     *  Done
     *  判斷用戶所輸入的頭四位號碼屬哪一發卡機構
     *  當輸入至四位時移到下焦點
     */
    private TextWatcher Bank_No_First_TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            try{

                Bank_No_First_EditText.removeTextChangedListener(this);

                if (editable.toString().length() == 4){
                    Bank_No_Last_EditText.requestFocus();
                }

                if (editable.toString().length() < 3) {
                    BankCard_Associations_Text = "";
                    Bank_Associations_Selector(BankCard_Associations_Text);
                }

                // Visa
                if (editable.toString().length() > 2 && editable.toString().substring(0, 1).equals("4")){
                    BankCard_Associations_Text = "VISA";
                    Bank_Associations_Selector(BankCard_Associations_Text);
                }

                // Master
                if (editable.toString().length() > 2 && editable.toString().substring(0, 1).equals("5")){
                    BankCard_Associations_Text = "MASTER";
                    Bank_Associations_Selector(BankCard_Associations_Text);
                }

                // Union Pay
                if (editable.toString().length() > 2 && editable.toString().substring(0, 2).equals("62") | editable.toString().substring(0, 2).equals("60") | editable.toString().substring(0, 1).equals("9")){
                    BankCard_Associations_Text = "UNION";
                    Bank_Associations_Selector(BankCard_Associations_Text);
                }

                //  AE or JCB, 由於同為3開頭, 所以先判斷3再判斷是否 37 / 34
                if (editable.toString().length() > 2 && editable.toString().substring(0, 1).equals("3")){

                    if(editable.toString().substring(0, 2).equals("37") | editable.toString().substring(0, 2).equals("34")){
                        BankCard_Associations_Text = "AE";
                        Bank_Associations_Selector(BankCard_Associations_Text);
                    }else {
                        BankCard_Associations_Text = "JCB";
                        Bank_Associations_Selector(BankCard_Associations_Text);
                    }

                }

                Bank_No_First_EditText.addTextChangedListener(this);


            }catch (NumberFormatException mNumberFormatException){

                mNumberFormatException.printStackTrace();

            }

        }
    };


    /*
     *  Done
     *  當輸入至四位時移到下焦點
     */
    private  TextWatcher Bank_No_Last_TextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            try {

                Bank_No_Last_EditText.removeTextChangedListener(this);

                if (editable.toString().length() == 4){

                    Holder_Name_EditText.requestFocus();

                }

                Bank_No_Last_EditText.addTextChangedListener(this);

            }catch (NumberFormatException mNumberFormatException){

                mNumberFormatException.printStackTrace();

            }

        }
    };


    /*
     *  Done
     *  控制 Expiry Date 格式
     */
    private TextWatcher ExpiryDate_TextWatcher = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
        {
            // TODO: Implement this method
        }

        @Override
        public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
        {
            // TODO: Implement this method
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            char slash = '/';
            if(s.length() > 0 && (s.length()%3) == 0)
            {
                char c = s.charAt(s.length()-1);
                if(Character.isDigit(c))
                    s.insert(s.length()-1,String.valueOf(slash));
            }

        }

    };


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
            Remark_EditText.requestFocus();

        }else {

            Flip_Card_Animation.Start_Card_LeftIn(Card_Black);
            Flip_Card_Animation.Start_Card_LeftOut(Card_Front);
            Flip_Card_Flag = true;
            Bank_Name_EditText.requestFocus();

        }

    }


    /*
     *  Done
     *  顯示顏色選區, 供用戶改變卡片顏色
     */
    private void Show_Color_Manager(){

        // 關閉鍵盤
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Bank_Name_EditText.getWindowToken(), 0);

        if (Color_Manager.getVisibility() == View.VISIBLE){

            Color_Manager.setVisibility(View.INVISIBLE);

        }else {

            Color_Manager.setVisibility(View.VISIBLE);

        }

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
                mDocumentReference.delete();
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }


    /*
     *  Done
     *  1. 新增新資料到 Database
     *  2. 修改現有資料至 Database
     */
    private void Save_Card(){

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Bank_Name_EditText.getWindowToken(), 0);

        Bank_Name_Text = Bank_Name_EditText.getText().toString();
        Bank_No_First_Text = Bank_No_First_EditText.getText().toString();
        Bank_No_Last_Text = Bank_No_Last_EditText.getText().toString();
        Expiry_Date_Text = Expiry_Date_EditText.getText().toString();

        if (Bank_Name_Text == "" | Bank_Name_Text == null | Bank_Name_Text.isEmpty()){
            Error_Message_TextView.setText("Bank No is worng");
            return;
        }

        if(Bank_No_First_Text.length() < 4){
            Error_Message_TextView.setText("Bank No is worng");
            return;
        }

        if(Bank_No_Last_Text.length() < 4){
            Error_Message_TextView.setText("Bank No is worng");
            return;
        }

        if(BankCard_Associations_Text == "" | BankCard_Associations_Text == null | BankCard_Associations_Text == "reset"){
            // Toast.makeText(this, "Bank AC is worng", Toast.LENGTH_SHORT).show();
            Error_Message_TextView.setText("Bank No is worng");
            return;
        }

        if(Expiry_Date_Text != null | Expiry_Date_Text != ""){

            int ExpiryDate_Month = Integer.parseInt(Expiry_Date_Text.toString().substring(0, 2));
            int ExpiryDate_Year = Integer.parseInt(Expiry_Date_Text.toString().substring(3, 5));

            if(Expiry_Date_Text.length() < 5){
                // Toast.makeText(this, "Expiry Date is worng", Toast.LENGTH_SHORT).show();
                Error_Message_TextView.setText("Expiry Date formate worng");
                return;
            }

            if(ExpiryDate_Month > 12) {
                // Toast.makeText(this, "Expiry Month is worng", Toast.LENGTH_SHORT).show();
                Error_Message_TextView.setText("Expiry Date is worng");
                return;
            }

            if(ExpiryDate_Year > 18 + 5){
                // Toast.makeText(this, "Expiry Year is wrong", Toast.LENGTH_SHORT).show();
                Error_Message_TextView.setText("Expiry Date is worng");
                return;
            }

        }else{
            Error_Message_TextView.setText("Expiry Date Null");
            return;
        }

        Account_Model mAccount_Data = new Account_Model(
                Color_Code,
                Bank_Name_EditText.getText().toString(),
                Bank_No_First_EditText.getText().toString(),
                Bank_No_Last_EditText.getText().toString(),
                Holder_Name_EditText.getText().toString(),
                Expiry_Date_EditText.getText().toString(),
                BankCard_Associations_Text,
                Credit_Limit_EditText.getText().toString(),
                Remark_EditText.getText().toString());
       
        mDocumentReference.set(mAccount_Data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

//                Map<String, Object> member_list = new HashMap<>();
//                member_list.put(mFirebaseUser.getUid(), FieldValue.serverTimestamp());
//                mDocumentReference.update(member_list);
//
				// Set Create Date to FireStore
				if (DatabaseRef_Key == null){

					Map<String, Object> Create_Date = new HashMap<>();
					Create_Date.put("create_Date", FieldValue.serverTimestamp());
					mDocumentReference.update(Create_Date);

				}
				
				Map<String, Object> ownerby = new HashMap<>();
				ownerby.put(mFirebaseUser.getUid(), "owner");
				mDocumentReference.collection("member").add(ownerby);
				
                finish();
            }
        });
		
		
    }


    /*
     *  Done
     *  負責管理發卡機構
     */
    private void Bank_Associations_Selector(String Type){

        switch(Type){

            case "":
                BankCard_Associations.setImageResource(android.R.color.transparent);
                break;

            case "VISA":
                BankCard_Associations.setImageResource(R.drawable.visa_card_logo);
                break;

            case "MASTER":
                BankCard_Associations.setImageResource(R.drawable.master_card_logo);
                break;

            case "AE":
                BankCard_Associations.setImageResource(R.drawable.ae_card_logo);
                break;

            case "UNION":
                BankCard_Associations.setImageResource(R.drawable.unionpay_card_logo);
                break;

            case "JCB":
                BankCard_Associations.setImageResource(R.drawable.jcb_card_logo);
                break;
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







    /*

    private void Digit_16_Card(Editable s, char space){
        if (s.length() > 0 && (s.length()%5) == 0){
            char c = s.charAt(s.length() - 1);
            if (Character.isDigit(c)){
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }
    }

    private void Digit_15_Card(Editable s, char space){
        if (s.length() == 5){
            char c = s.charAt(s.length() - 1);
            if (Character.isDigit(c)){
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }else if(s.length() == 12){
            char c = s.charAt(s.length() - 1);
            if (Character.isDigit(c)){
                s.insert(s.length() - 1, String.valueOf(space));
            }
        }
    }

    private void Max_Card_Num_19(Editable s){
        if(s.length() == 19){
            HolderName_EditText.requestFocus();
        }
    }

    private void Max_Card_Num_17(Editable s){
        if(s.length() == 17){
            HolderName_EditText.requestFocus();
        }
    }

    */

}
