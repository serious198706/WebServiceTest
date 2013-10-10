package com.example.webservicetest;

import java.io.InputStreamReader;
import java.io.Reader;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;




public class MainActivity extends Activity {
	private String webResponse = "";
	private TextView textView;
	private TextView textView1;
	
	public static final String URL = "http://192.168.8.33:801/Service1.svc";
	public static String temp;
	
	public static Exception exception;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.result);
		textView1 = (TextView) findViewById(R.id.result1);
	 }
	
	public void startWebAccess(){
		try {
			DefaultHttpClient client = new DefaultHttpClient();

	        // http get request
	        HttpGet request = new HttpGet(URL);

	        // set the header to get the data in JSON format
	        request.setHeader("Accept", "application/json");
	        request.setHeader("Content-type", "application/json");
	        
	        //get the response
            HttpResponse response = client.execute(request);
            
	        HttpEntity entity = response.getEntity();
	        
	        //if entity contact length 0, means no employee exist in the system with these code
	        if(entity.getContentLength() != 0) {
	            // stream reader object
	            Reader reader = new InputStreamReader(response.getEntity().getContent());
	            //create a buffer to fill if from reader
	            char[] buffer = new char[(int) response.getEntity().getContentLength()];
	            //fill the buffer by the help of reader
	            reader.read(buffer);
	            //close the reader streams
	            reader.close();
	            
	            temp = new String(buffer);
	            Log.d("TEST", temp);
	            
	            runOnUiThread(new Runnable() {
	                public void run() {

	           //stuff that updates ui
	                	textView.setText(temp);
	               }
	           });
	        }
	        else {
	            
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private static final String SOAP_ACTION = "http://192.168.8.33:801/helloword";
	private static final String METHOD_NAME = "helloword";
	private static final String NAMESPACE = "http://192.168.8.33:801/";
	private static final String URL1 = "http://192.168.8.33:801/Service1.svc";
	
	public void startWebAccess1() {
		try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //request.addProperty("prop1", "myprop");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL1);

            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object result = (Object)envelope.getResponse();

            String[] results = (String[])  result;
            textView1.setText( ""+results[0]);
            
            runOnUiThread(new Runnable() {
                public void run() {

           //stuff that updates ui
                	textView1.setText(temp);
               }
           });
            
        }
        catch (Exception e) {
        	exception = e;
        	runOnUiThread(new Runnable() {
                public void run() {

           //stuff that updates ui
                	textView1.setText(exception.getMessage());
               }
           });
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void connect(View v) {
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		            //Your code goes here
		        	startWebAccess();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});
		
		thread.start();
	}
	
	public void connect1(View v) {
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		            //Your code goes here
		        	startWebAccess1();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});
		
		thread.start();
	}
}
