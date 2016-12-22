package com.demo.observer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class SmsHandler extends Handler {

    private OnSmsResponseListener mSmsResponseListener;
    private OnSmsFilterListener mSmsFilterListener;

    public SmsHandler(OnSmsResponseListener smsResponseListener) {
        this.mSmsResponseListener = smsResponseListener;
    }

    public SmsHandler(OnSmsResponseListener smsResponseListener, OnSmsFilterListener smsFilterListener) {
        this.mSmsResponseListener = smsResponseListener;
        this.mSmsFilterListener = smsFilterListener;
    }

    public SmsHandler(Callback callback, OnSmsResponseListener smsResponseListener, OnSmsFilterListener smsFilterListener) {
        super(callback);
        this.mSmsResponseListener = smsResponseListener;
        this.mSmsFilterListener = smsFilterListener;
    }

    public SmsHandler(Looper looper, OnSmsResponseListener smsResponseListener, OnSmsFilterListener smsFilterListener) {
        super(looper);
        this.mSmsResponseListener = smsResponseListener;
        this.mSmsFilterListener = smsFilterListener;
    }

    public SmsHandler(Looper looper, Callback callback, OnSmsResponseListener smsResponseListener, OnSmsFilterListener smsFilterListener) {
        super(looper, callback);
        this.mSmsResponseListener = smsResponseListener;
        this.mSmsFilterListener = smsFilterListener;
    }

    public void setSmsFilterListener(OnSmsFilterListener smsFilterListener) {
        this.mSmsFilterListener = smsFilterListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constant.SMS_RECEIVED_CODE:
                String[] infos = (String[]) msg.obj;
                if (infos != null && infos.length == 2 && mSmsResponseListener != null) {
                    if (mSmsFilterListener == null) {
                        mSmsFilterListener = new DefaultSmsFilter();
                    }
                    mSmsResponseListener.onSmsResponse(mSmsFilterListener.onSmsFilter(infos[0], infos[1]));
                }
                break;
            default:
                break;
        }
    }
}
