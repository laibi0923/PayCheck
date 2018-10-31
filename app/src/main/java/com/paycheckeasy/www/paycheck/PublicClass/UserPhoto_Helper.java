package com.paycheckeasy.www.paycheck.PublicClass;
 
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.paycheckeasy.www.paycheck.R;

import java.io.File;
import java.io.FileNotFoundException;

public class UserPhoto_Helper {

    private Activity mActivity;

    public UserPhoto_Helper(Activity mActivity){
        this.mActivity = mActivity;
    }

    public boolean Check_UserPhoto_Exists(File Local_UserPhoto_Image){

        if(Local_UserPhoto_Image.exists()){
            Log.e("patch", "存在");
            return true;
        }else{
            Log.e("patch", "不存在");
            return false;
        }
    }

    public void Setup_UserPhoto(Uri User_Photo_Uri, CircleImageView mCircleImageView){
        try{
            Bitmap bitmap = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(User_Photo_Uri));
            mCircleImageView.setImageBitmap(bitmap);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }


    public void Setup_Default_UserPhoto(CircleImageView mCircleImageView){
        Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_account);
        mCircleImageView.setImageBitmap(bitmap);
    }



}
