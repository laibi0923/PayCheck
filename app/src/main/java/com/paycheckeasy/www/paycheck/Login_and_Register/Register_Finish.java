package com.paycheckeasy.www.paycheck.Login_and_Register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.paycheckeasy.www.paycheck.R;

public class Register_Finish extends Fragment {
    private RelativeLayout Register_Finish_Btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.b9_register_finish, container, false);

        Register_Finish_Btn = v.findViewById(R.id.register_finish_btn);
        Register_Finish_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Register_Elf_Main) getActivity()).Register_New_User();
            }
        });

        return v;
    }
}
