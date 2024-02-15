package com.example.quizapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.model.AnimalList;
import com.example.quizapp.model.AnimalModel;

import java.util.ArrayList;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    AnimalList animalsList;
    public recyclerViewAdapter(Context context, AnimalList animalsList,
                               RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.animalsList = animalsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public recyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout (Giving look to rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);
        return new recyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to the view created in the single_item layout
        // based on the position of the recycler view.

        holder.textView.setText(animalsList.getSingleAnimal(position).getName());
        holder.imageView.setImageURI(animalsList.getSingleAnimal(position).getImage());
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Grabbing the views from the single_item layout file
        //Kinda like a onCreate method
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.animal_image);
            textView = itemView.findViewById(R.id.animal_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
