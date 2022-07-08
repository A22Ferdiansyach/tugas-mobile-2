package com.ferdi.qrbarcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private BarangAdapter barangAdapter;
    private List<Barang> barangModelList;
    private InterfaceBarang interfaceBarang;
    Button btn_add;

    androidx.recyclerview.widget.RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_barang);

        recyclerView    =   findViewById(R.id.rcylrBarang);
        btn_add =   findViewById(R.id.tambah_barang);
    btn_add.setOnClickListener(view -> {
        startActivity(new Intent(BarangActivity.this, TambahBarang.class));
    });
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        interfaceBarang= DataApi.getClient().create (InterfaceBarang.class);

        tampilkanData();


    }

    private void tampilkanData() {
        Call<List<Barang>> call = interfaceBarang.getBarang();

        call.enqueue(new Callback<List<Barang>>() {

            @Override
            public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {

                barangModelList = response.body();
                barangAdapter= new BarangAdapter(BarangActivity.this, barangModelList);
                recyclerView.setAdapter(barangAdapter);
            }


            @Override
            public void onFailure(Call<List<Barang>> call, Throwable t) {

                // Menampilkan toast saat no connection

                Toast.makeText(BarangActivity.this, "No connection, please try again", Toast.LENGTH_LONG).show();


//                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }





    public void scanner(View view) {
        startActivity(new Intent(BarangActivity.this, TambahBarang.class));
    }


}

