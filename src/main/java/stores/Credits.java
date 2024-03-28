package stores; 

import structures.*;
import interfaces.ICredits;

public class Credits implements ICredits{
    Stores stores;
    HashMap<Integer, CreditsMetaData> CreditsDataTable; //HashMap for Credits info
    MyLinkedList<Integer> films = new MyLinkedList<>(); //Linked List for movies
    int size;

    /**
     * The constructor for the Credits data store. This is where you should
     * initialise your data structures.
     * 
     * @param stores An object storing all the different key stores, 
     *               including itself
     */
    public Credits (Stores stores) {
        //Initializing data structure
        this.stores = stores;
        this.CreditsDataTable = new HashMap<>();
        size = 0;
    }

    /**
     * Adds data about the people who worked on a given film. The movie ID should be
     * unique
     * 
     * @param cast An array of all cast members that starred in the given film
     * @param crew An array of all crew members that worked on a given film
     * @param id   The (unique) movie ID
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(CastCredit[] cast, CrewCredit[] crew, int id) {
        //Check if the movie is already in the hashmap
        if (!CreditsDataTable.containsKey(id)) {
            //Add the movie to the HashMap, linked list and increment the size
            CreditsMetaData credits = new CreditsMetaData(cast, crew);
            CreditsDataTable.add(id, credits);
            films.add(id);
            size++;
            return true;
        }
        return false;
        
    }

    /**
     * Remove a given films data from the data structure
     * 
     * @param id The movie ID
     * @return TRUE if the data was removed, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        //Remove the movie from the HashMap
        boolean isRemove=CreditsDataTable.remove(id);
        if(isRemove){
            size--; //Decrement size
        }
        return isRemove;
    }

    /**
     * Gets all the cast members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CastCredit objects, one for each member of cast that is 
     *         in the given film. The cast members should be in "order" order. If
     *         there is no cast members attached to a film, or the film canot be 
     *         found, then return an empty array
     */
    @Override
    public CastCredit[] getFilmCast(int filmID) {
        //Get the movie from the HashMap
        CreditsMetaData credits = CreditsDataTable.get(filmID);
        if (credits!= null){
            //If the movie is not null, return the data requsted
            return credits.getCastCredit();
        }

        return new CastCredit[0];
    }

    /**
     * Gets all the crew members for a given film
     * 
     * @param filmID The movie ID
     * @return An array of CrewCredit objects, one for each member of crew that is
     *         in the given film. The crew members should be in ID order. If there 
     *         is no crew members attached to a film, or the film canot be found, 
     *         then return an empty array
     */
    @Override
    public CrewCredit[] getFilmCrew(int filmID) {
        //Get the movie from the HashMap
        CreditsMetaData credits = CreditsDataTable.get(filmID);
        if (credits!= null){
            return credits.getCrewCredit();
        }
        return new CrewCredit[0];
    }

    /**
     * Gets the number of cast that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of cast member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeOfCast(int filmID) {
        //Get the movie from the CastCredit array
        CastCredit[] cast = getFilmCast(filmID);
        if (cast.length > 0) {
            return cast.length; //If the array is not empty return its size
        }
        return -1;
    }

    /**
     * Gets the number of crew that worked on a given film
     * 
     * @param filmID The movie ID
     * @return The number of crew member that worked on a given film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public int sizeofCrew(int filmID) {
        //Get the movie from the CreWCredit array
        CrewCredit[] cast= getFilmCrew(filmID);
        if (cast.length > 0){
            return cast.length; //If the array is not empty return its size
        }
        return -1;
    }

    /**
     * Gets the number of films stored in this data structure
     * 
     * @return The number of films in the data structure
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Gets a list of all unique cast members present in the data structure
     * 
     * @return An array of all unique cast members as Person objects. If there are 
     *         no cast members, then return an empty array
     */
    @Override
    public Person[] getUniqueCast() {
        //Create a new ArrayList for the Cast and a new Set so the IDs are not repeated
        MyArrayList<Person> uniqueCast = new MyArrayList<>();
        Set0<Integer> uniqueCastIDs = new Set0<>(); 

        //Iterate through each movie to get the cast members
        for (int i = 0; i < size(); i++) {
            CastCredit[] castList = getFilmCast(films.get(i));
            for (CastCredit castMember : castList) {
                int castID = castMember.getID();

                //Check is the castID is unique
                if (!uniqueCastIDs.contains(castID)) {

                    //Create a Person object and add to list
                    Person castAsPerson = new Person(castMember.getID(), castMember.getName(), castMember.getProfilePath());
                    uniqueCast.add(castAsPerson);
                    uniqueCastIDs.add(castID);
                }
            }
        }

        //Convert ArrayList to Person Array
        Person[] uniqueCastArray = new Person[uniqueCast.size()];
        for (int i = 0; i < uniqueCast.size(); i++) {
            uniqueCastArray[i] = uniqueCast.get(i);
        }

        return uniqueCastArray;
    }

