package com.example.quizapp.model;

import android.net.Uri;
import android.provider.MediaStore;

public class AnimalModel {

    private int image;
    private String name;

    public AnimalModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

//    public AnimalModel(int pictureId, String name){
//        Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(pictureId) );
//        image = uri;
//        this.name = name;
//    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
