package com.example.webservicetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.service.HelloService;

import org.ksoap2.serialization.SoapObject;

/**
 * Created by 岩 on 13-10-9.
 */
public class TestActivity extends Activity {
    public TextView textView;
    public String resultString;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = (TextView) findViewById(R.id.result);
    }

    public void connect(View v) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Your code goes here
                    getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void getResult() {
        HelloService service = new HelloService(1);

        SoapObject result = service.LoadResult();
        resultString = result.getProperty(0).toString();

        runOnUiThread(new Runnable() {
            public void run() {

                //stuff that updates ui
                textView.setText(resultString);
            }
        });
    }
}