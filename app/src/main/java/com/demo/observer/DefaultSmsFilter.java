package com.demo.observer;

public class DefaultSmsFilter implements OnSmsFilterListener {

    @Override
    public String onSmsFilter(String address, String content) {
        return content;
    }
}
