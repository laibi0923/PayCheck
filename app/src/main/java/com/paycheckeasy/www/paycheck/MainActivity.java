package com.paycheckeasy.www.paycheck;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.paycheckeasy.www.paycheck.AccountManagment.Account_Managment_Main;
import com.paycheckeasy.www.paycheck.Login_and_Register.LoginScreen_Main;
import com.paycheckeasy.www.paycheck.PublicClass.CircleImageView;
import com.paycheckeasy.www.paycheck.PublicClass.UserPhoto_Helper;
import com.paycheckeasy.www.paycheck.Review.Review_Main;
import com.paycheckeasy.www.paycheck.Setting.Setting_Main;
import com.paycheckeasy.www.paycheck.Transitions.Transitions_Main;
import com.paycheckeasy.www.paycheck.UserProfile.UserProfile_Main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.google.firebase.firestore.*;
import java.util.*;

public class MainActivity extends AppCompatActivity
{
	// Toolbar
	private Toolbar mToolbar;
	public TextView mToolbar_Title;
	
	// DrawerLayout
	private DrawerLayout mDrawerLayout;
	
	// Navigation View
	private NavigationView mNavigationView;
	private LinearLayout NavigationView_HeaderLayout;
	public TextView Nav_UserName_TextView, Nav_UserEmail_TextView;
	
	// CircleImageView
	public CircleImageView UserPhoto_CircleImageView;
	
	// Logout Button
	private LinearLayout Logout_btn;
	
	// Fragment
	private Fragment Current_Fragment;
	private String Current_Fragment_Tag;
	
	// Value
	private final static String Toolbar_Title_Text = "Testing";
	public String Uid_Text, DisplayName_Text, UserPhotoUri_Text, Email_Text;

	// FireBase Auth & Storage
	private FirebaseAuth mFirebaseAuth;

	private StorageReference mStorageReference;

	private FirebaseDatabase mFirebaseDatabase;

	private DatabaseReference connectedRef;

	private DatabaseReference Online_Status_For_Database;

	private FirebaseFirestore mFirebaseFirestore;
	
	private DocumentReference Online_Status_For_FireStore;

	
	
