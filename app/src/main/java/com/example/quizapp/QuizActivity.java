package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapp.model.AnimalList;
import com.example.quizapp.model.AnimalModel;
import com.example.quizapp.model.GlobalList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity {
    // Liste med svaralternativer
    private List<String> answerOptions;
    private String correctAnswer;
    // Liste for å holde stur på viste dyr
    private List<AnimalModel> shownAnimals = new ArrayList<>();
    // Bruker denne i checkAnswer og setUp
    private TextView textViewQuiz;
    private int score = 0;
    private int attempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuiz = findViewById(R.id.textView_Quiz);

        ImageView imageView = findViewById(R.id.imageView);

        // Legger til den globale listen og velger et bilde.
        // dette funker så lenge det er mer enn et bilde igjen, er det et bilde krasjer appen når man åpner quiz aktiviteten fra main aktiviteten.
        AnimalList animalList = GlobalList.getAnimalList();
        Uri imageUri = animalList.randomAnimal().getImage();
        imageView.setImageURI(imageUri);

        setUpQuiz();
    }

    private void setUpQuiz() {
        AnimalList animalList = GlobalList.getAnimalList();
        List<AnimalModel> unusedAnimals = animalList.getListOfAnimals().stream()
                .filter(animal -> !shownAnimals.contains(animal))
                .collect(Collectors.toList());

        if (unusedAnimals.isEmpty()) {
            textViewQuiz.setText("Quiz over! No more images left.");
            // Legger til en forsinkelse før man går tilbake til hovedmenyen slik at brukeren rekker å se meldingen
            new Handler().postDelayed(this::finish, 2000); // Venter i 2 sekunder før aktiviteten avsluttes
            return;
        }

        // Blander listen for å velge et tilfeldig dyr
        Collections.shuffle(unusedAnimals);
        AnimalModel currentAnimal = unusedAnimals.get(0);

        // Hentet svaralternativer - allerede blandet
        answerOptions = animalList.getRandomAnswers(currentAnimal);

        // Oppdaterer UI med det nye spørsmålet
        correctAnswer = currentAnimal.getName();
        Uri imageUri = currentAnimal.getImage();
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageURI(imageUri);

        // Legger til det valgte dyret i listen over viste dyr
        shownAnimals.add(currentAnimal);

        updateAnswerButtons();
    }

    private void updateAnswerButtons() {
        Button answerButton1 = findViewById(R.id.choice1);
        Button answerButton2 = findViewById(R.id.choice2);
        Button answerButton3 = findViewById(R.id.choice3);

        answerButton1.setText(answerOptions.get(0));
        answerButton2.setText(answerOptions.get(1));
        answerButton3.setText(answerOptions.get(2));

        answerButton1.setOnClickListener(v -> checkAnswer(answerOptions.get(0)));
        answerButton2.setOnClickListener(v -> checkAnswer(answerOptions.get(1)));
        answerButton3.setOnClickListener(v -> checkAnswer(answerOptions.get(2)));
    }

    private void checkAnswer(String selectedAnswer) {
        attempts++;
        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            textViewQuiz.setText("Correct!\nScore: " + score + "   Attempts: " + attempts);
        } else {
            textViewQuiz.setText("Wrong! The correct answer was: " + correctAnswer + ". \nScore: " + score + "   Attempts: " + attempts);
        }
        // Går til neste spørsmål etter en kort pause for å la brukeren se svaret
        new Handler().postDelayed(this::setUpQuiz, 2000); // Venter i 2 sekunder før neste spørsmål lastes
    }

    public void backButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
