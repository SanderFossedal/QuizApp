package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizapp.adapter.RecyclerViewInterface;
import com.example.quizapp.adapter.recyclerViewAdapter;
import com.example.quizapp.model.AnimalModel;

import java.util.ArrayList;

public class GalleryV2 extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<AnimalModel> animals = new ArrayList<>();
    int[] images = {R.drawable.polar_bear, R.drawable.gorilla};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_v2);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);


        setUpAnimalModels();

        recyclerViewAdapter adapter = new recyclerViewAdapter(this, animals, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpAnimalModels(){
        String[] animalNames = getResources().getStringArray(R.array.animal_names);

        for(int i = 0; i< animalNames.length; i++){
            animals.add(new AnimalModel(images[i], animalNames[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        //Code for on click in recycleView
        Intent intent = new Intent(this, Gallery_Item_Click.class);

        //Gives information about the AnimalModel by the RecyclerviewAdapter based on the position.
        //Not the best way to do this, should use parselable?
        intent.putExtra("NAME", animals.get(position).getName());
        intent.putExtra("IMAGE", animals.get(position).getImage());

        startActivity(intent);
    }
}