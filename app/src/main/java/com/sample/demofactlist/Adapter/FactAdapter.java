package com.sample.demofactlist.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.demofactlist.Model.Row;
import com.sample.demofactlist.R;

import java.util.ArrayList;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Row> rowArrayList;

    public FactAdapter(Context context, ArrayList<Row> rows) {
        context = context;
        rowArrayList = rows;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fact_row_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView imageV;

        public MyViewHolder(View view) {
            super(view);
            title  = view.findViewById(R.id.title);
            desc   = view.findViewById(R.id.description);
            imageV = view.findViewById(R.id.imageView);
        }

    }
}
