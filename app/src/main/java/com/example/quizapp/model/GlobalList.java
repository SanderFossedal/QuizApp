package com.example.quizapp.model;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;

import com.example.quizapp.R;

public class GlobalList extends Application {

    private static AnimalList animalList;

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalList.animalList = new AnimalList();
        changeToUri(R.drawable.gorilla, "Gorilla");
        changeToUri(R.drawable.polar_bear, "Polar Bear");
        changeToUri(R.drawable.fox, "Fox");
    }

    /**
     * Converts an image resource ID to a Uri and adds a new AnimalModel to the list.
     * @param imageId The resource ID of the image.
     * @param name The name of the animal.
     */
   public void changeToUri(int imageId, String name){
       Uri uriImage = new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
               .authority(getResources().getResourcePackageName(imageId))
               .appendPath(getResources().getResourceTypeName(imageId))
               .appendPath(getResources().getResourceEntryName(imageId))
               .build();

       animalList.addNewAnimal(new AnimalModel(uriImage, name));
   }

    public static AnimalList getAnimalList() {
        return animalList;
    }

    public static void setAnimalList(AnimalList animalList) {
        GlobalList.animalList = animalList;
    }
}
