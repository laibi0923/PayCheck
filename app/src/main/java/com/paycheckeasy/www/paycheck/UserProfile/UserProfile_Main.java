package com.paycheckeasy.www.paycheck.UserProfile;

import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.annotation.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.storage.*;
import com.paycheckeasy.www.paycheck.*;
import com.paycheckeasy.www.paycheck.Permission.*;
import com.paycheckeasy.www.paycheck.PublicClass.*;
import java.io.*;

import android.Manifest;
import com.paycheckeasy.www.paycheck.R;
import com.google.firebase.firestore.*;

public class UserProfile_Main extends AppCompatActivity {
	
    private final int IMAGE_CODE = 800;
    private final int RESIZE_IMAGE_CODE = 900;

    // View
    private CircleImageView mCircleImageView;

    private RelativeLayout Back_Button, User_Name_RelativeLayout, User_Email_RelativeLayout;

    public TextView User_Name_TextView, User_Email_TextView;

	private Permission_Helper mPermission_Helper;

	// Firebase
	private FirebaseAuth mFirebaseAuth;
	private FirebaseUser mFirebaseUser;
	
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference mDatabaseReference;
	
	private FirebaseFirestore mFirebaseFirestore;
	private DocumentReference mDocumentReference;
	
	private StorageReference mStorageReference;

	public static int DIALOG_REQUEST_CODE = 303;

	// Value
    public String Uid_Text, DisplayName_Text, UserPhotoUri_Text, Email_Text;

    String[] Permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String Local_UserPhoto_Image_Name;
    private File  Local_UserPhoto_Image;

    private Uri Resize_Image_Uri;
    
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO: Implement thdis method
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        if(requestCode == IMAGE_CODE)
            crop_image(data.getData());

