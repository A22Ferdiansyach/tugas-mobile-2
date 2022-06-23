package com.ferdi.qrbarcodescanner;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_QR_SCAN = 101;

    Button scan;
    String nama = "";
    String harga = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        scan = findViewById(R.id.btnScan);
        scan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MediaBarCode.class);
            startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
        });



    }


}