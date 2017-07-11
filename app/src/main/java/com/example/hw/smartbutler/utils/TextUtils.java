package com.example.hw.smartbutler.utils;

import android.support.annotation.Nullable;

/**
 * Created by HW on 11/07/2017.
 */

public class TextUtils {

    public static boolean isEmpty(@Nullable CharSequence str){

        if (str == null || str.length() == 0){
            return true;
        } else {
            return false;
        }
    }
}
