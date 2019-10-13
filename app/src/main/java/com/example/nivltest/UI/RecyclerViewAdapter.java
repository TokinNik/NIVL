 package com.example.nivltest.UI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nivltest.Net.NivlData;
import com.example.nivltest.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    public static final String TAG = "RECYCLER_VIEW_ADAPTER";
    private List<NivlData.Collection.Item> dataList;
    private MainActivity.OnListInteractionListener lisener;

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final View view;
        final TextView titleTextView;
        final TextView dateTextView;
        final TextView typeTextView;
        final ImageView imageView;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.view = itemView;
            titleTextView = itemView.findViewById(R.id.title_textView);
            dateTextView = itemView.findViewById(R.id.date_textView);
            typeTextView = itemView.findViewById(R.id.type_textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    RecyclerViewAdapter(List<NivlData.Collection.Item> data, MainActivity.OnListInteractionListener listener)
    {
        this.dataList = data;
        this.lisener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
         v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new RecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position)
    {
        Log.d(TAG, "onBindViewHolder: " + dataList.get(position).getData().get(0).getTitle());
        holder.titleTextView.setText(dataList.get(position).getData().get(0).getTitle());
        holder.dateTextView.setText(dataList.get(position).getData().get(0).getDate_created().substring(0,10));
        holder.typeTextView.setText(dataList.get(position).getData().get(0).getMedia_type());
        if (dataList.get(position).getData().get(0).getMedia_type().equals("image"))
        {
            Picasso.with(holder.view.getContext())
                    .load(dataList.get(position).getLinks().get(0).getHref())
                    .placeholder(R.drawable.nasa_logo)
                    .into(holder.imageView);
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.nasa_logo);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                lisener.OnListInteraction(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
