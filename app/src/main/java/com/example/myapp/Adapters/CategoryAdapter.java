package com.example.myapp.Adapters;

import android.app.Activity;
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
import com.example.myapp.Activities.MainActivity;
import com.example.myapp.Domains.PopularDomain;
import com.example.myapp.Domains.categoryDomain;

import com.example.myapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

  ArrayList<categoryDomain>catsList;

    public CategoryAdapter(ArrayList<categoryDomain> catsList) {
        this.catsList = catsList;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);

        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
        categoryDomain cat = catsList.get(position);
        holder.titleTxt.setText(cat.getTitle());

        int drawableResourceId=holder.itemView.getResources().getIdentifier(catsList.get(position).getPicPath()
                ,"drawable",holder.itemView.getContext().getPackageName());

         Glide.with(holder.itemView.getContext())
                 .load(drawableResourceId)
                 .into(holder.picImg);

        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), MainActivity.class);
            intent.putExtra("title",cat.getTitle());
            holder.itemView.getContext().startActivity(intent);
            ((Activity) holder.itemView.getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            ((Activity) holder.itemView.getContext()).finish();
        });

    }

    @Override
    public int getItemCount() {
        return catsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView picImg;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            picImg=itemView.findViewById(R.id.catImg);
        }
    }
}
