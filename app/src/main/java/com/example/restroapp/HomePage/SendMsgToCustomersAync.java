package com.example.restroapp.HomePage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.restroapp.QpayProgressDialog;
import com.example.restroapp.details.HttpsConstant;
import com.example.restroapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendMsgToCustomersAync extends AsyncTask<String,String,String> {
    private Context context;
    private SendMsgToCustomersInterface sendMsgToCustomersInterface;
    private QpayProgressDialog progressDialog;
    private JSONObject jsonObject;

    public SendMsgToCustomersAync(Context context, JSONObject jsonObject, SendMsgToCustomersInterface sendMsgToCustomersInterface) {
        this.context = context;
        this.sendMsgToCustomersInterface = sendMsgToCustomersInterface  ;
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new QpayProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = null;
        try {
            response = HttpsConstant.sendHTTPData(Constants.SEND_MSG_TO_CUSTOMERS, jsonObject);
            Log.d("Gopal", "response : " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            if (status.equals("00") ) {
                sendMsgToCustomersInterface.Onsuccess(message);

            } else {
                sendMsgToCustomersInterface.OnFail(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface SendMsgToCustomersInterface {
        void Onsuccess(String msg);

        void OnFail(String msg);
    }
}