    /**
     * Gets a list of all unique crew members present in the data structure
     * 
     * @return An array of all unique crew members as Person objects. If there are
     *         no crew members, then return an empty array
     */
    @Override
    public Person[] getUniqueCrew() {
        //Create a new ArrayList for the Cast and a new Set so the IDs are not repeated
        MyArrayList<Person> uniqueCrew = new MyArrayList<>();
        Set0<Integer> uniqueCrewIDs = new Set0<>();

        //Iterate through each movie to get the crew members
        for (int i = 0; i < size(); i++) {
            CrewCredit[] crewList = getFilmCrew(films.get(i));
            for (CrewCredit crewMember : crewList) {
                int crewID = crewMember.getID();

                //Check is the crewID is unique
                if (!uniqueCrewIDs.contains(crewID)) {

                    //Create a Person object and add to list
                    Person crewAsPerson = new Person(crewMember.getID(), crewMember.getName(), crewMember.getProfilePath());
                    uniqueCrew.add(crewAsPerson);
                    uniqueCrewIDs.add(crewID);
                }
            }
        }

        //Convert ArrayList to Person Array
        Person[] uniqueCrewArray = new Person[uniqueCrew.size()];
        for (int i = 0; i < uniqueCrew.size(); i++) {
            uniqueCrewArray[i] = uniqueCrew.get(i);
        }

        return uniqueCrewArray;
    }

    /**
     * Get all the cast members that have the given string within their name
     * 
     * @param cast The string that needs to be found
     * @return An array of unique Person objects of all cast members that have the 
     *         requested string in their name
     */
    @Override
    public Person[] findCast(String cast) {
        MyLinkedList<Person> foundCastList = new MyLinkedList<>(); //Linked List to store Cast Members
        Person[] castMembers = getUniqueCast(); //Person array to get the unique cast

        //Iterate through each member
        for (int i=0; i<castMembers.length; i++){
            Person castMember = castMembers[i];
            
            //If the cast member contains the String, add it to the list
            if (castMember.getName().contains(cast)){
                foundCastList.add(castMember);
            }
        }

        //Convert linked list to Person Array
        Person[] foundCastArray = new Person[foundCastList.size()];
        for (int k=0; k<foundCastList.size(); k++){
            foundCastArray[k] = foundCastList.get(k);
        }

        return foundCastArray;
    }

    /**
     * Get all the crew members that have the given string within their name
     * 
     * @param crew The string that needs to be found
     * @return An array of unique Person objects of all crew members that have the 
     *         requested string in their name
     */
    @Override
    public Person[] findCrew(String crew) {
        MyLinkedList<Person> foundCrewList = new MyLinkedList<>(); //Linked List to store Crew Members
        Person[] crewMembers = getUniqueCrew(); //Person array to get the unique Crew

        //Iterate through each member
        for (int i=0; i<crewMembers.length; i++){
            Person crewMember = crewMembers[i];

            //If the crew member contains the String, add it to the list
            if (crewMember.getName().contains(crew)){
                foundCrewList.add(crewMember);
            }
        }

        //Convert linked list to Person Array
        Person[] foundCrewArray = new Person[foundCrewList.size()];
        for (int k=0; k<foundCrewList.size(); k++){
            foundCrewArray[k] = foundCrewList.get(k);
        }

        return foundCrewArray;
    }

