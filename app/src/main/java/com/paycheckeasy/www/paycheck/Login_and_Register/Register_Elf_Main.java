package com.paycheckeasy.www.paycheck.Login_and_Register;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.view.inputmethod.*;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.paycheckeasy.www.paycheck.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Register_Elf_Main extends AppCompatActivity
{
	private DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private Fragment Current_Fragment;
	public String Register_Email_Value, Register_Password_Value, Register_UserName_Value, Register_Gender_Value, Register_DOB_Value;

	private FirebaseAuth mFirebaseAuth;
	private FirebaseUser mFirebaseUser;

	private FirebaseFirestore mFirebaseFirestore;
	private DocumentReference mDocumentReference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		setContentView(R.layout.b8_register_elf);

		formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));

		mFirebaseAuth = FirebaseAuth.getInstance();

		mFirebaseFirestore = FirebaseFirestore.getInstance();
		
//		Current_Fragment = new Register_Email();
		Fragment_Transaction(new Register_Email(), 0, 0, false, "email");
	}


	//	註冊新使用者
	public void Register_New_User(){

		mFirebaseAuth.createUserWithEmailAndPassword(Register_Email_Value, Register_Password_Value).addOnCompleteListener(Register_Elf_Main.this, new OnCompleteListener<AuthResult>(){
				@Override
				public void onComplete(Task<AuthResult> task)
				{
					// TODO: Implement this method
					if(task.isSuccessful()){
						
						Log.e("FirebaseAuth Action", "Register Successful");

                        mFirebaseUser = mFirebaseAuth.getCurrentUser();

						if (mFirebaseUser != null){


							//	步驟一 : 於 FirebaseAuth 建立新使用者
							UserProfileChangeRequest mUserProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Register_UserName_Value).build();
							mFirebaseUser.updateProfile(mUserProfileChangeRequest);


							//	步驟二 : 將使用者資料存入 FirebaseFireStorge
							mDocumentReference = mFirebaseFirestore.collection("User Profile").document(mFirebaseUser.getUid());

							Map<String, Object> User_Profile_Map = new HashMap<>();

							User_Profile_Map.put("Create_Date", FieldValue.serverTimestamp());
							User_Profile_Map.put("Email_Address", Register_Email_Value);
							User_Profile_Map.put("Password", Register_Password_Value);
							User_Profile_Map.put("User_Name", Register_UserName_Value);
							User_Profile_Map.put("Gender", Register_Gender_Value);
							User_Profile_Map.put("DOB", Register_DOB_Value);

							mDocumentReference.set(User_Profile_Map);


							//	步驟三 : 發送確定電郵予使用者
							mFirebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
									@Override
									public void onComplete(@NonNull Task<Void> task) {

										if (task.isSuccessful()){
											Log.e("Firebase Auth", "Successful sent verify Email to:" + mFirebaseUser.getEmail());
										}else {
											Log.e("Firebase Auth", "Failed sent verify Email:" + task.getException().getMessage());
										}

									}
								});


							// 步驟四 : 登出所有狀態
                            mFirebaseAuth.signOut();
                            Log.e("Register", "Signout");


							// 步驟五 : 跳轉至登入畫面
                            Intent mIntent = new Intent(Register_Elf_Main.this, LoginScreen_Main.class);
                            mIntent.putExtra("Email", Register_Email_Value);
                            mIntent.putExtra("Password", Register_Password_Value);
                            startActivity(mIntent);
                            finish();
                        }

					}else{

						Log.e("FirebaseAuth Action", "Register Fail:" + task.getException().getMessage());

					}
				}
			});
	}


	// Open Soft keybord event
	public void Open_keybord(View mView){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mView, 0);
	}

	public void Fragment_Transaction(Fragment fm, int anim_enter, int anim_exit, boolean addBackStack, String Tag){

		FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mFragmentTransaction.setCustomAnimations(anim_enter, anim_exit, anim_exit, anim_enter);
		if (addBackStack){
			mFragmentTransaction.addToBackStack(null);
		}
		mFragmentTransaction.replace(R.id.register_elf_FrameLayout, fm, Tag);
		mFragmentTransaction.commit();
	}

}
