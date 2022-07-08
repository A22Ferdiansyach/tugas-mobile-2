package com.ferdi.qrbarcodescanner;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaksi implements Serializable {
    @SerializedName("kode")
    String kode;
    @SerializedName("nama_barang")
    String nama;
    @SerializedName("harga")
    String harga;
    @SerializedName("satuan")
    String satuan;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("image")
    String image;



    public Transaksi(String kode, String nama, String harga, String image, String satuan, String jumlah) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.satuan = satuan;
        this.jumlah = jumlah;

        this.image = image;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getImage() {
        return ServerAPI.URL +"/assets/images/"+ image;
    }
    public String getImgName() { return image;  }

    public void setImage(String image) {
        this.image = image;
    }


}