    /**
     * Gets the Person object corresponding to the cast ID
     * 
     * @param castID The cast ID of the person to be found
     * @return The Person object corresponding to the cast ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCast(int castID) {
        //Get unique cast members
        Person[] castMembers = getUniqueCast();

        //Iterate through the members
        for (int k=0; k<castMembers.length; k++){
            //If the ID of the cast member is the same as the ID passed, return the cast member
            if (castMembers[k].getID()== castID){
                return castMembers[k];
            }
        }
        return null;
    }

    /**
     * Gets the Person object corresponding to the crew ID
     * 
     * @param crewID The crew ID of the person to be found
     * @return The Person object corresponding to the crew ID provided. 
     *         If a person cannot be found, then return null
     */
    @Override
    public Person getCrew(int crewID){
        //Get unique crew members
        Person[] crewMembers = getUniqueCrew();

        //Iterate through the members
        for (int k=0; k<crewMembers.length; k++){

            //If the ID of the cast member is the same as the ID passed, return the cast member
            if (crewMembers[k].getID()== crewID){
                return crewMembers[k];
            }
        }
        return null;    
    }

    
    /**
     * Get an array of film IDs where the cast member has starred in
     * 
     * @param castID The cast ID of the person
     * @return An array of all the films the member of cast has starred
     *         in. If there are no films attached to the cast member, 
     *         then return an empty array
     */
    @Override
    public int[] getCastFilms(int castID){
        //Create a Linked List for movie IDs
        MyLinkedList<Integer> FilmIdList = new MyLinkedList<>();

        //Iterate through the list of movies
        for (int i=0; i<films.size(); i++) {

            //For each movie in the array list get the id and cast list
            int movie = films.get(i);
            CastCredit[] castList = getFilmCast(movie);

            //Iterate through the cast list of the movie
            for (CastCredit j : castList){
                if (j.getID() == castID){
                    //If a movie is found add it to the linked list and break out of the loop
                    FilmIdList.add(movie);
                    break;
                }

            }
        }

        //Convert the linked list to an array
        int[] filmIDArray = new int[FilmIdList.size()];
        for (int k = 0; k < FilmIdList.size(); k++) {
            filmIDArray[k] = FilmIdList.get(k);
        }

        return filmIDArray;  
    }

    /**
     * Get an array of film IDs where the crew member has starred in
     * 
     * @param crewID The crew ID of the person
     * @return An array of all the films the member of crew has starred
     *         in. If there are no films attached to the crew member, 
     *         then return an empty array
     */
    @Override
    public int[] getCrewFilms(int crewID) {
        //Create a Linked List for movie IDs
        MyLinkedList<Integer> FilmIdList = new MyLinkedList<>();

        //Iterate through the list of movies
        for (int i=0; i<films.size(); i++) {

            //For each movie in the array list get the id and crew list
            int movie = films.get(i);
            CrewCredit[] crewList = getFilmCrew(movie);

            //Iterate through the crew list of the movie
            for (CrewCredit j : crewList){
                //If a movie is found add it to the linked list and break out of the loop
                if (j.getID() == crewID){
                    FilmIdList.add(movie);
                    break;
                }
            }
        }

        //Convert the linked list to an array
        int[] filmIDArray = new int[FilmIdList.size()];
        for (int k = 0; k < FilmIdList.size(); k++) {
            filmIDArray[k] = FilmIdList.get(k);
        }
        return filmIDArray;
    }

    /**
     * Get the films that this cast member stars in (in the top 3 cast
     * members/top 3 billing). This is determined by the order field in
     * the CastCredit class
     * 
     * @param castID The cast ID of the cast member to be searched for
     * @return An array of film IDs where the the cast member stars in.
     *         If there are no films where the cast member has starred in,
     *         or the cast member does not exist, return an empty array
     */
    @Override
    public int[] getCastStarsInFilms(int castID){
        //Create a Linked List for the stars
        MyLinkedList<Integer> starsInFilms = new MyLinkedList<>();

         //Iterate through the list of movies
        for (int i = 0; i < films.size(); i++) {

            //For each movie in the array list get the id and cast list
            int movieID = films.get(i);
            CastCredit[] castList = getFilmCast(movieID);

            //Iterate through the cast list
            for (CastCredit castCredit : castList) {

                //If the IDs match and order is <= 3, add the cast member to the linked list and break out of the loop
                if (castCredit.getID() == castID && castCredit.getOrder() <= 3) {
                    starsInFilms.add(movieID);
                    break;
                }
            }
        }

        //Convert the linked list to an array
        int[] starsInFilmsArray = new int[starsInFilms.size()];
        for (int i = 0; i < starsInFilms.size(); i++) {
            starsInFilmsArray[i] = starsInFilms.get(i);
        }

        return starsInFilmsArray;
        }
    
