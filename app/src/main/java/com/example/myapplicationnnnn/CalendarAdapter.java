package com.example.myapplicationnnnn;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final List<CalendarDay> calendarDays;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CalendarAdapter(List<CalendarDay> calendarDays, OnItemClickListener onItemClickListener) {
        this.calendarDays = calendarDays;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        CalendarDay calendarDay = calendarDays.get(position);
        holder.textViewDay.setText(String.valueOf(calendarDay.getDay()));

        Uri imageUri = calendarDay.getImageUri();
        if (imageUri != null) {
            holder.imageView.setImageURI(imageUri);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return calendarDays.size();
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDay;
        ImageView imageView;

        CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