	// User Photo Information
	private UserPhoto_Helper mUserPhoto_Helper; // Public Class
	private final static String UserPhoto_Name = "IMG_User_Icon.jpg";
	private File Local_UserPhoto_Image; // Local Patch
	private String Online_Text = "Online";
	private String Offline_Text = "Offline";


	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a1_activity_main);

		Init_Value();

		Find_View();
		
		// 保存當前 Fragment
		Restore_Fragment(savedInstanceState);

		mFirebaseFirestore = FirebaseFirestore.getInstance();
		
		// 設置用戶頭像
		Local_UserPhoto_Image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), UserPhoto_Name);

		mUserPhoto_Helper = new UserPhoto_Helper(MainActivity.this);

		if (UserPhotoUri_Text != null){

			Download_Image(Uid_Text, UserPhoto_Name);

		}else {

			mUserPhoto_Helper.Setup_Default_UserPhoto(UserPhoto_CircleImageView);

		}

	}


	/*
	 *	Done
	 *	".info/connected" 顯示用戶連線狀況, 當連接數據庫時更新用戶為在線, 反之離線
	 */
	@Override
	protected void onStart() {

		// TODO: Implement this method
		super.onStart();

		Online_Status_For_FireStore = mFirebaseFirestore.collection("User Profile").document(Uid_Text);

        Online_Status_For_Database = mFirebaseDatabase.getInstance().getReference().child(Uid_Text).child("status");

		connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");

		connectedRef.addValueEventListener(new ValueEventListener(){

				@Override
				public void onDataChange(DataSnapshot snapshot)
				{
					// TODO: Implement this method

					boolean conneted = snapshot.getValue(Boolean.class);

					if (conneted == true){

						Online_Status_For_Database.setValue(Online_Text);

						Online_Status_For_Database.onDisconnect().setValue(Offline_Text);


					}

				}

				@Override
				public void onCancelled(DatabaseError p1)
				{
					// TODO: Implement this method
				}
			});
	}



	/*
	 *
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Implement this method
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode != RESULT_OK){

			return;
		}

		if(requestCode == 666){

			mUserPhoto_Helper.Setup_UserPhoto(data.getData(), UserPhoto_CircleImageView);

			Nav_UserName_TextView.setText(data.getStringExtra("Result_User_Name"));

		}
	}


	/*
	 *
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);

		Log.e("onSaveInstanceState", "Saveing Fragment to Bundle");

		getSupportFragmentManager().putFragment(outState, "fragment", Current_Fragment);

	}


	private void Init_Value(){

		Intent Value_Intent = getIntent();
		this.Uid_Text = Value_Intent.getStringExtra("Uid");
		this.DisplayName_Text = Value_Intent.getStringExtra("DisplayName");
		this.Email_Text = Value_Intent.getStringExtra("Email");
		this.UserPhotoUri_Text = Value_Intent.getStringExtra("PhotoUri");

	}
	

	/*
	 *
	 */
	private void Find_View(){
		// Toolbar
		mToolbar = (Toolbar) findViewById(R.id.mToolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_HOME_AS_UP);

		mToolbar_Title = (TextView) findViewById(R.id.mToolbarTitle);
		mToolbar_Title.setText(Toolbar_Title_Text);

		// DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
		ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				mToolbar,
				R.string.drawer_open,
				R.string.drawer_close);

		mActionBarDrawerToggle.syncState();

		mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

		// NavigationView
		mNavigationView = (NavigationView) findViewById(R.id.mNavigationView);
		mNavigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		View headerlayout = mNavigationView.getHeaderView(0);

		NavigationView_HeaderLayout = headerlayout.findViewById(R.id.navigationview_header_layout);
		NavigationView_HeaderLayout.setOnClickListener(Item_OnclickListener);

		UserPhoto_CircleImageView = headerlayout.findViewById(R.id.navigationview_userphoto);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_account);
		UserPhoto_CircleImageView.setImageBitmap(bitmap);

		Nav_UserName_TextView = headerlayout.findViewById(R.id.navigationview_username);
		Nav_UserName_TextView.setText(DisplayName_Text);

		Nav_UserEmail_TextView = headerlayout.findViewById(R.id.navigationview_useremail);
		Nav_UserEmail_TextView.setText(Email_Text);

		Logout_btn = (LinearLayout) findViewById(R.id.navigationview_logout);
		Logout_btn.setOnClickListener(Item_OnclickListener);

	}



	/*
	 *	下載用戶頭像
	 */
	public void Download_Image(String Ref,String Child){

		mStorageReference = FirebaseStorage.getInstance().getReference().child(Ref + "/" + Child);

		mStorageReference.getFile(Local_UserPhoto_Image).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>(){

				@Override
				public void onSuccess(FileDownloadTask.TaskSnapshot task)
				{
					// TODO: Implement this method
					Log.e("FileStroage Action", "Download Success");
					mUserPhoto_Helper.Setup_UserPhoto(Uri.fromFile(Local_UserPhoto_Image), UserPhoto_CircleImageView);
				}
			}).addOnFailureListener(new OnFailureListener(){

				@Override
				public void onFailure(Exception p1)
				{
					// TODO: Implement this method
					Log.e("FileStroage Action", p1.getMessage());
				}
			});

	}



	// Restore Fragment
	private void Restore_Fragment(Bundle savedInstanceState){
		/*** Restore Fragment when Activity kill by system ***/
		if (savedInstanceState != null) {
			Current_Fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
		}

		if (Current_Fragment == null){
			Current_Fragment = new Transitions_Main();
		}

		Fragment_Transaction(Current_Fragment, 0, 0, false, "");
		mNavigationView.getMenu().getItem(0).setChecked(true);
	}


	
	// OnClickListener
	private View.OnClickListener Item_OnclickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()){
				
				case R.id.navigationview_header_layout:
					Intent mIntent = new Intent(MainActivity.this ,UserProfile_Main.class);
					// shared element
					ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, (View)UserPhoto_CircleImageView, "user_icon");
					mIntent.putExtra("DisplayName", DisplayName_Text);
					mIntent.putExtra("Email", Email_Text);
					mIntent.putExtra("Uid", Uid_Text);
					//mIntent.putExtra("UserPhoto", UserPhoto);
					mIntent.setData(Uri.fromFile(Local_UserPhoto_Image));
					startActivityForResult(mIntent, 666, option.toBundle());
					break;

				case R.id.navigationview_logout:
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("確認登出?");
					builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) { 
							// 登出
							Logout();
						}
					});
					builder.setNegativeButton("取消", null);
					builder.show();
					break;
			}
		}
	};

	
	
	public void Logout(){
		
		// 刪除個人頭像
		String UserPhoto_Name = "IMG_User_Icon.jpg";
		File Local_UserPhoto_Image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), UserPhoto_Name);
		if(Local_UserPhoto_Image.exists()){
			try{
				Local_UserPhoto_Image.getCanonicalFile().delete();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

		// 登出
		mFirebaseAuth = FirebaseAuth.getInstance();
		mFirebaseAuth.signOut();

		//
		Intent mIntent = new Intent(MainActivity.this, LoginScreen_Main.class);
		startActivity(mIntent);
		finish();
		Log.e("FirebaseAuth Action", "Logout by user");
		
	}
	
	
	
	private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener(){

		@Override
		public boolean onNavigationItemSelected(MenuItem item)
		{
			// TODO: Implement this method
			if (!item.isChecked())
			{
				switch (item.getItemId())
				{
						/*
						 case R.id.menu_editor:
						 Fragment_Transaction(new Editor_Main(), 0, 0, false);
						 break;
						 */
						 
					case R.id.menu_transitions:
						Current_Fragment = new Transitions_Main();
						Current_Fragment_Tag = "";
						break;
						
					case R.id.menu_review:
						Current_Fragment = new Review_Main();
						Current_Fragment_Tag = "";
						break;

					case R.id.menu_signin:
						Current_Fragment = new Account_Managment_Main();
						Current_Fragment_Tag = "Account";
						break;

					case R.id.menu_setting:
						Current_Fragment = new Setting_Main();
						Current_Fragment_Tag = "";
						break;

				}
				item.setChecked(true);
			}
			
			mDrawerLayout.closeDrawers();
			Fragment_Transaction(Current_Fragment,0,0, false, Current_Fragment_Tag);
			
			return true;
		}
	};

	
	
	public void Fragment_Transaction(Fragment fm, int anim_enter, int anim_exit, boolean addBackStack, String Tag)	{
		FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		mFragmentTransaction.setCustomAnimations(anim_enter, anim_exit, anim_exit, anim_enter);
		if (addBackStack){
			mFragmentTransaction.addToBackStack(null);
		}
		mFragmentTransaction.replace(R.id.mFrameLayout, fm, Tag);
		mFragmentTransaction.commit();
	}

}

