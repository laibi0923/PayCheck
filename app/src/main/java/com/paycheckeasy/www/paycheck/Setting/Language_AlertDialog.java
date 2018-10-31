package com.paycheckeasy.www.paycheck.Setting;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.paycheckeasy.www.paycheck.R;
 
@SuppressLint("ValidFragment")
public class Language_AlertDialog extends DialogFragment
{
	private RadioGroup Laugage_Group;
	private RadioButton CHT_RadioButton, ENG_RadioButton;
	private RelativeLayout Submit_button, Cancel_button;
	private int Laugage_Code;

    public static final String RESPONSE = "response";

    public Language_AlertDialog(int Laugage_Code){
        this.Laugage_Code = Laugage_Code;
    }

    @Override
    public void onStart() {
        // 設置 DialogFragment Match Parent 方法
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        }
    }

    @Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.s2_setting_language_dialog, container, false);
		Find_View(v);
		return v;
	}

    private void Find_View(View v){

		Laugage_Group = v.findViewById(R.id.laugage_radio_group);
		Laugage_Group.setOnCheckedChangeListener(mOnCheckedChangeListener);

		CHT_RadioButton = v.findViewById(R.id.laugage_cht_radio);
		ENG_RadioButton = v.findViewById(R.id.laugage_eng_radio);

		Submit_button = v.findViewById(R.id.submit_button);
		Submit_button.setOnClickListener(Item_OnClickListener);
		Cancel_button = v.findViewById(R.id.cancel_button);
		Cancel_button.setOnClickListener(Item_OnClickListener);

		if (Laugage_Code == 0){
			CHT_RadioButton.setChecked(true);
		}else if (Laugage_Code == 1){
			ENG_RadioButton.setChecked(true);
		}

	}

	private View.OnClickListener Item_OnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

			switch (view.getId()){

				case R.id.submit_button:
				    
					// 將用戶選擇傳到 Setting Main Fragment
                    Intent mIntent = new Intent();
                    mIntent.putExtra(RESPONSE, Laugage_Code);
                    getTargetFragment().onActivityResult(Setting_Main.DIALOG_REQUEST_CODE, Activity.RESULT_OK, mIntent);

					dismiss();
					break;

				case R.id.cancel_button:
					dismiss();
					break;
			}
		}
	};

	private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup radioGroup, int i) {
			switch(i){

				case R.id.laugage_cht_radio:
					Laugage_Code = 0;
					break;

				case R.id.laugage_eng_radio:
					Laugage_Code = 1;
					break;
			}
		}
	};

}
