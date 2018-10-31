package com.paycheckeasy.www.paycheck.Login_and_Register;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import com.paycheckeasy.www.paycheck.*;

public class Register_UserName extends Fragment
{
	private TextView UserName_Register_Title, UserName_Register_ErrorMsg;
	private EditText UserName_Input_Edittext;
	private RelativeLayout Next_Step_Button;
	private String UserName_Input = null;
	
	private void Find_View(View v){

		UserName_Register_Title = v.findViewById(R.id.register_page_title);
		UserName_Register_Title.setText(getResources().getString(R.string.register_username_erroemsg1));

		UserName_Input_Edittext = v.findViewById(R.id.register_page_edittext);
		UserName_Input_Edittext.requestFocus();

		UserName_Register_ErrorMsg = v.findViewById(R.id.register_page_errormsg);
		UserName_Register_ErrorMsg.setText("");

		Next_Step_Button = v.findViewById(R.id.register_page_submitbtn);
		Next_Step_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v)
				{
					// TODO: Implement this method
					UserName_Input = UserName_Input_Edittext.getText().toString();

					if (UserName_Input.isEmpty()){
						UserName_Register_ErrorMsg.setText(getResources().getString(R.string.register_username_title));
					}else{
						((Register_Elf_Main) getActivity()).Register_UserName_Value = UserName_Input;
						Fragment Current_Fragment = new Register_Gender();
						((Register_Elf_Main) getActivity()). Fragment_Transaction(Current_Fragment, 0, 0, true, "gender");

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
//		((Register_Elf_Main) getActivity()).Open_keybord(UserName_Input_Edittext);
		return v;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		((Register_Elf_Main) getActivity()).Open_keybord(UserName_Input_Edittext);
	}
}
