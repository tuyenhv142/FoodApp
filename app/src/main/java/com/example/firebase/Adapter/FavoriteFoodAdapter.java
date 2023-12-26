package com.example.firebase.Adapter;

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

import com.example.firebase.Activity.DetailFoodFavoriteActivity;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteFoodAdapter extends RecyclerView.Adapter<FavoriteFoodAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id, title, category, image;

    public FavoriteFoodAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList category, ArrayList image) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.category = category;
        this.image = image;
    }

    @NonNull
    @Override
    public FavoriteFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_post,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteFoodAdapter.MyViewHolder holder, int position) {

        String idValue = String.valueOf(id.get(position));
        holder.title.setText(String.valueOf(title.get(position)));
        holder.category.setText(String.valueOf(category.get(position)));
        try{
            String imageUrl = String.valueOf(image.get(position));
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_image).into(holder.image);
        }catch (Exception e){
            holder.image.setImageResource(R.drawable.ic_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailFoodFavoriteActivity.class);
                intent.putExtra("foodId",idValue);
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, category;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            category = itemView.findViewById(R.id.content);
            image = itemView.findViewById(R.id.image);
        }
    }
}
