package com.paycheckeasy.www.paycheck.Login_and_Register;

public class User_Profile_Model {

    private String Create_Date, Email, Password, Name, Gender, BOY, Photo_Uri;

    public User_Profile_Model(String create_Date, String email, String password, String name, String gender, String BOY, String photo_Uri) {
        Create_Date = create_Date;
        Email = email;
        Password = password;
        Name = name;
        Gender = gender; 
        this.BOY = BOY;
        Photo_Uri = photo_Uri;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBOY() {
        return BOY;
    }

    public void setBOY(String BOY) {
        this.BOY = BOY;
    }

    public String getPhoto_Uri() {
        return Photo_Uri;
    }

    public void setPhoto_Uri(String photo_Uri) {
        Photo_Uri = photo_Uri;
    }
}
