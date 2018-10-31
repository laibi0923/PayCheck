package com.paycheckeasy.www.paycheck.Login_and_Register;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import com.paycheckeasy.www.paycheck.*;

public class Register_Password extends Fragment
{
	private TextView Password_Register_Title, Password_Register_ErrorMsg;
	private EditText Password_Input_Edittext;
	private RelativeLayout Next_Step_Button;
	private String Password_Result = null;
	
	private void Find_View(View v){

		Password_Register_Title = v.findViewById(R.id.register_page_title);
		Password_Register_Title.setText(getResources().getString(R.string.register_password_title));

		Password_Input_Edittext = v.findViewById(R.id.register_page_edittext);
		Password_Input_Edittext.requestFocus();

		Password_Register_ErrorMsg = v.findViewById(R.id.register_page_errormsg);
		Password_Register_ErrorMsg.setText("");

		Next_Step_Button = v.findViewById(R.id.register_page_submitbtn);
		Next_Step_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO: Implement this method
					Password_Result = Password_Input_Edittext.getText().toString();

					if (Password_Result.isEmpty()){
						Password_Register_ErrorMsg.setText(getResources().getString(R.string.register_password_erroemsg1));
					}else {
						((Register_Elf_Main) getActivity()).Register_Password_Value = Password_Result;
						Fragment Current_Fragment = new Register_UserName();
						((Register_Elf_Main) getActivity()). Fragment_Transaction(Current_Fragment, 0, 0, true, "username");
					}
					
				}
			});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.b4_register_setup_edittext, container, false);
		Find_View(v);
		return v;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((Register_Elf_Main) getActivity()).Open_keybord(Password_Input_Edittext);
	}

}
