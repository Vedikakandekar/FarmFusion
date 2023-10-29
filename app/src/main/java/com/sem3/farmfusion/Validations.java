package com.sem3.farmfusion;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validations {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[789]\\d{9}$");

    private static final Pattern Password = Pattern.compile("^[A-Za-z0-9]{6}$");

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isPasswordValid(String password) {
        return Password.matcher(password).matches();
    }


}
