package com.ferdi.qrbarcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MediaBarCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView mScannerView;
    String kode = "";
    String nama="";
    String harga = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Barang");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        cameraPermission(); //meminta permission untuk menggunakan camera

    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void handleResult(Result result) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        getDataBarang(result.getText());



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    // Method untuk memanggil data barang

    private void getDataBarang(String result){
        String url="http://192.168.100.14/qrcode/cari_qrcode.php?kode="+result;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.PUT,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for(int i = 0; i < jsonArray.length(); i++) {


                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                kode = jsonobject.getString("kode");
                                nama = jsonobject.getString("nama_barang");
                                harga = jsonobject.getString("harga");



                                AlertDialog alertDialog = new AlertDialog.Builder(MediaBarCode.this).create();
                                alertDialog.setTitle("Hasil Scanning");
                                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                                alertDialog.setMessage("Kode Barcode : " + kode + "\nNama Barang : " + nama + "\nHarga Barang : " + decimalFormat.format(Double.parseDouble(harga)));

                                tambahData(kode, nama, harga);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                                finish();
                                            }
                                        });
                                alertDialog.show();
                                Toast.makeText(MediaBarCode.this, "Barang ditemukan: "+nama, Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void tambahData(String kode, String nama, String harga ) {
        String key = myRef.push().getKey();

        myRef.child(key).child("kode").setValue(kode);
        myRef.child(key).child("nama").setValue(nama);
        myRef.child(key).child("harga").setValue(harga);
        Toast.makeText(MediaBarCode.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }


}