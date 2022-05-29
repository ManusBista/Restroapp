package com.example.restroapp.scan;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.restroapp.R;
import com.google.zxing.Result;

public class scanner extends Fragment {

//    private CodeScanner mCodeScanner;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        final Activity activity = getActivity();
//        return inflater.inflate(R.layout.fragment_scanner, container, false);
//        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
//        mCodeScanner = new CodeScanner(activity, scannerView);
//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull final Result result) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCodeScanner.startPreview();
//            }
//        });
//        return root;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mCodeScanner.startPreview();
//    }
//
//    @Override
//    public void onPause() {
//        mCodeScanner.releaseResources();
//        super.onPause();
//    }
}