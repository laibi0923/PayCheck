package com.paycheckeasy.www.paycheck.UserProfile;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.paycheckeasy.www.paycheck.*;

public class UserProfile_UserName_Editor extends AppCompatActivity{
	
	private EditText UserName_Edittext;
	private String UserName;
	private RelativeLayout Comfirm_Button, Cancel_Button;
	
	public void Find_View(){
		UserName_Edittext = (EditText) findViewById(R.id.editor_username_edittext);
		UserName_Edittext.setText(UserName);
		
		Comfirm_Button = (RelativeLayout) findViewById(R.id.editor_confirm_button);
		Comfirm_Button.setOnClickListener(Item_OnclickListener);
		
		Cancel_Button = (RelativeLayout) findViewById(R.id.editor_cancel_button);
		Cancel_Button.setOnClickListener(Item_OnclickListener);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.g2_username_editor);
		Intent mIntent = getIntent();
		UserName = mIntent.getStringExtra("UserName_Editor");
		Find_View();
		Open_keybord(UserName_Edittext);
	}
	
	private View.OnClickListener Item_OnclickListener = new View.OnClickListener(){

		@Override
		public void onClick(View view)
		{   
			// TODO: Implement this method
			switch(view.getId()){
				
				case R.id.editor_confirm_button:
					
					String Current_UserName = UserName_Edittext.getText().toString();
					
					Intent mIntent = new Intent(UserProfile_UserName_Editor.this, UserProfile_Main.class);
					mIntent.putExtra("Current_UserName", Current_UserName);
					setResult(RESULT_OK, mIntent);
					
					finish();
					break;
					
				case R.id.editor_cancel_button:
					finish();
					break;
			}
		}
	};
	
	// Open Soft keybord event
	public void Open_keybord(View mView){
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mView, 0);
	}
}
