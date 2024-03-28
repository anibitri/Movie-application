package stores; 

import java.time.LocalDate;
import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies{
    Stores stores;
    HashMap<Integer, MetaData> dataTable; //HashMap for moive MetaData
    HashMap<Integer, Collections> collectionDataTable; //Hashmap for collections
    HashMap<Integer, Integer> filmToCollection; //HashMap to connect movies and collections
    int size;

    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        //Initialise the data structures
        this.stores = stores;
        this.dataTable = new HashMap<>();
        this.collectionDataTable = new HashMap<>();
        this.filmToCollection = new HashMap<>();
        size = 0;
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        
        //Check if the the movie is already in the hashmap
        if (!dataTable.containsKey(id)) {
            //If the movie does not exist in the HashMap, add it and increment size
            MetaData newMovie = new MetaData(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
            dataTable.add(id, newMovie); 
            size++;
            return true; 
        }
        
        return false; 
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        //Remove the movie from the HashMap
        boolean removed = dataTable.remove(id);
        if(removed){
            size--; //Decrement size if the movie is removed
        }
        return removed;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        //Convert the HashMap to an array with MetaData objects
        MetaData[] moviesArray = dataTable.MoviesToArray();
        int[] ids = new int[moviesArray.length];
        
        for (int i = 0; i < moviesArray.length; i++) {
            //Store the ID of the movie in the ids array
            ids[i] = moviesArray[i].getID();
        }
        
        return ids;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {

        //Get all film IDs
        int[] allIDs = getAllIDs();

        //Create an array list to store film IDs released within the given range
        MyArrayList<Integer> idsInRange = new MyArrayList<>();

        //Iterate through all movie IDs
        for (int id : allIDs) {

            //Get movie id from the HashMap
            MetaData movieData = dataTable.get(id);

            //Check if the id is not null and its release date is within the given range
            if (movieData != null && movieData.getRelease() != null &&
                !movieData.getRelease().isEqual(start) && !movieData.getRelease().isEqual(end) &&
                movieData.getRelease().isAfter(start) && movieData.getRelease().isBefore(end)) {
                //Add the film ID to the array list
                idsInRange.add(id);
            }
        }

        //Convert the array list to an array
        int[] idsArrayInRange = new int[idsInRange.size()];
        for (int i = 0; i < idsInRange.size(); i++) {
            idsArrayInRange[i] = idsInRange.get(i);
        }

        return idsArrayInRange;
    }

    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getTitle(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getOriginalTitle(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getOverview(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getTagline(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getStatus(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        //Get the movie
        MetaData movie = dataTable.get(id);
        if (movie != null) {
            return movie.getGenres(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getRelease(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getBudget(); //If the movie is not null return what is requested
        }
        return -1;
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getRevenue(); //If the movie is not null return what is requested
        }
        return -1;
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getLanguages(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getOriginalLanguage(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public double getRuntime(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getRuntime(); //If the movie is not null return what is requested
        }
        return -1;
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getHomepage(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getAdult(); //If the movie is not null return what is requested
        }
        return false;
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getVideo(); //If the movie is not null return what is requested
        }
        return false;
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getPoster(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            //If the movie is not null set what is requested and return true
            NewMovie.setVoteAvg(voteAverage);
            NewMovie.setVoteCount(voteCount);
            return true;
        }
        return false;
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1
     */
    @Override
    public double getVoteAverage(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getVoteAverage(); //If the movie is not null return what is requested
        }
        return -1;
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getVoteCount(); //If the movie is not null return what is requested
        }
        return -1;
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        Collections collection = collectionDataTable.get(collectionID);
        MetaData newMovie = dataTable.get(filmID);
        if (newMovie != null) {
            if (collection == null) {
                collection = new Collections(collectionID, collectionName, collectionPosterPath, collectionBackdropPath);
                collectionDataTable.add(collectionID, collection);
                filmToCollection.add(filmID, collectionID);
            }
            return collection.add(newMovie);
        }
        return false;
    }

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        //Get the collection from the HashMap
        Collections collection = collectionDataTable.get(collectionID);

        if (collection != null) {
            //If the collection is not null ad the filmIDs of the movies in the collection to an array with equal size to the collection
            int[] ids = new int[collection.getSize()];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = collection.getFilm(i).getID();
            }
            return ids;
        }
        return new int[0];
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        //Get the collection from the HashMap
        Collections collection = collectionDataTable.get(collectionID);
        if (collection != null){
            return collection.getCollectionName(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        //Get the collection from the HashMap
        Collections collection = collectionDataTable.get(collectionID);
        if (collection != null){
            return collection.getCollectionPoster(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        //Get the collection from the HashMap
        Collections collection = collectionDataTable.get(collectionID);
        if (collection != null){
            return collection.getCollectionBackground(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        //If the filmToCollection hashmap contains the movie, return the collectionID corresponded to the filmID
        if (filmToCollection.containsKey(filmID)) {
            return filmToCollection.get(filmID);
        } 
        return -1;
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        //Get the movie
        MetaData NewMovie = dataTable.get(filmID);
        if (NewMovie != null) {
            NewMovie.setIMDB(imdbID); //If the movie is not null set what is requested and return true
            return true;
        }

        return false;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        //Get the movie
        MetaData NewMovie = dataTable.get(filmID);
        if (NewMovie != null) {
            return NewMovie.getIMDB(); //If the movie is not null return what is requested
        }
        return null;
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            NewMovie.setPopularity(popularity); //If the movie is not null set what is requested and return true
            return true;
        }

        return false;
        
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            return NewMovie.getPopularity(); //If the movie is not null return what is requested
        }
        return -1.0;
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            //If the movie is not null add the company to the corresponding film id in the hashmap
            NewMovie.addCompany(company); 
            return true;
        }
        return false;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        //Get the movie
        MetaData NewMovie = dataTable.get(id);
        if (NewMovie != null) {
            //If the movie is not null add the country to the corresponding film id in the hashmap
            NewMovie.addCountry(country);
            return true;
        }
        return false;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        //Get the movie
        MetaData newmovie = dataTable.get(id);

        //Check if the movie exists
        if(newmovie == null){
            return null;
        }

        //Create an array for production countries
        Company[] companies = new Company[newmovie.getCompanies().size()];

        //Iterate through the countries of the movie and add them to the array
        for(int i = 0; i < newmovie.getCompanies().size(); i++){
            companies[i] = newmovie.getCompanies().get(i);
        }
        return companies;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        //Get the movie
        MetaData newMovie = dataTable.get(id);

        //Check if the movie exists
        if(newMovie == null){
            return null;
        }

        //Create an array for production countries
        String[] countries = new String[newMovie.getCountries().size()];

        //Iterate through the countries of the movie and add them to the array
        for(int i = 0; i < newMovie.getCountries().size(); i++){
            countries[i] = newMovie.getCountries().get(i);
        }
        return countries;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        //Get all the movie IDs
        int[] allIDs = getAllIDs();

        //Create an array list to store the IDs of the movies found
        MyArrayList<Integer> foundIDs = new MyArrayList<>();

        //Iterate through all IDs
        for (int id : allIDs) {

            //Get movie
            MetaData movieData = dataTable.get(id);

            //Check if the search term exists in the title, original title, or overview
            if (movieData.getTitle().contains(searchTerm) ||
                movieData.getOriginalTitle().contains(searchTerm) ||
                movieData.getOverview().contains(searchTerm)) {

                // Add the film ID to the list if the search term is found
                foundIDs.add(id);
            }
        }

        //Convert the array list of found IDs to an array
        int[] foundIDsArray = new int[foundIDs.size()];
        for (int i = 0; i < foundIDs.size(); i++) {
            foundIDsArray[i] = foundIDs.get(i);
        }

        return foundIDsArray;
    }
}
