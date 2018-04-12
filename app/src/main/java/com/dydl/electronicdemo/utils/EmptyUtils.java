package com.dydl.electronicdemo.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lwx on 2018/3/16.
 */

public class EmptyUtils {

    public static boolean checkEditTextEmtity(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }
    public static String getEditTextEmtity(EditText editText){
        if(!TextUtils.isEmpty(editText.getText().toString().trim())){
            return editText.getText().toString().trim();
        }else {
            return "";
        }

    }

    public static String getTextViewEmtity(TextView textView){
        if(!TextUtils.isEmpty(textView.getText().toString().trim())){
            return textView.getText().toString().trim();
        }else {
            return "";
        }

    }
    public static boolean checkTextViewEmtity(TextView editText){
        if(TextUtils.isEmpty(editText.getText().toString().trim())){
            return true;
        }else{
            return false;
        }
    }

}
