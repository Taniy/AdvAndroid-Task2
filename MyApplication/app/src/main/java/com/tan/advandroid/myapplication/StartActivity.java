package com.tan.advandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tan.advandroid.myapplication.network.ApiService;
import com.tan.advandroid.myapplication.network.AppResultsReceiver;
import com.tan.advandroid.myapplication.network.JsonParser;
import com.tan.advandroid.myapplication.network.Network;

import java.io.IOException;


public class StartActivity extends Activity implements AppResultsReceiver.Receiver {
    public final Activity activity = this;
    private AppResultsReceiver mReceiver;

    public final static int STATUS_FINISHED = 1;
    public final static int STATUS_ERROR = 0;
    public final static String RECEIVER_DATA = "RECEIVER_DATA";
    public final static String RECEIVER = "RECEIVER";
    private final String LOG = "LogService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        if (savedInstanceState != null) {
            mReceiver = savedInstanceState.getParcelable(RECEIVER);
        }
        else {
            mReceiver = new AppResultsReceiver(new Handler());
            Intent intent = new Intent(StartActivity.this, ApiService.class);
            intent.putExtra(RECEIVER, mReceiver);
            startService(intent);
        }
        mReceiver.setReceiver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelable(RECEIVER, mReceiver);
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new AppResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);
    }


    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        Log.d(LOG, "onReceiver");
        switch (resultCode) {
            case STATUS_FINISHED:
                Log.d(LOG, "id in receiver");
                Intent loginIntent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(loginIntent);
                StartActivity.this.finish();
                break;
            case STATUS_ERROR :
                String errorString = data.getString("ERROR");
                Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
