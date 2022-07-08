package com.ferdi.qrbarcodescanner;

import static com.ferdi.qrbarcodescanner.ServerAPI.DATA_API;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterTransaksi extends RecyclerView.Adapter<AdapterTransaksi.MyViewHolder> {
    Context context;
    List<Transaksi> transaksiModels;



    public AdapterTransaksi(Context context, List<Transaksi> transaksiModels) {
        this.context = context;
        this.transaksiModels= transaksiModels;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_barang_transaksi, parent,
                false);

        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


        holder.kd_brg2.setText(transaksiModels.get(position).getKode());
        holder.nm_brg2.setText(transaksiModels.get(position).getNama());
        holder.hrg_brg2.setText(transaksiModels.get(position).getHarga());
        holder.jml_brg2.setText(transaksiModels.get(position).getJumlah());
        holder.satuan_brg2.setText(transaksiModels.get(position).getSatuan());

            //menapilkan gbr barcode
        Glide.with(context)
                .load(DATA_API + "qrcode/qr/"+transaksiModels.get(position).getKode()+".png")
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.img_brg2);



    }

    @Override
    public int getItemCount() {
        return transaksiModels.size();
    }

    public void filterList(ArrayList<Transaksi> filteredList) {

        transaksiModels = filteredList;
        notifyDataSetChanged();
    }



    // agar barng bisa di klik
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView kd_brg2,nm_brg2,hrg_brg2, jml_brg2, satuan_brg2;
        ImageView img_brg2;

        public MyViewHolder(View itemView) {
            super(itemView);
            kd_brg2 = itemView.findViewById(R.id.tv_kdbrg2);
            nm_brg2 = itemView.findViewById(R.id.tv_nmbrg2);
            hrg_brg2 = itemView.findViewById(R.id.tv_harga2);
            jml_brg2 = itemView.findViewById(R.id.tv_jumlah2);
            satuan_brg2 = itemView.findViewById(R.id.tv_satuan2);
            img_brg2 = itemView.findViewById(R.id.img_barang2);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent (view.getContext(), PenjualanActivity.class);
            intent.putExtra("kd_brg2", kd_brg2.getText().toString());
            intent.putExtra("nm_brg2", nm_brg2.getText().toString());
            intent.putExtra("hrg_brg2", hrg_brg2.getText().toString());
            intent.putExtra("jml_brg2", jml_brg2.getText().toString());
            intent.putExtra("satuan_brg2", satuan_brg2.getText().toString());
            view.getContext().startActivity(intent);



        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);

    }
}

