package com.paycheckeasy.www.paycheck.AccountManagment;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FieldValue;

import java.lang.reflect.Field;

public class Account_Model { 

    // For Card
	private int position;
//    private String Data_Key;
    private int Color_Code;
    private String Card_Name, First_Num, Last_Num, Holder_Name, Expiry_Date, Amount, Remark;
    private String Type = null;
//    private Object Created_TimeStamp, Last_TimeStamp;


    public Account_Model(){
//        this.Last_TimeStamp = FieldValue.serverTimestamp();
    }

    // For Cash
	public Account_Model(int Color_Code, String Card_Name, String Type, String Amount, String Remark){

//        this.Created_TimeStamp = FieldValue.serverTimestamp();
        this.Color_Code = Color_Code;
		this.Card_Name = Card_Name;
        this.Type = Type;
		this.Amount = Amount;
		this.Remark = Remark;
//        this.Last_TimeStamp = FieldValue.serverTimestamp();

    }

    // For Card
    public Account_Model(int Color_Code, String Card_Name, String First_Num, String Last_Num, String Holder_Name, String Expiry_Date, String Type, String Amount, String Remark){

//        this.Created_TimeStamp = FieldValue.serverTimestamp();
        this.Color_Code = Color_Code;
        this.Card_Name = Card_Name;
        this.First_Num = First_Num;
        this.Last_Num = Last_Num;
        this.Holder_Name = Holder_Name;
        this.Expiry_Date = Expiry_Date;
        this.Type = Type;
		this.Amount = Amount;
        this.Remark = Remark;
//        this.Last_TimeStamp = FieldValue.serverTimestamp();

    }

//    public void setCreated_TimeStamp(long created_TimeStamp) {
//        Created_TimeStamp = created_TimeStamp;
//    }
//
//    public Object getCreated_TimeStamp() {
//        return Created_TimeStamp;
//    }
//
//    @Exclude
//    public long getCreated_TimeStampLong(){
//        return (long) Created_TimeStamp;
//    }
//
//    public void setLast_TimeStamp(long last_TimeStamp) {
//        Last_TimeStamp = last_TimeStamp;
//    }
//
//    public Object getLast_TimeStamp() {
//        return Last_TimeStamp;
//    }
//
//    @Exclude
//    public long getLast_TimeStampLong() {
//        return (long) Last_TimeStamp;
//    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getColor_Code() {
        return Color_Code;
    }

    public void setColor_Code(int color_Code) {
        Color_Code = color_Code;
    }

    public String getCard_Name() {
        return Card_Name;
    }

    public void setCard_Name(String card_Name) {
        Card_Name = card_Name;
    }

    public String getFirst_Num() {
        return First_Num;
    }

    public void setFirst_Num(String first_Num) {
        First_Num = first_Num;
    }

    public String getLast_Num() {
        return Last_Num;
    }

    public void setLast_Num(String last_Num) {
        Last_Num = last_Num;
    }

    public String getHolder_Name() {
        return Holder_Name;
    }

    public void setHolder_Name(String holder_Name) {
        Holder_Name = holder_Name;
    }

    public String getExpiry_Date() {
        return Expiry_Date;
    }

    public void setExpiry_Date(String expiry_Date) {
        Expiry_Date = expiry_Date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
