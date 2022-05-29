package com.example.restroapp.details;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.restroapp.QpayProgressDialog;
import com.example.restroapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class InsertCustomerDetailsAync extends AsyncTask<String, String, String> {

    private Context context;
    private JSONObject jsonObject;
    private InsertCustomerInterface insertCustomerInterface;
    private QpayProgressDialog progressDialog;


    public InsertCustomerDetailsAync(Context context, JSONObject jsonObject, InsertCustomerInterface insertCustomerInterface) {

        this.context = context;
        this.jsonObject = jsonObject;
        this.insertCustomerInterface = insertCustomerInterface;

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
            JSONObject jsObject = jsonObject;
            response = HttpsConstant.sendHTTPData(Constants.INSERT_CUSTOMER_DETAILS, jsonObject);
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
            if (status.equals("00")) {
                insertCustomerInterface.Onsuccess(message);
            }
            else if (status.equals("98")){
                insertCustomerInterface.OnRegistered(message);
            }
            else {
                insertCustomerInterface.OnFail(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface InsertCustomerInterface {
        void Onsuccess(String msg);

        void OnFail(String msg);

        void OnRegistered(String msg);
    }
}
