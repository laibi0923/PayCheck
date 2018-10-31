package com.paycheckeasy.www.paycheck.Login_and_Register;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import com.paycheckeasy.www.paycheck.R;

public class Register_Fragment extends Fragment{

    private RelativeLayout Start_Register_Button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.b3_register_start, container, false);

        Start_Register_Button = v.findViewById(R.id.start_register_btn);

        Start_Register_Button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                // TODO: Implement this method
                Intent mIntent = new Intent(getActivity(), Register_Elf_Main.class);
                startActivity(mIntent);
            }
        });

        return v;
    }




}
