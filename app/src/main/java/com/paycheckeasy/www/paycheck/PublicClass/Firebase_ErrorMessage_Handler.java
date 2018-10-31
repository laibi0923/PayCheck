package com.paycheckeasy.www.paycheck.PublicClass;
 
/*
 * Create Date : 5 Oct 2018
 * Create By : Leo Lai
 * Function : 負責處理將 Firebase Error Code 轉成自定義訊息予使用者
 */
  
public class Firebase_ErrorMessage_Handler {

    private String Error_Code;

    public Firebase_ErrorMessage_Handler(String Error_Code) {
        this.Error_Code = Error_Code;
    }

    public String Get_Error_Message(){

        String Error_Message = "";

        switch(Error_Code){
            // https://stackoverflow.com/questions/37859582/how-to-catch-a-firebase-auth-specific-exceptions
            case "ERROR_INVALID_CUSTOM_TOKEN":
                Error_Message = "";
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Error_Message = "";
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Error_Message = "";
                break;

            case "ERROR_INVALID_EMAIL":
                Error_Message = "你所輸入資料不正確, 請從新輸入";
                break;

            case "ERROR_WRONG_PASSWORD":
                Error_Message = "你所輸入的帳戶或密碼不正確, 請從新輸入。 如果你忘記密碼請輸入正確帳戶, 然後按下忘記密碼以重設你的密碼。";
                break;

            case "ERROR_USER_MISMATCH":
                Error_Message = "";
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Error_Message = "";
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Error_Message = "";
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Error_Message = "";
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Error_Message = "";
                break;

            case "ERROR_USER_DISABLED":
                Error_Message = "";
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Error_Message = "";
                break;

            // 末有登記
            case "ERROR_USER_NOT_FOUND":
                Error_Message = "還沒註冊帳戶嗎? 加入我們吧。";
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Error_Message = "";
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Error_Message = "";
                break;

            case "ERROR_WEAK_PASSWORD":
                Error_Message = "密碼太弱";
                break;
        }

        return Error_Message;
    }

}
