package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.quizapp.adapter.RecyclerViewInterface;
import com.example.quizapp.adapter.recyclerViewAdapter;
import com.example.quizapp.model.AnimalList;
import com.example.quizapp.model.AnimalModel;
import com.example.quizapp.model.GlobalList;

import java.util.ArrayList;
import java.util.Collections;

public class GalleryV2 extends AppCompatActivity implements RecyclerViewInterface {

    //private AnimalList animalList;
    private static final int GALLERY_REQUEST = 1; // Class constant for gallery request
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

    public void sortButton(View view){
        // Sort the global list of animals
        GlobalList.getAnimalList().sortAnimalsByName();
        // Refresh the RecyclerView
        refreshRecyclerView();
    }


    public void addButton(View view){
        // Launch the gallery to pick an image
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST); // Make sure GALLERY_REQUEST is a defined constant
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            Uri imageUri = data.getData();
            // Prompt for entering a name
            promptForAnimalName(imageUri);
        }
    }

    private void promptForAnimalName(Uri imageUri) {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add a name for the picture")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = input.getText().toString();
                    if (!name.isEmpty()) {
                        // Add the new animal to the global list
                        AnimalModel newAnimal = new AnimalModel(imageUri, name);
                        GlobalList.getAnimalList().addNewAnimal(newAnimal);
                        // Refresh the RecyclerView to display the new item
                        refreshRecyclerView();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void refreshRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerViewAdapter adapter = (recyclerViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Notify the adapter to refresh the view
        }
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