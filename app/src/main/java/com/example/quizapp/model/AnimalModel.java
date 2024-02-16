package com.example.quizapp.model;

import android.net.Uri;
import android.provider.MediaStore;

import java.io.Serializable;

public class AnimalModel {

    //private int image;

    private Uri image;
    private String name;

//    public AnimalModel(int image, String name) {
//        this.image = image;
//        this.name = name;
//    }

    public AnimalModel(Uri image, String name){
        this.image = image;
        this.name = name;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        // Check if the object is an instance of AnimalModel
        if (!(obj instanceof AnimalModel)) return false;

        AnimalModel other = (AnimalModel) obj;

        // Compare the data members and return accordingly
        return this.name.equals(other.name) && this.image.equals(other.image);
    }
}
