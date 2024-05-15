package com.example.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapp.Activities.DetailActivity;
import com.example.myapp.Domains.PopularDomain;
import com.example.myapp.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<PopularDomain> items;

    Context context;


    public PopularAdapter(ArrayList<PopularDomain> items, Context context) {
        this.items = items;
        this.context = context;

    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        PopularDomain popularDomain = items.get(position);
        holder.titleTxt.setText(popularDomain.getTitle());
        holder.locationTxt.setText(popularDomain.getLocation());
        String score = String.valueOf(popularDomain.getScore());
        holder.scoreTxt.setText(score);

        /*int drawableResId=holder.itemView.getResources().getIdentifier(items.get(position).getPic(),
                "drawable",holder.itemView.getContext().getPackageName());*/

        Glide.with(holder.itemView.getContext())
                .load(popularDomain.getPic())
                .transform(new CenterCrop(),new GranularRoundedCorners(40,40,40,40))
                .placeholder(R.drawable.intro)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), DetailActivity.class);
            //intent.putExtra("object",items.get(position));
            intent.putExtra("id",items.get(position).getId());
            intent.putExtra("title",items.get(position).getTitle());
            intent.putExtra("location",items.get(position).getLocation());
            intent.putExtra("score",items.get(position).getScore());
            intent.putExtra("bed",items.get(position).getBed());
            intent.putExtra("desc",items.get(position).getDescription());
            intent.putExtra("pic",items.get(position).getPic());
            intent.putExtra("wifi",items.get(position).isWifi());
            intent.putExtra("guide",items.get(position).isGuide());
            intent.putExtra("price",items.get(position).getPrice());
            holder.itemView.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt,locationTxt,scoreTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt=itemView.findViewById(R.id.titleTxt);
            locationTxt=itemView.findViewById(R.id.locationTxt);
            scoreTxt=itemView.findViewById(R.id.scoreTxt);
            pic=itemView.findViewById(R.id.picImg);
        }
    }
}
