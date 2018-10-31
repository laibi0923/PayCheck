package com.paycheckeasy.www.paycheck.Login_and_Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paycheckeasy.www.paycheck.R;

public class Register_Gender extends Fragment {

    private TextView Gender_Register_Title, Gender_Register_ErrorMsg;
    private RadioGroup Gender_RadioGroup;
    private RadioButton Gender_Register_Male, Gender_Register_Fmale;
    private String Gender_Result;
    private RelativeLayout Next_Step_Button;

    private void Find_View(View v){

        Gender_Register_Title = v.findViewById(R.id.register_page_title);
        Gender_Register_Title.setText(getResources().getString(R.string.register_gender_title));

        Gender_Register_Male = v.findViewById(R.id.gender_male);
        Gender_Register_Male.setOnCheckedChangeListener(mOnCheckedChangeListener);

        Gender_Register_Fmale = v.findViewById(R.id.gender_female);
        Gender_Register_Fmale.setOnCheckedChangeListener(mOnCheckedChangeListener);

        Gender_Register_ErrorMsg = v.findViewById(R.id.register_page_errormsg);
        Gender_Register_ErrorMsg.setText("");

        Next_Step_Button = v.findViewById(R.id.register_page_submitbtn);
        Next_Step_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                // TODO: Implement this method

                if(Gender_Result == null){
                    Gender_Register_ErrorMsg.setText(getResources().getString(R.string.register_gender_erroemsg1));
                }else {
                    ((Register_Elf_Main) getActivity()).Register_Gender_Value = Gender_Result;
                    Fragment Current_Fragment = new Register_YearOfBirth();
                    ((Register_Elf_Main) getActivity()). Fragment_Transaction(Current_Fragment, 0, 0, true, "YOB");
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.b5_register_gender, container, false);
        Find_View(v);
        return v;
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            Gender_Result = null;

            switch (compoundButton.getId()){

                case R.id.gender_male:
                    Gender_Result = "Male";
                    break;

                case R.id.gender_female:
                    Gender_Result = "Female";
                    break;
            }
        }
    };
}
