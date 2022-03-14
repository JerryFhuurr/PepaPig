package com.and.pepapig;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<CalculateResult> results;

    public ListAdapter(ArrayList<CalculateResult> results){
        this.results = results;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater  inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.name.setText(results.get(position).getExpression());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    //Create a ViewHolder inner class to contain the Views that will be send to the RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        ViewHolder(View v){
            super (v);
            name = v.findViewById(R.id.item_name);
            image = v.findViewById(R.id.item_image);
        }
    }
}
