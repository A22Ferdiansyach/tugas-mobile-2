package com.ferdi.qrbarcodescanner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceTransaksi {
    // get untuk menganbil data
    @GET("qrcode/")
    Call<List<Transaksi>> getBarang2();
    @FormUrlEncoded
    @POST("qrcode/transaksi.php")
    Call<Transaksi> simpanPenjualan(@Field("kode") String kode,
                                    @Field("jumlah") String jumlah);

    @DELETE("qrcode/")
    Call<Transaksi> deleteBarang(@Query("kode") String kode);
}