		// 剪裁相片結果
        if(requestCode == RESIZE_IMAGE_CODE){ 
            try{
                if (Resize_Image_Uri != null){

					// 1.UserProfile Main 設置頭像
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Resize_Image_Uri));
                    mCircleImageView.setImageBitmap(bitmap);
					
					// 2.上傳相片至伺服器
					upload_image(Resize_Image_Uri);
					
                }
            }catch (FileNotFoundException note){
                note.printStackTrace();
            }
        } 

		// 更改用戶名稱結果
		if(requestCode == DIALOG_REQUEST_CODE){

			// 1.UserProfile Main 設置用戶名稱
			User_Name_TextView.setText(data.getStringExtra("Current_UserName"));
			
			/*
			// 2.將用戶名稱上載至伺服器 (Firedatabase)
			FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
			DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference();
			mDatabaseReference.child("User Information").child(Uid_Text).child("Profile").child("name").setValue(data.getStringExtra("Current_UserName"));
			*/

			Log.e("Setip set name", data.getStringExtra("Current_UserName"));
			// 2. 將用戶名稱上載至伺服器 (Firestoge)
			mDocumentReference.update("User_Name", data.getStringExtra("Current_UserName"));

			// 3.更新 FirebaseAuth 用戶名稱
			UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(data.getStringExtra("Current_UserName")).build();
			mFirebaseUser.updateProfile(profileUpdate);
		}
    }
    

    // 接收權限結果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if(mPermission_Helper.Request_Permissions_Result(grantResults)){
			open_gallery();
		}
    }

	// 攔截返回鍵
	@Override
	public void onBackPressed() {
		// TODO: Implement this method
		super.onBackPressed();
		go_back();
	}
	
	
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.g1_userprofile_main);

        Init_Value();

        Find_View();
        
		// 權限
		mPermission_Helper = new Permission_Helper(this, Permissions);

		// Firebase Auth
		mFirebaseAuth = FirebaseAuth.getInstance();
		mFirebaseUser = mFirebaseAuth.getCurrentUser();

		// Firebase Storage
		mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://paycheck-d2d7b.appspot.com");

		// Firebase Database
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		mDatabaseReference = mFirebaseDatabase.getReference();
		
		// Firebase Firestore
		mFirebaseFirestore = FirebaseFirestore.getInstance();

		// DocumentReference
        mDocumentReference = mFirebaseFirestore.collection("User Profile").document(mFirebaseUser.getUid());

    }


    private void Init_Value(){

	    Intent Value_Intent = getIntent();
        this.Uid_Text = Value_Intent.getStringExtra("Uid");
        this.DisplayName_Text = Value_Intent.getStringExtra("DisplayName");
        this.Email_Text = Value_Intent.getStringExtra("Email");
        this.UserPhotoUri_Text = Value_Intent.getDataString();

    }


    /*
     * Done
     */
	private void Find_View(){

        Back_Button = (RelativeLayout) findViewById(R.id.backpop_Button);
        Back_Button.setOnClickListener(Item_OnClickListener);

        Local_UserPhoto_Image_Name = "IMG_User_Icon.jpg";
        Local_UserPhoto_Image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), Local_UserPhoto_Image_Name);

        mCircleImageView = (CircleImageView) findViewById(R.id.headerlayout_PersonIcon);
        mCircleImageView.setOnClickListener(Item_OnClickListener);

		if(Local_UserPhoto_Image.exists()){
			try{
				Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(Local_UserPhoto_Image)));
				mCircleImageView.setImageBitmap(bitmap);
			}
			catch (FileNotFoundException e){e.printStackTrace();}
		}else{
			try{
				Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_account);
				mCircleImageView.setImageBitmap(bitmap);
			}
			catch (Exception e){e.printStackTrace();}
		}

        User_Email_RelativeLayout = (RelativeLayout) findViewById(R.id.user_name_Layout);
        User_Email_TextView = (TextView) findViewById(R.id.user_email_TextView);
        User_Email_TextView.setText(Email_Text);

        User_Name_RelativeLayout = (RelativeLayout) findViewById(R.id.user_name_Layout);
        User_Name_RelativeLayout.setOnClickListener(Item_OnClickListener);
        User_Name_TextView = (TextView) findViewById(R.id.user_name_TextView);
        User_Name_TextView.setText(DisplayName_Text);
    }
	
	
	
	// 上傳相片 FireStorage
	public void upload_image(Uri image_uri){
		
		UploadTask upload_image_task = mStorageReference.child(Uid_Text).child("IMG_User_Icon.jpg").putFile(image_uri);
		
		upload_image_task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){

				@Override
				public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception
				{
					// TODO: Implement this method
					if(!task.isSuccessful()){

						throw task.getException();

					}

					return mStorageReference.getDownloadUrl();

				}
				
			}).addOnCompleteListener(new OnCompleteListener<Uri>(){

				@Override
				public void onComplete(Task<Uri> task)
				{
					// TODO: Implement this method
					if(task.isSuccessful()){
						
						Uri downloadUri = task.getResult();
						
						// 1. Save to Firebase Auth
						UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setPhotoUri(downloadUri).build();
						mFirebaseUser.updateProfile(profileUpdate);
						
						// 2. 將用戶名稱上載至伺服器 (Firestoge)
						mDocumentReference.update("User_Photo_Uri", downloadUri.toString());
						
					}else{
						
						Log.e("Upload Fail", task.getException().getMessage() + "");
						
					}
					
				}
				
			});
		
		}
	
	
	
	public void set_username_text(String text){
		User_Name_TextView.setText(text);
		Intent Result_UserName_Intent = new Intent();
		Result_UserName_Intent.putExtra("User_Name_Result", text);
		mDatabaseReference.child(Uid_Text).child("Profile").child("user_Name").setValue(text);
		setResult(RESULT_OK, Result_UserName_Intent);
	}

	
	
	
	
	// 點擊事件
    private View.OnClickListener Item_OnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){

				// 返回鍵
                case R.id.backpop_Button:
					go_back();
					finish();
                    break;

				// 頭像
                case R.id.headerlayout_PersonIcon:
					if(mPermission_Helper.Request_Permission()){
						open_gallery();
					}
                    break;
					
				// 用戶名稱
                case R.id.user_name_Layout:
                    Intent mIntent = new Intent(UserProfile_Main.this, UserProfile_UserName_Editor.class);
					mIntent.putExtra("UserName_Editor", User_Name_TextView.getText().toString());
					startActivityForResult(mIntent, DIALOG_REQUEST_CODE);
                    break;
            }
        }
    };


	private void go_back(){
		Intent Result_Intent = new Intent();
		Result_Intent.setData(Uri.fromFile(Local_UserPhoto_Image));
		Result_Intent.putExtra("Result_User_Name", User_Name_TextView.getText().toString());
		setResult(RESULT_OK, Result_Intent);
	}
	
	
	
    // 打開相冊
    private void open_gallery(){
        Log.e("Index", "已進入相簿");
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_CODE);
    }
	
	
	
	

    // 剪裁相片
    private void crop_image(Uri scoure_img){
        Log.e("Index", "已進入剪裁");
        File imageFile = createImageFile();
        Intent intent = new Intent("com.android.camera.action.CROP");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

            /*
             *	Android N 後版本需提供臨時授權以讀取檔案
             *	輸出 Uri 不需使用 ContentUri 故用回 Uri.fromFile(imageFile) 即可
             *	https://www.jianshu.com/p/2275bb552327
             */

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);		// 賦予一次性臨時授權
            //scoure_Image_Uri = FileProvider.getUriForFile(this, getApplication().getPackageName() + ".provider", imageFile);
            Resize_Image_Uri = Uri.fromFile(imageFile);
            Log.e("resize uri x: ", Resize_Image_Uri + "");

        }else{

            Resize_Image_Uri = Uri.fromFile(imageFile);
            Log.e("resize uri : y", Resize_Image_Uri + "");

        }

        intent.setDataAndType(scoure_img, "image/*"); // 張原圖放入, 原圖 URI 為 ContentUri
        intent.putExtra("crop", "true");	// 允許剪裁
        intent.putExtra("aspectX", 1);		// 設置寬比例
        intent.putExtra("aspectY", 1);		// 設置高比例
        intent.putExtra("outputX", 500);	// 設置剪裁後寬
        intent.putExtra("outputY", 500);	// 設置剪裁後高
        intent.putExtra("scale", true);		// 允許縮放
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Resize_Image_Uri); // 儲存剪裁圖片至路徑
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false); // 允許前置鏡頭
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());		//輸出格式

        startActivityForResult(intent, RESIZE_IMAGE_CODE);
    }

	
	
	
	
    // 建立剪裁後的相片
    private File createImageFile() {

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        //String imageFileName = "IMG" + timeStamp + ".jpg";

        String imageFileName = "IMG_User_Icon.jpg";		// 由於頭像只需一張故此用指定名稱

        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);

        image.getAbsolutePath();

        return image;
    }
	
	
	
	
}
