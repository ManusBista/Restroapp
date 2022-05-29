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

public class GetActiveOrderDetailsAync extends AsyncTask<String, String, String> {
    private Context context;
    private GetActiveOrderInterface getActiveOrderInterface;
    private QpayProgressDialog progressDialog;
    private JSONObject jsonObject;

    public GetActiveOrderDetailsAync(Context context, JSONObject jsonObject, GetActiveOrderInterface getActiveOrderInterface) {
        this.context = context;
        this.getActiveOrderInterface = getActiveOrderInterface;
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
            response = HttpsConstant.sendHTTPData(Constants.GET_ACTIVE_ORDER_DETAILS, jsonObject);
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
                JSONArray dataJSONArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataJSONArray.length(); i++) {
                    JSONArray account = dataJSONArray.getJSONArray(i);
                    for (int j = 0; j < account.length(); j++) {
                        JSONObject jsonObject1 = account.getJSONObject(j);

                        ArrayList<ActiveOrder> ActiveOrder = new ArrayList<>();

                        JSONArray jsonObject2 = jsonObject1.getJSONArray("activeOrderDetails");
                        for (int g = 0; g < jsonObject2.length(); g++) {
                            JSONObject object = jsonObject2.getJSONObject(g);
                            ActiveOrder ActiveOrder1 = new ActiveOrder();
                            ActiveOrder1.setId(object.getString("Id"));
                            ActiveOrder1.setOrderId(object.getString("OrderId"));
                            ActiveOrder1.setTotal(object.getString("Total"));
                            ActiveOrder1.setSubTotal(object.getString("subTotal"));
                            ActiveOrder1.setDiscount(object.getString("Discount"));
                            ActiveOrder1.setOrderedBy(object.getString("OrderedBy"));
                            ActiveOrder1.setStatus(object.getString("Status"));
                            ActiveOrder1.setRate(object.getString("Rate"));
                            ActiveOrder1.setOrderBy(object.getString("OrderBy"));
                            ActiveOrder1.setMobileNumber(object.getString("mobileNumber"));
                            ActiveOrder1.setTime(object.getString("Time"));
                            ActiveOrder1.setOrderType(object.getString("OrderType"));
                            ActiveOrder1.setIsComplimentary(object.getBoolean("IsComplimentary"));

                            ActiveOrder.add(ActiveOrder1);
                        }
                        getActiveOrderInterface.Onsuccess(message, ActiveOrder);
                    }

                }

            } else {
                getActiveOrderInterface.OnFail(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface GetActiveOrderInterface {
        void Onsuccess(String msg, ArrayList<ActiveOrder> Order);

        void OnFail(String msg);
    }
}


