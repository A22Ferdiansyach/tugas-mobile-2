package com.ferdi.qrbarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyViewHolder> {
    Context context;
    List<Barang> barangModels;



    public BarangAdapter(Context context, List<Barang> barangModels) {
         this.context = context;
         this.barangModels= barangModels;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_barang, parent,
                        false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {


// Get position of MyViewHolder class
        holder.kd_brg.setText(barangModels.get(position).getKode());
        holder.nm_brg.setText(barangModels.get(position).getNama());
        holder.hrg_brg.setText(barangModels.get(position).getHarga());

    }
    @Override
    public int getItemCount() {
        return barangModels.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView kd_brg,nm_brg,hrg_brg;
        public MyViewHolder(View itemView) {
            super(itemView);
            kd_brg = itemView.findViewById(R.id.tv_kdbrg);
            nm_brg = itemView.findViewById(R.id.tv_nmbrg);
            hrg_brg = itemView.findViewById(R.id.tv_hrgbrg);
        }
    }
}
