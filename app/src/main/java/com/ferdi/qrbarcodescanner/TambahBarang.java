package com.ferdi.qrbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarang extends AppCompatActivity {
    EditText xkd_brg, xnm_brg, xhrg_brg, xjml_brg, xsatuan_brg;
    InterfaceBarang interfaceBarang;
    Button btnsimpan, btlview;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refBarang = database.getReference("Barang");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);




        xkd_brg = findViewById(R.id.xkode);
        xnm_brg = findViewById(R.id.xnmbrg);
        xhrg_brg = findViewById(R.id.xharga);
        xjml_brg = findViewById(R.id.xjumlah);
        xsatuan_brg = findViewById(R.id.xsatuan);
        interfaceBarang = DataApi.getClient().create(InterfaceBarang.class);
    }


    public void simpandata(View view) {
        String kode_brg = xkd_brg.getText().toString();
        String nama_brg = xnm_brg.getText().toString();
        String harga_brg = xhrg_brg.getText().toString();
        String jumlah_brg = xjml_brg.getText().toString();
        String satuan_brg = xsatuan_brg.getText().toString();


        tambahData(kode_brg, nama_brg, harga_brg, jumlah_brg, satuan_brg);

        // retrofit
        Call<Barang> postBarang = interfaceBarang.postBarang(kode_brg,
                nama_brg, harga_brg, jumlah_brg, satuan_brg);
        postBarang.enqueue(new Callback<Barang>() {
            @Override
            public void onResponse(Call<Barang> call, Response<Barang>
                    response) {
                Toast.makeText(TambahBarang.this, "Save data Success",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TambahBarang.this, BarangActivity.class);
                startActivity(intent);


            }
            @Override
            public void onFailure(Call<Barang> call, Throwable t) {
                Toast.makeText(TambahBarang.this, "Save data Success",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TambahBarang.this, BarangActivity.class));
            }
        });
    }

    //method firebase
    private void tambahData(String kode, String nama, String harga, String jumlah, String satuan) {


        refBarang.child(kode).child("kode").setValue(kode);
        refBarang.child(kode).child("nama").setValue(nama);
        refBarang.child(kode).child("harga").setValue(harga);
        refBarang.child(kode).child("jumlah").setValue(jumlah);
        refBarang.child(kode).child("satuan").setValue(satuan);
        Toast.makeText(TambahBarang.this, "Berhasil menambahkan data ke firebase", Toast.LENGTH_LONG).show();
    }


}