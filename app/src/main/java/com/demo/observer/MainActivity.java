package com.demo.observer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnSmsResponseListener {

    private SmsObserver mObserver;
    private TextView tvSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSms = (TextView) findViewById(R.id.tvSms);
        mObserver = new SmsObserver(this, this, new VerificationCodeSmsFilter(Constant.SMS_ADDRESS));
        mObserver.registerSmsObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mObserver.unregisterSmsObserver();
    }

    @Override
    public void onSmsResponse(String content) {
        tvSms.setText(content);
    }
}
