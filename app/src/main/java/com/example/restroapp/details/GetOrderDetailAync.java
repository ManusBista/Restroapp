package com.example.restroapp.details;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.restroapp.QpayProgressDialog;
import com.example.restroapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetOrderDetailAync extends AsyncTask<String, String, String> {
    private Context context;
    private JSONObject jsonObject;
    private GetOrderInterface getOrderInterface;
    private QpayProgressDialog progressDialog;

    public GetOrderDetailAync(Context context, JSONObject jsonObject, GetOrderInterface getOrderInterface) {
        this.context = context;
        this.jsonObject = jsonObject;
        this.getOrderInterface = getOrderInterface;
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
            response = HttpsConstant.sendHTTPData(Constants.GET_ORDER_DETAILS_BY_ID_FOR_TAB, jsonObject);
            Log.d("Gopal", "response : " + response + "api" + Constants.GET_ORDER_DETAILS_BY_ID_FOR_TAB+"req"+jsonObject);
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
                ArrayList<Order> Order = new ArrayList<>();
                for (int i = 0; i < dataJSONArray.length(); i++) {
                    JSONArray account = dataJSONArray.getJSONArray(i);
                    for (int j = 0; j < account.length(); j++) {
                        JSONObject jsonObject1 = account.getJSONObject(j);
                        Order order1 = new Order();
                        order1.setItemId(jsonObject1.getString("ItemId"));
                        order1.setItemName(jsonObject1.getString("ItemName"));
                        order1.setQuantity(jsonObject1.getString("Quantity"));
                        order1.setTotal(jsonObject1.getString("Total"));
                        order1.setOrderDetailsId(jsonObject1.getString("OrderDetailsId"));
                        order1.setStatus(jsonObject1.getString("Status"));
                        order1.setRate(jsonObject1.getString("Rate"));
                        order1.setImageUrl(jsonObject1.getString("ImageUrl"));
                        order1.setNote(jsonObject1.getString("Note"));
                        order1.setTime(jsonObject1.getString("Time"));
                        order1.setType(jsonObject1.getString("Type"));
                        order1.setIsComplimentary(jsonObject1.getBoolean("IsComplimentary"));

                        Order.add(order1);
                    }
                }
                getOrderInterface.Onsuccess(message, Order);
            } else {
                getOrderInterface.OnFail("Qr Code is not recognized!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface GetOrderInterface {
        void Onsuccess(String msg, ArrayList<Order> Order);

        void OnFail(String msg);
    }
}


