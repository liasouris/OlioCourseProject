package com.example.courseprojectolio;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MunicipalityListAdapter extends RecyclerView.Adapter<MunicipalityListAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(String municipality);
    }

    private final OnItemClickListener listener;
    private final List<String> data;

    public MunicipalityListAdapter(List<String> initialData,
                                   OnItemClickListener listener) {
        this.data     = new ArrayList<>(initialData);
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<String> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_search_view, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        String muni = data.get(pos);
        holder.nameTv.setText(muni);
        holder.button.setOnClickListener(v -> listener.onItemClick(muni));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(muni));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageButton button;
        TextView nameTv;
        VH(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.recentSearchButton);
            nameTv = itemView.findViewById(R.id.MunicipalityNameText);
        }
    }
}
