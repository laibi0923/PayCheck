package com.paycheckeasy.www.paycheck.Login_and_Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paycheckeasy.www.paycheck.R;

public class Register_YearOfBirth extends Fragment {

    private TextView YOB_Register_Title, YOB_Register_ErrorMsg;
    private EditText YOB_Input_Edittext;
    private RelativeLayout Next_Step_Button;
    private String YOB_Result = null;

    private void Find_View(View v){

        YOB_Register_Title = v.findViewById(R.id.register_page_title);
        YOB_Register_Title.setText(getResources().getString(R.string.register_yob_title));

        YOB_Input_Edittext = v.findViewById(R.id.register_page_edittext);
        YOB_Input_Edittext.requestFocus();
        YOB_Input_Edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
        YOB_Input_Edittext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        YOB_Register_ErrorMsg = v.findViewById(R.id.register_page_errormsg);
        YOB_Register_ErrorMsg.setText("");

        Next_Step_Button = v.findViewById(R.id.register_page_submitbtn);
        Next_Step_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                // TODO: Implement this method
                YOB_Result = YOB_Input_Edittext.getText().toString();

                if (YOB_Result.isEmpty()){
                    YOB_Register_ErrorMsg.setText(getResources().getString(R.string.register_yob_erroemsg1));
                    return;
                }

                if (Integer.parseInt(YOB_Result) > 1953 && Integer.parseInt(YOB_Result) < 2018){
                    ((Register_Elf_Main) getActivity()).Register_BOY_Value = YOB_Result;
                    Fragment Current_Fragment = new Register_Finish();
                    ((Register_Elf_Main) getActivity()). Fragment_Transaction(Current_Fragment, 0, 0, true, "finish");
                }else {
                    YOB_Register_ErrorMsg.setText(getResources().getString(R.string.register_yob_erroemsg2));
                }

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.b4_register_setup_edittext, container, false);
        Find_View(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Register_Elf_Main) getActivity()).Open_keybord(YOB_Input_Edittext);
    }
}
