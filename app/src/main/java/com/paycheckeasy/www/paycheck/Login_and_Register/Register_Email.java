package com.paycheckeasy.www.paycheck.Login_and_Register;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.os.*;
import com.paycheckeasy.www.paycheck.R;
import android.widget.*;
import android.text.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Email extends Fragment
{
	private TextView Email_Register_Title, Email_Register_ErrorMsg;
	private EditText Email_Input_Edittext;
	private RelativeLayout Next_Step_Button;
	private String Email_Result = null;
	
	private void Find_View(View v){
		
		Email_Register_Title = v.findViewById(R.id.register_page_title);
		Email_Register_Title.setText(getResources().getString(R.string.register_email));
		
		Email_Input_Edittext = v.findViewById(R.id.register_page_edittext);
		Email_Input_Edittext.requestFocus();
		Email_Input_Edittext.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
		Email_Register_ErrorMsg = v.findViewById(R.id.register_page_errormsg);
		Email_Register_ErrorMsg.setText("");
		
		Next_Step_Button = v.findViewById(R.id.register_page_submitbtn);
		Next_Step_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO: Implement this method
					Email_Result = Email_Input_Edittext.getText().toString();

					if (Email_Result.isEmpty()){
						Email_Register_ErrorMsg.setText(getResources().getString(R.string.register_email_errormsg1));
					}

					if (isEmailValid(Email_Result)){
						((Register_Elf_Main) getActivity()).Register_Email_Value = Email_Result;
						Fragment Current_Fragment = new Register_Password();
						((Register_Elf_Main) getActivity()). Fragment_Transaction(Current_Fragment, 0, 0, true, "password");
					}else {
						Email_Register_ErrorMsg.setText(getResources().getString(R.string.register_email_errormsg2));
					}
				}
			});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.b4_register_setup_edittext, container, false);
		Find_View(v);
//		((Register_Elf_Main) getActivity()).Open_keybord(Email_Input_Edittext);
		return v;
	}

	// 檢查Email 格式
	public static boolean isEmailValid(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((Register_Elf_Main) getActivity()).Open_keybord(Email_Input_Edittext);
	}
}
