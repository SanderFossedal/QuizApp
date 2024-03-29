package com.example.quizapp.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalList{
    // Standardverdi for sortering
    private boolean isSortedAscending = true;
    private List<AnimalModel> listOfAnimals;

    public AnimalList() {
        this.listOfAnimals = new ArrayList<AnimalModel>();
    }

    public List<AnimalModel> getListOfAnimals() {
        return listOfAnimals;
    }

    public AnimalModel getSingleAnimal(int position){
        return listOfAnimals.get(position);
    }

    public int size(){
        return listOfAnimals.size();
    }

    public void setListOfAnimals(List<AnimalModel> listOfAnimals) {
        this.listOfAnimals = listOfAnimals;
    }

    public void addNewAnimal(AnimalModel animal){
        listOfAnimals.add(animal);
    }

    public void deleteAnimal(AnimalModel animal){
        listOfAnimals.remove(animal);
    }

    /**
     * Removes the first animal with the specified name from the list of animals.
     * This method iterates over the list of animals and removes the first occurrence
     * of an animal that has a matching name. If no animal with the specified name
     * is found, the list remains unchanged.
     *
     * @param name The name of the animal to be removed from the list.
     */
    public void deleteAnimalByName(String name) {
        Iterator<AnimalModel> iterator = listOfAnimals.iterator();
        while (iterator.hasNext()) {
            AnimalModel animal = iterator.next();
            if (animal.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
    }


    /**
     * Selects and returns a random animal from a pre-defined list, ensuring it's
     * different from the initially first animal in the list. This method shuffles
     * the list until the first element changes, then returns the new first element.
     *
     *
     * @return A randomly selected `AnimalModel` from the list, distinct from the
     *         animal initially at the first position if possible and if the list
     *         contains more than one unique animal.
     * @throws IndexOutOfBoundsException if `listOfAnimals` is empty.
     */
    public AnimalModel randomAnimal(){
        List<AnimalModel> listCopy = listOfAnimals;
        AnimalModel temp = listCopy.get(0);
        while (listCopy.get(0).equals(temp)){
            Collections.shuffle(listCopy);
        }

        return listCopy.get(0);
    }

    /**
     * Generates a list containing three random answers: one correct and two incorrect.
     * The final list is shuffled to randomize the order of answers.
     *
     * @param correctAnimal The correct `Animal` instance to be included in the answer list.
     * @return A list of three strings: the correct answer's name and two randomly chosen incorrect
     *         answer names. The order of answers in the list is randomized.
     * @throws IndexOutOfBoundsException if `animalList` contains less than three options, which
     *         could occur when attempting to access elements beyond the list size.
     */
    public List<String> getRandomAnswers(AnimalModel correctAnimal) {
        List<String> answers = new ArrayList<String>();

        // Add the correct answer first
        answers.add(correctAnimal.getName());

        // Create a copy of the optionList excluding the correct option
        List<AnimalModel> filteredList = listOfAnimals.stream()
                .filter(animalModel -> !animalModel.equals(correctAnimal))
                .collect(Collectors.toList());

        // Shuffle the filtered list to randomize the order
        Collections.shuffle(filteredList);

        // Add two incorrect answers from the shuffled and filtered list
        answers.add(filteredList.get(0).getName());
        answers.add(filteredList.get(1).getName());

        // Shuffle the answers list to mix correct and incorrect answers
        Collections.shuffle(answers);

        return answers;
    }

    /**
     * Sorts the list of animals by their names either in ascending (A-Z) or descending (Z-A) order,
     * toggling the sorting order each time the method is called.
     */
    public void sortAnimalsByName() {
        if (isSortedAscending) {
            // Sorter A-Z
            Collections.sort(listOfAnimals, (animal1, animal2) -> animal1.getName().compareToIgnoreCase(animal2.getName()));
        } else {
            // Sorter Z-A
            Collections.sort(listOfAnimals, (animal1, animal2) -> animal2.getName().compareToIgnoreCase(animal1.getName()));
        }
        // Bytt sorteringstilstand
        isSortedAscending = !isSortedAscending;
    }

    // La til denne da den brukes på sortButton i GalleryActivity
    public boolean getIsSortedAscending() {
        return isSortedAscending;
    }
}
