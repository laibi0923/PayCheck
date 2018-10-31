package com.paycheckeasy.www.paycheck.Setting;
import android.support.v4.app.*;
import android.view.*;
import android.os.*;
import com.paycheckeasy.www.paycheck.R;
import android.widget.*;

public class Update_Log_Main extends Fragment
{
	private TextView Log_Content;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		View v = inflater.inflate(R.layout.s3_setting_update_log_main, container, false);
		Log_Content = v.findViewById(R.id.updatelog_content);
		Log_Content.setText(getResources().getString(R.string.update_log_content));
		return v;
	}
	
}
