package com.paycheckeasy.www.paycheck.PublicClass;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
 
import java.util.HashMap; 
  
public class TimeStamp_Changer {

    private String name;
    private String owner;
    private HashMap<String, Object> dateCreated;
    private HashMap<String, Object> dateLastChanged;

    public TimeStamp_Changer() {

        HashMap<String, Object> dateLastChangedObj = new HashMap<String, Object>();
        dateLastChangedObj.put("date", ServerValue.TIMESTAMP);
        this.dateLastChanged = dateLastChangedObj;
    }

    public HashMap<String, Object> getDateLastChanged() {
        return dateLastChanged;
    }

    public HashMap<String, Object> getDateCreated() {
        //If there is a dateCreated object already, then return that
        if (dateCreated != null) {
            return dateCreated;
        }
        //Otherwise make a new object set to ServerValue.TIMESTAMP
        HashMap<String, Object> dateCreatedObj = new HashMap<String, Object>();
        dateCreatedObj.put("date", ServerValue.TIMESTAMP);
        return dateCreatedObj;
    }

    // Use the method described in https://.com/questions/25500138/android-chat-crashes-on-datasnapshot-getvalue-for-timestamp/25512747#25512747
// to get the long values from the date object.
    @Exclude
    public long getDateLastChangedLong() {

        Log.e("dalast", dateLastChanged.get("date") + "");
        return (long)dateLastChanged.get("date");
    }

    @Exclude
    public long getDateCreatedLong() {
        return (long)dateCreated.get("date");
    }

}
