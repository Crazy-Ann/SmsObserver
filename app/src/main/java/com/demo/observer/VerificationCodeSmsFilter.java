package com.demo.observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VerificationCodeSmsFilter implements OnSmsFilterListener {

    private String mAddress;

    public VerificationCodeSmsFilter(String address) {
        this.mAddress = address;
    }

    @Override
    public String onSmsFilter(String address, String content) {
        if (address.startsWith(mAddress)) {
            Pattern pattern = Pattern.compile("(\\d{6})");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return null;
    }
}
