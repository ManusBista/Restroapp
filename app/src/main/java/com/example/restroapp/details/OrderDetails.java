package com.example.restroapp.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restroapp.HomePage.HomeActivity;
import com.example.restroapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.Serializable;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDetails extends AppCompatActivity {

    FloatingActionButton addPhn_btn;
    List<Order> OrderList;
    RecyclerView recyclerView;
    String orderId = "";
    private Toolbar toolbar;
    private TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_text = toolbar.findViewById(R.id.title);
        toolbar_text.setText("Order Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addPhn_btn = findViewById(R.id.addPhn_btn);

        OrderList = new ArrayList<>();
        recyclerView = findViewById(R.id.cus_oder);

        Intent intent = getIntent();
        ArrayList<Order> Order = (ArrayList<Order>) intent.getExtras().getSerializable("data");

        orderId = intent.getStringExtra("id");

        PutDataIntoRecyclerView(Order);

        addPhn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(OrderDetails.this, "name");

            }

        });

    }

    public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);

        EditText name = (EditText) dialog.findViewById(R.id.name_input);
        EditText phno = (EditText) dialog.findViewById(R.id.phone_input);
        Button dialogButton = (Button) dialog.findViewById(R.id.proceed_txt);


        phno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validateMobile(phno.getText().toString())) {
                    dialogButton.setEnabled(true);
                } else {
                    dialogButton.setEnabled(false);
                    phno.setError("invalid mobile number");
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateName(name.getText().toString())) {
                    dialogButton.setEnabled(true);
                } else {
                    dialogButton.setEnabled(false);
                    name.setError("FIELD CANNOT BE EMPTY");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        float density = getResources().getDisplayMetrics().density;
        lp.width = (int) (360 * density);
        lp.height = (int) (340 * density);
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!phno.getText().toString().isEmpty() && !name.getText().toString().isEmpty()) {


                    new InsertCustomerDetailsAync(OrderDetails.this, getReqForInsertCustPhone(name.getText().toString(), phno.getText().toString()), new InsertCustomerDetailsAync.InsertCustomerInterface() {
                        @Override
                        public void Onsuccess(String msg) {
                            Intent intent = new Intent(OrderDetails.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void OnFail(String msg) {
                            Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnRegistered(String msg) {
                            Toast.makeText(OrderDetails.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    }).execute();
                    dialog.dismiss();
                } else {
                    name.setError("Field can't be empty");
                    phno.setError("invalid mobile number");
                }
            }

        });

        ImageView cancel;

        cancel = (ImageView) dialog.findViewById(R.id.cancel_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public JSONObject getReqForInsertCustPhone(String name, String phno) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("mobileNumber", phno);
            jsonObject.put("orderId", orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    public void PutDataIntoRecyclerView(List<Order> orderList) {

        OrderAdapter orderAdapter = new OrderAdapter(this, orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);

    }

    boolean validateMobile(String input) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    boolean validateName(String input) {
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(input);
        return m.matches();

    }


}
