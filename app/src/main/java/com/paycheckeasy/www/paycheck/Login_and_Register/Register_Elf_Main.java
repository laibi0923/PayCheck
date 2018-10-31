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

import com.paycheckeasy.www.paycheck.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Register_Elf_Main extends AppCompatActivity
{
	private DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private Fragment Current_Fragment;
	public String Register_Email_Value, Register_Password_Value, Register_UserName_Value, Register_Gender_Value, Register_BOY_Value;

	private FirebaseAuth mFirebaseAuth;
	private FirebaseUser mFirebaseUser;

	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference mDatabaseReference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		setContentView(R.layout.b8_register_elf);

		formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));

		mFirebaseAuth = FirebaseAuth.getInstance();
		
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		mDatabaseReference = mFirebaseDatabase.getReference();
		
		Current_Fragment = new Register_Email();
		Fragment_Transaction(Current_Fragment, 0, 0, false, "email");		
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
							
						    // Setup Username to Firebase Auth
							UserProfileChangeRequest mUserProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Register_UserName_Value).build();
							mFirebaseUser.updateProfile(mUserProfileChangeRequest);

							String get_Current_Date = formatter.format(Calendar.getInstance().getTime());
							Log.e("Time", get_Current_Date);

                            // Setup User Profile to Firebase Data
							mDatabaseReference.child("User Information").child(mFirebaseUser.getUid()).child("Profile")
								.setValue(new User_Profile_Model(get_Current_Date, Register_Email_Value, Register_Password_Value, Register_UserName_Value, Register_Gender_Value, Register_BOY_Value, ""))
								.addOnCompleteListener(new OnCompleteListener<Void>(){

									@Override
									public void onComplete(Task<Void> task)
									{
										// TODO: Implement this method
										Log.e("FirebaseDatabase", "New User Profile was create");
									}
								})
								.addOnFailureListener(new OnFailureListener(){

									@Override
									public void onFailure(Exception e)
									{
										// TODO: Implement this method
										Log.e("Database Action", "New User Profile was create fail:" + e.getMessage());
									}
								});
								
								
                            // Send Confirmation Email to Client
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
								
							
                            // 登出
                            mFirebaseAuth.signOut();
                            Log.e("Register", "Signout");
							
							
                            // 跳轉至登入畫面
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

}
