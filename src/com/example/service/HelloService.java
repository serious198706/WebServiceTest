package com.example.service;

/**
 * Created by 岩 on 13-10-9.
 */
import android.util.Log;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class HelloService implements ISoapService {
    private static final String NameSpace = "http://192.168.8.33:801";
    private static final String URL = "http://192.168.8.33:801/ReportService.svc";
    private static final String SOAP_ACTION = "http://192.168.8.33:801/IReportsService/ListCheckedvehiclesForJsonData";
    private static final String MethodName = "ListCheckedvehiclesForJsonData";

    private int userid;

    public HelloService(int userid) {
        this.userid = userid;
    }

    public SoapObject LoadResult() {
        SoapObject soapObject = new SoapObject(NameSpace, MethodName);
        soapObject.addProperty("userid", userid);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // 版本
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE trans = new HttpTransportSE(URL);
        trans.debug = true; // 使用调试功能

        try {
            trans.call(SOAP_ACTION, envelope);
            Log.d("WCFTest", "Call Successful!");
        } catch (IOException e) {
            Log.d("WCFTest", "IOException");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.d("WCFTest", "XmlPullParserException");
            e.printStackTrace();
        }

        SoapObject result = (SoapObject) envelope.bodyIn;

        return result;
    }
}
