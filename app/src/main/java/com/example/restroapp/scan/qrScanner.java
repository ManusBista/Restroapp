package com.example.restroapp.scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.restroapp.R;
import com.example.restroapp.details.GetOrderDetailAync;
import com.example.restroapp.details.Order;
import com.example.restroapp.details.OrderDetails;
import com.google.zxing.Result;

import org.json.JSONObject;

import java.util.ArrayList;

public class qrScanner extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 101;
    private boolean permissionEnable = false;
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        try {

            CodeScannerView scannerView = findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(this, scannerView);
//            if (ActivityCompat.checkSelfPermission(qrScanner.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                cameraSource.start(barcodeView.getHolder());
//            }
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        System.out.println(result);
                            if (result.getText() != null) {
                                new GetOrderDetailAync(qrScanner.this, getReqForGetOrderDetail(result.getText()), new GetOrderDetailAync.GetOrderInterface() {
                                    @Override
                                    public void Onsuccess(String msg, ArrayList<Order> Order) {
                                        Intent i = new Intent(getApplicationContext(), OrderDetails.class);
                                        i.putExtra("data", Order);
                                        i.putExtra("id", result.getText());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void OnFail(String msg) {
                                        Toast.makeText(qrScanner.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                }).execute();
                            }
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });

            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequestCamera();
            } else {
                permissionEnable = true;
//                mCodeScanner.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionEnable) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (permissionEnable) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    protected void makeRequestCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            qrScanner.this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
    }

    public JSONObject getReqForGetOrderDetail(String result) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Id", result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}