package com.example.restroapp.HomePage;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restroapp.R;
import com.example.restroapp.details.InsertCustomerDetailsAync;
import com.example.restroapp.details.Order;
import com.example.restroapp.details.OrderAdapter;
import com.example.restroapp.details.OrderDetails;
import com.example.restroapp.scan.qrScanner;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add_btn, continue_btn;
    List<ActiveOrder> activeOrderList;
    RecyclerView recyclerView;
    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    private SwipeRefreshLayout swipReferesh;

    private static final int CAMERA_REQUEST_PERMISSION = 1;
    private static final int SCANNING_REQUEST_CODE = 2;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_RestroApp);
        setContentView(R.layout.activity_home);

        add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(this);

        activeOrderList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        swipReferesh = findViewById(R.id.swipReferesh);


        new GetActiveOrderDetailsAync(HomeActivity.this, getReqForGetActiveOrderDetails(), new GetActiveOrderDetailsAync.GetActiveOrderInterface() {
            @Override
            public void Onsuccess(String msg, ArrayList<ActiveOrder> Order) {

                PutDataIntoRecyclerView(Order);
            }

            @Override
            public void OnFail(String msg) {
                Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }).execute();


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        swipReferesh.setOnRefreshListener(() -> {
            new GetActiveOrderDetailsAync(HomeActivity.this, getReqForGetActiveOrderDetails(), new GetActiveOrderDetailsAync.GetActiveOrderInterface() {
                @Override
                public void Onsuccess(String msg, ArrayList<ActiveOrder> Order) {
                    PutDataIntoRecyclerView(Order);
                    swipReferesh.setRefreshing(false);
                }

                @Override
                public void OnFail(String msg) {
                    Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }).execute();

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startScanningActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION);
            } else {
                startActivityForResult(new Intent(HomeActivity.this, qrScanner.class), SCANNING_REQUEST_CODE);
            }
        } else {
            startActivityForResult(new Intent(HomeActivity.this, qrScanner.class), SCANNING_REQUEST_CODE);
        }
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == CAMERA_REQUEST_PERMISSION) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startActivityForResult(new Intent(HomeActivity.this, qrScanner.class), SCANNING_REQUEST_CODE);
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                Intent scanner = new Intent(getApplicationContext(), qrScanner.class);
                startActivity(scanner);
                break;
        }
    }

    public JSONObject getReqForGetActiveOrderDetails() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Id", "256");
            jsonObject.put("date", currentDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getReqForSendMsgToCustomers(String name, String numbr,String orderID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("mobileNumber", numbr);
            jsonObject.put("OrderId",orderID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }


    public void PutDataIntoRecyclerView(List<ActiveOrder> activeOrderList) {

        ActiveOrderAdapter activeOrderAdapter = new ActiveOrderAdapter(this, activeOrderList, new ActiveOrderAdapter.ContinueButton() {
            @Override
            public void ContinueButton(String num, String name,String OrderID) {

                new SendMsgToCustomersAync(HomeActivity.this, getReqForSendMsgToCustomers(name, num,OrderID), new SendMsgToCustomersAync.SendMsgToCustomersInterface() {
                    @Override
                    public void Onsuccess(String msg) {
                        SuccessDialog(msg);

                    }

                    @Override
                    public void OnFail(String msg) {
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).execute();

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(activeOrderAdapter);

    }

    public void SuccessDialog(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        View v = inflater.inflate(R.layout.success_dialog, null);
        builder.setView(v);
        builder.setCancelable(false);
        TextView success = (TextView) v.findViewById(R.id.content_text);
        success.setText(msg);
        final Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        v.findViewById(R.id.proceed_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                new GetActiveOrderDetailsAync(HomeActivity.this, getReqForGetActiveOrderDetails(), new GetActiveOrderDetailsAync.GetActiveOrderInterface() {
                    @Override
                    public void Onsuccess(String msg, ArrayList<ActiveOrder> Order) {
                        PutDataIntoRecyclerView(Order);
                        swipReferesh.setRefreshing(false);
                    }

                    @Override
                    public void OnFail(String msg) {
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).execute();
            }

        });
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        float density = getResources().getDisplayMetrics().density;
        lp.width = (int) (300 * density);
        lp.height = (int) (320 * density);
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
    }

}