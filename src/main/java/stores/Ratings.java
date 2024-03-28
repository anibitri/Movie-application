package stores;

import java.time.LocalDateTime;
import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;
    HashMap<Integer, RatingsMetaData> RatingsDataTable; //HashMap for Ratings Data
    int size;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        //Initialize the data structure
        this.stores = stores;
        this.RatingsDataTable = new HashMap<>();
        this.size = 0;
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userID, int movieID, float rating, LocalDateTime timestamp) {
        //Generatate a unique key from the user ID and the movie ID
        int uniqueKey = generateKey(userID, movieID);

        //Check if the rating is already in the HashMap
        if (!RatingsDataTable.containsKey(uniqueKey)) {

            //If not, add the rating with the uniqueKey as key and increment size
            RatingsDataTable.add(uniqueKey, new RatingsMetaData(userID, movieID, rating, timestamp));
            size++;
            return true;
        }

        return false; 
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userID, int movieID) {

        //Genreate the unique key from the user ID and movie ID
        int uniqueKey = generateKey(userID, movieID);

        //If the HashMap contains the key, remove the rating from the HashMap and decrement size
        if (RatingsDataTable.containsKey(uniqueKey)) {
            RatingsDataTable.remove(uniqueKey);
            size--;
            return true;
        }

        return false;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userID, int movieID, float rating, LocalDateTime timestamp) {

        //Genreate the unique key from the user ID and movie ID
        int uniqueKey = generateKey(userID, movieID);

        //Overwrite if rating exists, or add new rating
        RatingsDataTable.add(uniqueKey, new RatingsMetaData(userID, movieID, rating, timestamp)); 

        return true;
        
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieID) {

        //Create an Array List for movie ratings and a Set for user IDs
        MyArrayList<Float> ratingsList = new MyArrayList<>();
        Set0<Integer> addedUserIDs = new Set0<>();

        //Iterate through the entries in the hash map
        for (int i = 0; i < RatingsDataTable.table.length; i++) {
            KeyValuePairLinkedList<Integer, RatingsMetaData> linkedList = RatingsDataTable.table[i];
            ListElement<KeyValuePair<Integer, RatingsMetaData>> current = linkedList.getHead();

            //Traverse the linked list in the current bucket
            while (current != null) {
                RatingsMetaData ratingData = current.getValue().getValue();

                //Check if the movie IDs match
                if (ratingData.getMovieID() == movieID) {

                    int userID = ratingData.getID();

                    //Check if this user's rating is already added for the movie
                    if (!addedUserIDs.contains(userID)) {
                        ratingsList.add(ratingData.getRating());
                        addedUserIDs.add(userID);
                    }
                }
                current = current.getNext();
            }
    }

    //Convert the Array List to a float array
    float[] ratingsArray = new float[ratingsList.size()];
    for (int i = 0; i < ratingsList.size(); i++) {
        ratingsArray[i] = ratingsList.get(i);
    }

    return ratingsArray;
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        
        //Create an Array List for users
        MyArrayList<Float> userList = new MyArrayList<>();

        //Iterate through the ratings array and collect ratings for the given user ID
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) { //Turn the HashMap into an array

            //If the user IDs match, add it to the Array List
            if (rating.getID() == userid) {
                userList.add(rating.getRating());
            }
        }

        //Convert the array list to a float array
        float[] ratingsArray = new float[userList.size()];
        for (int i = 0; i < userList.size(); i++) {
            ratingsArray[i] = userList.get(i);
        }

        return ratingsArray;
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in ratings, but does exist in the movies store, return 0.0f. 
     *         If the film cannot be found in ratings or movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieID) {
        //Get all ratings for the movie
        float[] ratings = getMovieRatings(movieID);

        //Check if there are any ratings for the movie
        if (ratings.length == 0) {
            // If no ratings found, return -1.0f
            return -1.0f;
        }

        // Calculate the sum of ratings
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }

        //Calculate the average rating
        return sum / ratings.length;
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found, or there are no rating, return -1
     */
    @Override
    public float getUserAverageRating(int userid) {

        //Turn the HashMap into a RatingsMetaData array
        RatingsMetaData[] allRatings = RatingsDataTable.RatingsToArray();

        //variables for sum and number of ratings
        float sum = 0;
        int count = 0;

        //Iterate over each rating
        for (RatingsMetaData rating : allRatings) {

            //Check if the rating belongs to the specified user
            if (rating.getID() == userid) {
                sum += rating.getRating(); //Add the rating to the sum
                count++; // Increment the count of ratings
            }
        }

        //Check if the user has at least one rating
        if (count > 0) {
            //Calculate the average rating
            float averageRating = sum / count;
            return averageRating;
        } else {
            //If the user has no ratings, return -1
            return -1;
    }
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getMostRatedMovies(int num) {

        // Find the maximum movie ID from the ratings data
        int maxMovieID = 0;

        //Iterate through the ratings in the HashMap
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) { //Turn the HashMap into an array
            int movieID = rating.getMovieID();
            if (movieID > maxMovieID) {
                maxMovieID = movieID;
            }
        }

        //Create an array to store the number of ratings for each movie
        int[] movieRatingsCount = new int[maxMovieID + 1];

        //Count the number of ratings for each movie
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) {
            int movieID = rating.getMovieID();
            movieRatingsCount[movieID]++;
        }

        //Sort movie IDs based on the number of ratings using merge sort
        int[] sortedMovieIds = new int[maxMovieID + 1];
        for (int i = 0; i <= maxMovieID; i++) {
            sortedMovieIds[i] = i;
        }

        //Merge sort
        mergeSort1(sortedMovieIds, movieRatingsCount, 0, maxMovieID);

        //Get the top N movie IDs
        int[] topRatedMovies = new int[Math.min(num, maxMovieID + 1)];
        System.arraycopy(sortedMovieIds, 0, topRatedMovies, 0, topRatedMovies.length);

        return topRatedMovies;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        //Find the maximum user ID from the ratings data
        int maxUserID = 0;

        //Iterate through the ratings in the HashMap
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) { //Turn the HashMap into an array
            int userID = rating.getID();
            if (userID > maxUserID) {
                maxUserID = userID;
            }
        }

        //Create an array to store the number of ratings for each user
        int[] userRatingsCount = new int[maxUserID + 1];

        //Count the number of ratings for each user
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) {
            int userID = rating.getID();
            userRatingsCount[userID]++;
        }

        //Sort user IDs based on the number of ratings using merge sort
        int[] sortedUserIds = new int[maxUserID + 1];
        for (int i = 0; i <= maxUserID; i++) {
            sortedUserIds[i] = i;
        }

        //Merge sort
        mergeSort(sortedUserIds, userRatingsCount, 0, maxUserID);

        //Get the top N user IDs
        int[] topRatedUsers = new int[Math.min(num, maxUserID + 1)];
        System.arraycopy(sortedUserIds, 0, topRatedUsers, 0, topRatedUsers.length);

        return topRatedUsers;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the movies store, but there
     *         are no ratings for it, then return 0. If the movie
     *         does not exist in the ratings or movies store, then
     *         return -1
     */
    @Override
    public int getNumRatings(int movieID) {
        //Initialize a variable to count the number of ratings
        int count = 0;
        boolean movieExists = false;

        //Iterate over the buckets in the RatingsDataTable
        for (int i = 0; i < RatingsDataTable.table.length; i++) {

            //Iterate over the linked list in the current bucket
            ListElement<KeyValuePair<Integer, RatingsMetaData>> current = RatingsDataTable.table[i].getHead();
            while (current != null) {

                //Check if the movie ID matches
                if (current.getValue().getKey() % 1000000 == movieID) {
                    count++;
                    movieExists = true;
                }
                current = current.getNext();
            }
        }

        //Return -1 if the movie does not exist
        if (!movieExists) {
            return -1;
        }

        return count;
    }

    /**
     * Get the highest average rated film IDs, in order of there average rating
     * (hightst first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        //Create a set to store unique movie IDs
        Set0<Integer> movieIDsSet = new Set0<>();

        //Iterate through the ratings to collect unique movie IDs
        for (RatingsMetaData rating : RatingsDataTable.RatingsToArray()) { //Turn the HashMap to an array
            movieIDsSet.add(rating.getMovieID());
        }

        //Convert the set of movie IDs to an array
        int numMovies = movieIDsSet.size();
        int[] movieIDs = new int[numMovies];
        int index = 0;
        for (int movieID : movieIDsSet) {
            movieIDs[index++] = movieID;
        }

        //Initialize an array to store the average ratings for each movie
        float[] averageRatings = new float[numMovies];

        //Calculate the average rating for each movie
        for (int i = 0; i < numMovies; i++) {
            averageRatings[i] = getMovieAverageRating(movieIDs[i]);
        }

        //Perform merge sort to sort movie IDs based on average ratings
        mergeSort2(movieIDs, averageRatings, 0, numMovies - 1);

        //Get the top N movie IDs
        int[] topRatedMovies = new int[Math.min(numResults, numMovies)];
        for (int i = 0; i < topRatedMovies.length; i++) {
            topRatedMovies[i] = movieIDs[i];
        }

        return topRatedMovies;
    }




    //HELPER METHODS

    private int generateKey(int userID, int movieID) {
        return userID * 1000000 + movieID; // Combining userID and movieID to form a unique key
    }

    // Merge sort algorithm for MostRatedUsers
    private void mergeSort(int[] arr, int[] ratingsCount, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(arr, ratingsCount, low, mid);
            mergeSort(arr, ratingsCount, mid + 1, high);
            merge(arr, ratingsCount, low, mid, high);
        }
    }

    // Merge function for merge sort
    private void merge(int[] arr, int[] ratingsCount, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        int i, j, k;

        // Copy data to temp arrays
        for (i = 0; i < n1; i++)
            leftArray[i] = arr[low + i];
        for (j = 0; j < n2; j++)
            rightArray[j] = arr[mid + 1 + j];

        // Merge the temp arrays
        i = 0;
        j = 0;
        k = low;
        while (i < n1 && j < n2) {
            if (ratingsCount[leftArray[i]] >= ratingsCount[rightArray[j]]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of leftArray[], if any
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy the remaining elements of rightArray[], if any
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Merge sort algorithm for MostRatedMovies
    private void mergeSort1(int[] arr, int[] ratingsCount, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort1(arr, ratingsCount, low, mid);
            mergeSort1(arr, ratingsCount, mid + 1, high);
            merge1(arr, ratingsCount, low, mid, high);
        }
    }

    // Merge function for merge sort
    private void merge1(int[] arr, int[] ratingsCount, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        int i, j, k;

        // Copy data to temp arrays
        for (i = 0; i < n1; i++)
            leftArray[i] = arr[low + i];
        for (j = 0; j < n2; j++)
            rightArray[j] = arr[mid + 1 + j];

        // Merge the temp arrays
        i = 0;
        j = 0;
        k = low;
        while (i < n1 && j < n2) {
            if (ratingsCount[leftArray[i]] >= ratingsCount[rightArray[j]]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of leftArray[], if any
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy the remaining elements of rightArray[], if any
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Merge sort algorithm for TopAverageRatedMovies
    private void mergeSort2(int[] arr, float[] ratings, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort2(arr, ratings, low, mid);
            mergeSort2(arr, ratings, mid + 1, high);
            merge2(arr, ratings, low, mid, high);
        }
    }

    // Merge function for merge sort
    private void merge2(int[] arr, float[] ratings, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        float[] leftRatings = new float[n1];
        float[] rightRatings = new float[n2];
        int i, j, k;

        // Copy data to temp arrays
        for (i = 0; i < n1; i++) {
            leftArray[i] = arr[low + i];
            leftRatings[i] = ratings[low + i];
        }
        for (j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
            rightRatings[j] = ratings[mid + 1 + j];
        }

        // Merge the temp arrays
        i = 0;
        j = 0;
        k = low;
        while (i < n1 && j < n2) {
            if (leftRatings[i] >= rightRatings[j]) {
                arr[k] = leftArray[i];
                ratings[k] = leftRatings[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                ratings[k] = rightRatings[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of leftArray[], if any
        while (i < n1) {
            arr[k] = leftArray[i];
            ratings[k] = leftRatings[i];
            i++;
            k++;
        }

        // Copy the remaining elements of rightArray[], if any
        while (j < n2) {
            arr[k] = rightArray[j];
            ratings[k] = rightRatings[j];
            j++;
            k++;
        }
    }
}
