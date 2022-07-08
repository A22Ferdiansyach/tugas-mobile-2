package com.ferdi.qrbarcodescanner;

import static com.ferdi.qrbarcodescanner.ServerAPI.DATA_API;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MediaBarcode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView mScannerView;
    String kode = "";
    String nama="";
    String harga = "";
    String jumlah = "";
    String satuan = "";



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
    // method volley

    private void getDataBarang(String result){
        String url=DATA_API + "qrcode/cari_qrcode.php?kode="+result; // Ganti dengan IP address komputer kalian
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
                                jumlah = jsonobject.getString("jumlah");
                                satuan = jsonobject.getString("satuan");


                                Intent intent = new Intent(MediaBarcode.this, PenjualanActivity.class);
                                intent.putExtra("kd_brg2", kode);
                                intent.putExtra("nm_brg2", nama);
                                intent.putExtra("hrg_brg2", harga);
                                intent.putExtra("jml_brg2", jumlah);
                                intent.putExtra("satuan_brg2", satuan);
                                startActivity(intent);


                                Toast.makeText(MediaBarcode.this, "Barang ditemukan: "+nama, Toast.LENGTH_SHORT).show();


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


}