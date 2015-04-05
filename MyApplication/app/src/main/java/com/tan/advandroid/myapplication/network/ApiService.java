package com.tan.advandroid.myapplication.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import com.tan.advandroid.myapplication.StartActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class ApiService extends IntentService {
    private Network network;

    public ApiService() {
        super("MyIntentService");
    }

    public void onCreate() {
        super.onCreate();
        Log.d("service", "oncreate service");
        network = new Network();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Bundle data = new Bundle();
        ResultReceiver receiver = intent.getParcelableExtra(StartActivity.RECEIVER);
        try {
            JSONObject json = new JSONObject(network.urlConnection("http://mobevo.ext.terrhq.ru/shr/j/ru/technology.js"));
            JsonParser jsonParser = new JsonParser(getBaseContext());
            jsonParser.parser(json);
            data.putInt("finish", 1);
            receiver.send(StartActivity.STATUS_FINISHED, data);
        } catch (JSONException e) {
            e.printStackTrace();
            data.putString("ERROR","Невозможно подключиться к интернету");
            receiver.send(StartActivity.STATUS_ERROR, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}