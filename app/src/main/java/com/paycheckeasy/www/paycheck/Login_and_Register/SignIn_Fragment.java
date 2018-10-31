package com.paycheckeasy.www.paycheck.Login_and_Register;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.paycheckeasy.www.paycheck.MainActivity;
import com.paycheckeasy.www.paycheck.PublicClass.Firebase_ErrorMessage_Handler;
import com.paycheckeasy.www.paycheck.R;

/*
 *  Create Date : 5 Oct 2018
 *  Create By : Leo Lai
 *  Function : 登入頁面
 *  1) 處理登入資料正確
 *  2) 協助使用者重置帳戶已忘記的密碼
 */

public class SignIn_Fragment extends Fragment {

    // View
    private EditText Email_EditText, Password_EditText;
    private RelativeLayout SubMit_Btn;
    private TextView Error_Message_TextView, Forget_PW_BTN, Submit_Title_TextView;
    private RelativeLayout Loading_mask_Layout;

    // Vaule
    private String Email_Text, Password_Text;
    private String Profile_UserId, Profile_UserName, Profile_EmailAddress, Profile_UserPhotoLink, Profile_VerifyEmailStatus;

    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		// 開啟 Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // 用於註冊後使用者無需從新輸入登入資料
		Intent mIntent = getActivity().getIntent();
        Email_Text = mIntent.getStringExtra("Email");
        Password_Text = mIntent.getStringExtra("Password");
	}
	
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.b2_login_screen_content, container, false);

        Find_View(v);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

				Update_UI();
				
            }
        };

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        super.onStop();
    }


    /*
     *  Done
     */
    private void Find_View (View v){

        Email_EditText = v.findViewById(R.id.login_email_edittext);
        Email_EditText.setText(Email_Text);
        Email_EditText.addTextChangedListener(User_Input_Textwatcher);

        Password_EditText = v.findViewById(R.id.login_password_edittext);
        Password_EditText.setText(Password_Text);
        Password_EditText.addTextChangedListener(User_Input_Textwatcher);
		Password_EditText.setOnEditorActionListener(KeyDown_Action_Listener);

		Error_Message_TextView = v.findViewById(R.id.error_message_textview);
		Error_Message_TextView.setText("");

        Forget_PW_BTN = v.findViewById(R.id.forget_password_btn);
		Forget_PW_BTN.setVisibility(View.VISIBLE);
        Forget_PW_BTN.setOnClickListener(Item_OnclickListener);

        SubMit_Btn = v.findViewById(R.id.login_submit_btn);
        SubMit_Btn.setOnClickListener(Item_OnclickListener);

        Submit_Title_TextView = v.findViewById(R.id.login_submit_title);

        Loading_mask_Layout = v.findViewById(R.id.loading_mask_layout);
    }


    /*
     *  Done
     */
    private View.OnClickListener Item_OnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch(view.getId()){

                case R.id.forget_password_btn:
                    if (Email_EditText.getText().toString().isEmpty() | Email_EditText.getText().toString() == null){
                        Error_Message_TextView.setText(getResources().getString(R.string.login_forgot_pw_error));
                    }else {
                        Forgot_Password();
                    }
                    break;

                case R.id.login_submit_btn:
                    Check_Login_Value();
                    break;
            } 
        }
    };


    /*
     *  Done
     */
	private TextWatcher User_Input_Textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!Email_EditText.getText().toString().isEmpty() && !Password_EditText.getText().toString().isEmpty()){
                Submit_Title_TextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            }else {
                Submit_Title_TextView.setTextColor(getResources().getColor(R.color.color_editor_textcolor));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };



	/*
	 *  Done
	 *  Check user input information, if correct go to next step
	 */
	private void Check_Login_Value() {

		String Email = Email_EditText.getText().toString();
		
        String Password = Password_EditText.getText().toString();
		
		if (Email.isEmpty() | Password.isEmpty()){ 
            Error_Message_TextView.setText(getResources().getString(R.string.login_errormsg1));
			return;
        }
		
		if (!Email.isEmpty() && !Password.isEmpty()) {

            Loading_mask_Layout.setVisibility(View.VISIBLE);
			
			Error_Message_TextView.setText("");
			
			mFirebaseAuth.signInWithEmailAndPassword(Email, Password)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						
						if (task.isSuccessful()) {
							
							Update_UI();
							
						} else {
							
							/**  登入失敗, 顯示失敗結果 **/
							String Error_Code = ((FirebaseAuthException) task.getException()).getErrorCode();
							Error_Message_TextView.setText(new Firebase_ErrorMessage_Handler(Error_Code).Get_Error_Message());
							Log.e("FirebaseAuth", "Login Fail : " + task.getException().getMessage() + "(" + Error_Code + ")");
						}
						
						Loading_mask_Layout.setVisibility(View.GONE);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
					}
				});
		}
		
    }


	/*
	 *  Done
	 *  detect user keydown enter when input finish login details
	 */
	private TextView.OnEditorActionListener KeyDown_Action_Listener = new TextView.OnEditorActionListener(){

		@Override
		public boolean onEditorAction(TextView tv, int actionId, KeyEvent event)
		{
			// TODO: Implement this method
			if(actionId == EditorInfo.IME_ACTION_DONE){

				Check_Login_Value();

			}
			return false; 
		}
		
	};


	
	/*
	 *  協助用戶重設密碼
	 */
	private void Forgot_Password(){

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("我們已發送一封重設密碼電郵至你所登記的電郵地址");

        builder.setPositiveButton("確認", null);

        builder.show();
		
		String current_email = Email_EditText.getText().toString();

		if (current_email != null || !current_email.isEmpty() || current_email != null){

//			mFirebase_Helper.Reset_Password(Email_EditText.getText().toString());

		}
		
	}



	/*
	 *
	 */
	private void Update_UI(){

		mFirebaseUser = mFirebaseAuth.getCurrentUser();

		if (mFirebaseUser != null && mFirebaseUser.isEmailVerified()){

			Log.e("onAuthStateChanged", "登入:"+ mFirebaseUser.getUid());
			Intent mIntent = new Intent(getActivity(), MainActivity.class);
			mIntent.putExtra("Uid", mFirebaseUser.getUid());
			mIntent.putExtra("DisplayName", mFirebaseUser.getDisplayName());
			mIntent.putExtra("Email", mFirebaseUser.getEmail());
			if (mFirebaseUser.getPhotoUrl() != null){
                mIntent.putExtra("PhotoUri", mFirebaseUser.getPhotoUrl().toString());
            }else {
                mIntent.putExtra("PhotoUri", "");
            }

			startActivity(mIntent);
			getActivity().finish();

		}
    }


}
