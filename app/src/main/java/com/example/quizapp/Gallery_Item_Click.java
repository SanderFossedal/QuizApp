package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapp.model.AnimalModel;
import com.example.quizapp.model.GlobalList;

public class Gallery_Item_Click extends AppCompatActivity {

    //private AnimalModel animal;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_item_click);

        name = getIntent().getStringExtra("NAME");

        String imageUriString = getIntent().getStringExtra("IMAGE");
        Uri imageUri = Uri.parse(imageUriString);


        //animal = (AnimalModel) getIntent().getSerializableExtra("ANIMAL");


        TextView textView = findViewById(R.id.animal_name_text);
        ImageView imageView = findViewById(R.id.animal_image_big);

        textView.setText(name);
        imageView.setImageURI(imageUri);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, GalleryV2.class);
        startActivity(intent);
    }

    public void deleteButton(View view){
        // Bruker AlertDialog.Builder for å bygge og vise en dialogboks til brukeren
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete picture");
        builder.setMessage("Are you sure you want to delete the picture?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Sletter elementet fra listen
            GlobalList.getAnimalList().deleteAnimalByName(name);
            backButton(view);
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}