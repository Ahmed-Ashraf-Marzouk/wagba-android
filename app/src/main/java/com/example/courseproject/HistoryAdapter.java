package com.example.courseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<HistoryModel> HistoryList;

    public HistoryAdapter(ArrayList<HistoryModel> HistoryList){
        this.HistoryList = HistoryList;
    }
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.history_item, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        final HistoryModel myListData = HistoryList.get(position);
        holder.stateView.setText(myListData.getState());
        holder.descriptionView.setText(myListData.getDescription());
        holder.priceView.setText(myListData.getPrice() + "$");
        holder.dateView.setText(myListData.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getState(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return HistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stateView;
        public TextView descriptionView;
        public TextView priceView;
        public TextView dateView;
        public CardView cardView;
        //        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.stateView = (TextView) itemView.findViewById((R.id.order_state));
            this.descriptionView = (TextView) itemView.findViewById((R.id.order_description));
            this.priceView = (TextView) itemView.findViewById((R.id.order_price));
            this.dateView = (TextView) itemView.findViewById((R.id.order_date));
            cardView = (CardView) itemView.findViewById(R.id.order_element);
        }
    }
}
