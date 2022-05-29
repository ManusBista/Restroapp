package com.example.restroapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

public class QpayProgressDialog extends Dialog {
    private Context context;
    private Dialog dialog;

    public QpayProgressDialog(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public void init(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.progresslayout, null);
        builder.setView(v);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void show(){
        try {

            if(dialog != null) {
                dialog.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dismiss(){
        if(dialog != null) {
            dialog.dismiss();
        }
    }


}
