package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizapp.adapter.RecyclerViewInterface;
import com.example.quizapp.adapter.recyclerViewAdapter;
import com.example.quizapp.model.AnimalModel;
import com.example.quizapp.model.GlobalList;

public class GalleryActivity extends AppCompatActivity implements RecyclerViewInterface {

    //private AnimalList animalList;
    private static final int GALLERY_REQUEST = 1; // Class constant for gallery request
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        //GlobalList globalList = (GlobalList) getApplication();

        //AnimalList animalList = globalList.getAnimalList();
        //setUpAnimalModels();

        recyclerViewAdapter adapter = new recyclerViewAdapter(this, GlobalList.getAnimalList(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Handles the sorting of animals by their names and updates the UI accordingly.
     * This method sorts the global list of animals either in ascending (A-Z) or descending (Z-A) order,
     * based on the current sorting state. After sorting, it refreshes the RecyclerView to display the sorted list.
     * It also updates the text of the sort button to reflect the current sorting order for the next sort action.
     *
     * @param view The view that triggered this method, typically a sort button in the user interface.
     */
    public void sortButton(View view){
        // Sort the global list of animals
        GlobalList.getAnimalList().sortAnimalsByName();
        // Refresh the RecyclerView
        refreshRecyclerView();

        Button sortButton = (Button) findViewById(R.id.button2);

        if (GlobalList.getAnimalList().getIsSortedAscending()) {
            sortButton.setText("Sort A-Z");
        } else {
            sortButton.setText("Sort Z-A");
        }
    }


    public void addButton(View view){
        // Launch the gallery to pick an image
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }


    /**
     * Handles the result from launching the gallery for image selection.
     * If the result is OK and the request code matches, prompts the user to enter a name for the new animal.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing identification of who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached as Extras).
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {
            Uri imageUri = data.getData();
            // Prompt for entering a name
            promptForAnimalName(imageUri);
        }
    }

    /**
     * Displays a dialog prompting the user to enter a name for the new animal picture selected from the gallery.
     * If a name is entered and confirmed with "OK", a new AnimalModel instance is created and added to the global list.
     * The RecyclerView is then refreshed to include the newly added animal.
     *
     * @param imageUri The URI of the selected image to be associated with the new animal.
     */
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