package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Gallery_Item_Click extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_item_click);

        String name = getIntent().getStringExtra("NAME");
        int image = getIntent().getIntExtra("IMAGE", 0);

        TextView textView = findViewById(R.id.animal_name_text);
        ImageView imageView = findViewById(R.id.animal_image_big);

        textView.setText(name);
        imageView.setImageResource(image);
    }

    public void backButton(View view){
        Intent intent = new Intent(this, GalleryV2.class);
        startActivity(intent);
    }

    public void deleteButton(View view){
        //Delete code here
    }
}