    /**
     * Get Person objects for cast members who have appeared in the most
     * films. If the cast member has multiple roles within the film, then
     * they would get a credit per role played. For example, if a cast
     * member performed as 2 roles in the same film, then this would count
     * as 2 credits. The list should be ordered by the highest number of credits.
     * 
     * @param numResults The maximum number of elements that should be returned
     * @return An array of Person objects corresponding to the cast members
     *         with the most credits, ordered by the highest number of credits.
     *         If there are less cast members that the number required, then the
     *         list should be the same number of cast members found.
     */
    @Override
    public Person[] getMostCastCredits(int numResults) {
        //Create a new set for the cast IDs
        Set0<Integer> castIDsSet = new Set0<>();
        int[] creditsCounts = new int[2000000]; //Insanely large number because why not

        //Iterate through the list of movies
        for (int i = 0; i < films.size(); i++) {

            //For each movie get the movie id and the cast list
            int movieID = films.get(i);
            CastCredit[] castList = getFilmCast(movieID);

            //Iterate through the cast list
            for (CastCredit castCredit : castList) {

                //Get the cast ID, add it to the lest and the array
                int castID = castCredit.getID();
                castIDsSet.add(castID);
                creditsCounts[castID]++;
            }
        }

        //Get the top cast members
        int numCastMembers = Math.min(numResults, castIDsSet.size());
        Person[] topCastMembers = new Person[numCastMembers];

        //Convert the set to an array
        Integer[] sortedCastIDs = new Integer[castIDsSet.size()];
        int index = 0;
        for (Integer castID : castIDsSet) {
            sortedCastIDs[index++] = castID;
        }

        //Sort the cast ID based on the credit count using quicksort
        quickSort(sortedCastIDs, creditsCounts, 0, sortedCastIDs.length - 1);

        //Convert sorted cast IDs to Person array
        for (int i = 0; i < numCastMembers; i++) {
            topCastMembers[i] = getCast(sortedCastIDs[i]);
        }

        return topCastMembers;
    }

    /**
     * Get the number of credits for a given cast member. If the cast member has
     * multiple roles within the film, then they would get a credit per role
     * played. For example, if a cast member performed as 2 roles in the same film,
     * then this would count as 2 credits.
     * 
     * @param castID A cast ID representing the cast member to be found
     * @return The number of credits the given cast member has. If the cast member
     *         cannot be found, return -1
     */
    @Override
    public int getNumCastCredits(int castID) {
        boolean found = false;
        int credits = 0;

        //Iterate through the list of movies
        for (int i = 0; i < films.size(); i++) {

            //For each movie get the id and cast list
            int movieID = films.get(i);
            CastCredit[] castList = getFilmCast(movieID);

            //Iterate through the cast list
            for (CastCredit castCredit : castList) {

                //If the IDs match, increment credits
                if (castCredit.getID() == castID) {
                    credits++;
                    found = true;
                }
            }
        }
        return found ? credits : -1;
    }




    //HELPER METHODS BELOW



    // Quicksort algorithm
    private void quickSort(Integer[] arr, int[] creditsCounts, int low, int high) {
        if (low < high) {
            int pi = partition(arr, creditsCounts, low, high);
            quickSort(arr, creditsCounts, low, pi - 1);
            quickSort(arr, creditsCounts, pi + 1, high);
        }
    }

    // Partition function for quicksort
    private int partition(Integer[] arr, int[] creditsCounts, int low, int high) {
        int pivot = creditsCounts[arr[high]];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (creditsCounts[arr[j]] >= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
