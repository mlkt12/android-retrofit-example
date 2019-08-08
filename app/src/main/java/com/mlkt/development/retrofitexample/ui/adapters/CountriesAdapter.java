package com.mlkt.development.retrofitexample.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlkt.development.retrofitexample.R;
import com.mlkt.development.retrofitexample.api.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {

    private Context context;
    private List<Country> countries = new ArrayList<>();

    public CountriesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.item_recycler, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }

        public void setData(int position) {
            text.setText(String.format(context.getResources().getString(R.string.recycler_item_format),
                    countries.get(position).code,
                    countries.get(position).name));
        }
    }
}
