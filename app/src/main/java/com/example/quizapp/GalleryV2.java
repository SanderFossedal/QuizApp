package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.adapter.RecyclerViewInterface;
import com.example.quizapp.adapter.recyclerViewAdapter;
import com.example.quizapp.model.AnimalList;
import com.example.quizapp.model.AnimalModel;
import com.example.quizapp.model.GlobalList;

import java.util.ArrayList;

public class GalleryV2 extends AppCompatActivity implements RecyclerViewInterface {

    //private AnimalList animalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_v2);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        //GlobalList globalList = (GlobalList) getApplication();

        //AnimalList animalList = globalList.getAnimalList();
        //setUpAnimalModels();

        recyclerViewAdapter adapter = new recyclerViewAdapter(this, GlobalList.getAnimalList(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addButton(){
        
    }

    @Override
    public void onItemClick(int position) {
        //Code for on click in recycleView
        Intent intent = new Intent(this, Gallery_Item_Click.class);

        //Gives information about the AnimalModel by the RecyclerviewAdapter based on the position.
        //Not the best way to do this, should use parselable?
        intent.putExtra("NAME", GlobalList.getAnimalList().getSingleAnimal(position).getName());

        Uri imageUri = GlobalList.getAnimalList().getSingleAnimal(position).getImage();
        intent.putExtra("IMAGE", imageUri.toString());

        //intent.putExtra("ANIMAL", GlobalList.getAnimalList().getSingleAnimal(position));

        startActivity(intent);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}