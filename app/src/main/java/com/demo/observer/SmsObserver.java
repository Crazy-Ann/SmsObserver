package com.demo.observer;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class SmsObserver extends ContentObserver {

    private Context mContext;
    private SmsHandler mSmsHandler;

    public SmsObserver(SmsHandler handler) {
        super(handler);
        this.mSmsHandler = handler;
    }

    public SmsObserver(Activity context, OnSmsResponseListener listener) {
        this(new SmsHandler(listener));
        this.mContext = context;
    }

    public SmsObserver(Activity context, OnSmsResponseListener smsResponseListener, OnSmsFilterListener smsFilterListener) {
        this(new SmsHandler(smsResponseListener, smsFilterListener));
        this.mContext = context;
    }

    public void setSmsFilterListener(OnSmsFilterListener smsFilterListener) {
        mSmsHandler.setSmsFilterListener(smsFilterListener);
    }

    public void registerSmsObserver() {
        Uri uri = Uri.parse("content://sms");
        if (mContext != null) {
            mContext.getContentResolver().registerContentObserver(uri, true, this);
        }
    }

    public void unregisterSmsObserver() {
        if (mContext != null) {
            mContext.getContentResolver().unregisterContentObserver(this);
        }
        if (mSmsHandler != null) {
            mSmsHandler = null;
        }
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Uri inboxUri = Uri.parse("content://sms/inbox");//收件箱
        try {
            Cursor cursor = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    if (mSmsHandler != null) {
                        mSmsHandler.obtainMessage(Constant.SMS_RECEIVED_CODE, new String[]{address, body}).sendToTarget();
                    }
                    Log.i("---->", "sender is " + address + ",sms content is " + body);
                }
                cursor.close();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
