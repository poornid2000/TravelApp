package com.example.myapp.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Domains.categoryDomain;

import com.example.myapp.R;
import com.example.myapp.SQLiteHelper;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.viewHolder> {

    ArrayList<categoryDomain> bookingList;

    Context context;

    public BookingAdapter(ArrayList<categoryDomain> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookingAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_booking,parent,false);

        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.viewHolder holder, int position) {
        categoryDomain cat = bookingList.get(position);
        holder.titleTxt.setText(getLocTitle(cat.getLoc_id()));
        holder.timeTxt.setText(cat.getPicPath());

        holder.delText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?");
                builder.setTitle("   Cancel Booking");
                builder.setCancelable(true);
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    deleteBooking(cat.getId());
                    bookingList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), bookingList.size());
                    dialog.cancel();

                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void deleteBooking(int id) {
        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id_value = String.valueOf(id);

        String selection = "id = ?";
        String[] selectionArgs = {id_value};
        db.delete("bookings", selection, selectionArgs);

        db.close();
    }

    private String getLocTitle(int locId) {

        SQLiteHelper dbHelper = new SQLiteHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"title"};
        String selection = "id = ?";

        String locIdStr = String.valueOf(locId);

        String[] selectionArgs = {locIdStr};

        Cursor cursor = db.query("locations", projection, selection, selectionArgs, null, null, null);
        String locTitle = "";

        if (cursor != null && cursor.moveToFirst()) {
            locTitle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            cursor.close();
        }

        return locTitle;
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt, timeTxt, delText;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.booking_location);
            timeTxt=itemView.findViewById(R.id.booking_timestamp);
            delText=itemView.findViewById(R.id.delete_booking);
        }
    }
}

