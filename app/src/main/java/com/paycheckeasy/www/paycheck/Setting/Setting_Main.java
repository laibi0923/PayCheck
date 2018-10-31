package com.paycheckeasy.www.paycheck.Setting;


import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.paycheckeasy.www.paycheck.*;

import com.paycheckeasy.www.paycheck.R;

public class Setting_Main extends Fragment
{
	private LinearLayout Night_Mode, Language, Reset_Password, Update_Log;
	private TextView Night_Mode_Title2, Language_Textview;
	
	//private FirebaseAuth_Helper mFirebase_Helper;
	private FirebaseAuth mFirebaseAuth;
	private FirebaseUser mFirebaseUser;
	
	private SharedPreferences mSharedPreferences;
	private static final String SharedPreferences_Name = "Setting";
	private static final String language = "Language";
	private static final String night_mode = "NightMode";
	
	private int Language_Code = 0;
	private boolean NightMode_Switcher = false;

    public static int DIALOG_REQUEST_CODE;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		//mFirebase_Helper = new FirebaseAuth_Helper(getActivity());
		mFirebaseAuth = FirebaseAuth.getInstance();
		mFirebaseUser = mFirebaseAuth.getCurrentUser();
		
		mSharedPreferences = getContext().getSharedPreferences(SharedPreferences_Name, Context.MODE_PRIVATE);
		
		if (mSharedPreferences != null){
			Language_Code = mSharedPreferences.getInt(language, 0);
			NightMode_Switcher = mSharedPreferences.getBoolean(night_mode, false);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.s1_setting_main, container, false);
		Find_View(v);
		return v;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Update_Current_Setting();
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 接收 Language_AlertDialog 所選取資料
        switch (requestCode){
            case 1:
                int current_Language_code = data.getIntExtra(Language_AlertDialog.RESPONSE, 0);

				mSharedPreferences.edit().putInt(language, Language_Code).commit();
				
                if (current_Language_code == 0){
                    Language_Code = 0;
                }else if (current_Language_code == 1){
                    Language_Code = 1;
                }

                Update_Current_Setting();
				
				// Do Someting to change language...
                break;
        }
    }

    private void Find_View(View v){
		((MainActivity) getContext()).mToolbar_Title.setText("設定");
		
		Night_Mode = v.findViewById(R.id.setting_nightmode);
		Night_Mode.setOnClickListener(Item_OnClickListener);
		
		Night_Mode_Title2 = v.findViewById(R.id.night_mode_TextView2);
		
		Language = v.findViewById(R.id.setting_langage);
		Language.setOnClickListener(Item_OnClickListener);

		Language_Textview = v.findViewById(R.id.setting_langage_TextView2);

		Reset_Password = v.findViewById(R.id.setting_resetpassword);
		Reset_Password.setOnClickListener(Item_OnClickListener);
		
		Update_Log = v.findViewById(R.id.setting_updatelog);
		Update_Log.setOnClickListener(Item_OnClickListener);
	}
	
	private View.OnClickListener Item_OnClickListener = new View.OnClickListener(){

		@Override
		public void onClick(View v)
		{
			// TODO: Implement this method
			switch(v.getId()){
				
				case R.id.setting_nightmode:
					
					mSharedPreferences.edit().putBoolean(night_mode, NightMode_Switcher).commit();
					Update_Current_Setting();
					// Do Something to change Theme...
					break;
					
				case R.id.setting_langage:
				    For_Setting_Language();
					break;
					
				case R.id.setting_resetpassword:
					For_ResetPassword();
					break;

				case R.id.setting_updatelog:
					((MainActivity) getActivity()).Fragment_Transaction(new Update_Log_Main(),0 ,0 , true,"");
					break;
					
			}
			
		}
	};


	private void For_Setting_Language(){

        DIALOG_REQUEST_CODE = 1;
        String FRAGMENT_TAG = "hhhh";

        Language_AlertDialog mLanguage_AlertDialog = new Language_AlertDialog(Language_Code);
        mLanguage_AlertDialog.setTargetFragment(Setting_Main.this, DIALOG_REQUEST_CODE);
        mLanguage_AlertDialog.show(getFragmentManager(), FRAGMENT_TAG);

    }

    private void Update_Current_Setting(){
		
        if (Language_Code == 0){
            Language_Textview.setText("繁體 (預設)");
        }else if (Language_Code == 1){
            Language_Textview.setText("English");
        }
		
		if(NightMode_Switcher == false){

			Night_Mode_Title2.setText("啟動");
			NightMode_Switcher = true;

		}else if(NightMode_Switcher == true){

			Night_Mode_Title2.setText("未啟動");
			NightMode_Switcher = false;
		}
    }

    private void For_ResetPassword(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("我們將會發送一封重設密碼電郵至你所登記的電郵地址");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("請檢查你的電郵");
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
						
						mFirebaseAuth.sendPasswordResetEmail(mFirebaseUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>(){
								@Override
								public void onComplete(Task<Void> task)
								{
									// TODO: Implement this method
									if(task.isSuccessful()){
										Log.e("FirebaseAuth Action", "Sent Reset Password to client email");
									}else{
										Log.e("FirebaseAuth Action", "Reset Password Fail:" + task.getException().getMessage().toString());
									}
								}
							});
						
							((MainActivity) getActivity()).Logout();
                    }
                });
                builder.show();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }


	
}
