package com.example.androidChallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL-PC on 15-10-2016.
 */
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private List<Details> detailList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, cost, filmNames;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvfName);
            cost = (TextView) itemView.findViewById(R.id.cost);
            filmNames = (TextView) itemView.findViewById(R.id.films);
        }
    }

    public DetailsAdapter(List<Details> details) {
        this.detailList = details;
    }


    @Override
    public DetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(DetailsAdapter.MyViewHolder holder, int position) {
        Details detailPosition = detailList.get(position);
        holder.name.setText(detailPosition.name);
        holder.cost.setText(detailPosition.cost_in_credits);
        holder.filmNames.setText(detailPosition.filmText + ", etc..");

    }

    @Override
    public int getItemCount() {
        return detailList.size() < 15 ? detailList.size() : 15;
    }

    @Override
    public int getItemViewType(int position) {
        Details item = detailList.get(position);
        return item.type;
    }
}
