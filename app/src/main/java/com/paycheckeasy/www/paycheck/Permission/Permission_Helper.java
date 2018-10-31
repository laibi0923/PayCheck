package com.paycheckeasy.www.paycheck.Permission;
  
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.provider.*;
import android.support.v4.app.*;
import android.util.*; 
import java.util.*; 
  
public class Permission_Helper
{
	private List<String> Permission_List = new ArrayList<>();
	private Activity mActivity;
	private boolean checked_DntAskAgain = true;
	private String[] Permissions;
	
	public Permission_Helper(Activity mActivity, String[] Permissions){
		this.mActivity = mActivity;
		this.Permissions = Permissions;
	}
	

    /*
     * Request permission
     */
    public boolean Request_Permission(){

        Permission_List.clear();

        for (int i = 0; i < Permissions.length; i++){

            if (ActivityCompat.checkSelfPermission(mActivity, Permissions[i]) != PackageManager.PERMISSION_GRANTED){
                Permission_List.add(Permissions[i]);
            }else {
                //open_gallery();
                return true;
            }
        }

        Log.e("Perrmission List", Permission_List.size() + "");

        if (!Permission_List.isEmpty()){
            ActivityCompat.requestPermissions(mActivity, Permissions, 1);
        }else {
            Log.e("Permission", "於 request_Permission 中取得授權");
            //open_gallery();
			return true;
        }
		
		return false;
    }
	
	
	
	
	
	/*
     * Tell customs why need this permission
     */
    private void openAppDetails() {
		
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		
        builder.setMessage("玩野咩, 你唔比權限學咩人整頭像丫! 開返佢啦渠頭!");
        builder.setPositiveButton("去啦渠頭", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					mActivity.startActivity(intent);
				}
			});
        builder.setNegativeButton("取消", null);
        builder.show();
    }
	
	
	
	
	
	
	/*
     * Tell customs why need this permission
     */
	public boolean Request_Permissions_Result(int[] grantResults){
		
		for (int i = 0; i < grantResults.length; i ++){
			
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                checked_DntAskAgain = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Permissions[i]);
                Log.e("Checked Dnt ask again", String.valueOf(checked_DntAskAgain));
            }else {
                //open_gallery();
                return true;
            }
        }

        if (!checked_DntAskAgain){
            openAppDetails();
        }
		
		return false;
	}
	
	
	
	
	
}
