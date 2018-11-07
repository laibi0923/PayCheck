package com.paycheckeasy.www.paycheck.AccountManagment;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class Account_Model { 

    // For Card
    private int Color_Code;
    private String Card_Name, First_Num, Last_Num, Holder_Name, Expiry_Date, Amount, Remark;
    private String Type = null;

    @ServerTimestamp
    private Date create_Date, last_Modify_Date;

    public Account_Model(){

    }

    // For Cash
	public Account_Model(int Color_Code, String Card_Name, String Type, String Amount, String Remark){
        this.Color_Code = Color_Code;
		this.Card_Name = Card_Name;
        this.Type = Type;
		this.Amount = Amount;
		this.Remark = Remark;
    }

    // For Card
    public Account_Model(int Color_Code, String Card_Name, String First_Num, String Last_Num, String Holder_Name, String Expiry_Date, String Type, String Amount, String Remark){
        this.Color_Code = Color_Code;
        this.Card_Name = Card_Name;
        this.First_Num = First_Num;
        this.Last_Num = Last_Num;
        this.Holder_Name = Holder_Name;
        this.Expiry_Date = Expiry_Date;
        this.Type = Type;
		this.Amount = Amount;
        this.Remark = Remark;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getCreate_Date() {
        return create_Date;
    }

	/*
    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }
	*/

    public Date getLast_Modify_Date() {
        return last_Modify_Date;
    }

    public void setLast_Modify_Date(Date last_Modify_Date) {
        this.last_Modify_Date = last_Modify_Date;
    }
}
