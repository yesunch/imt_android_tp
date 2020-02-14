package com.imt_atlantique.tp1;

import android.text.InputFilter;
import android.text.Spanned;

public class InputNumberCheck  {
    static final int LENGTH_NUMBER = 10;

    public static boolean checkFormat(String str) {
        char[] chars = str.toCharArray();
        if (chars.length == LENGTH_NUMBER)
            return true;
        return false;
    }
}
