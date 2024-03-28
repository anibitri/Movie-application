package stores;

import java.time.LocalDateTime;

public class RatingsMetaData {
    float Rating;
    LocalDateTime TimeStamp;
    int movieID;
    int userID;
    int size;

    public RatingsMetaData(int userID, int movieID, float rating, LocalDateTime timestamp) {
        this.Rating = rating;
        this.TimeStamp = timestamp;
        this.movieID = movieID;
        this.userID = userID;
    }

    public boolean set(float newRating, LocalDateTime newTimeStamp){
        this.Rating = newRating;
        this.TimeStamp = newTimeStamp;
        return true;
    }

    public int getID() {
        return userID;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        this.Rating = rating;
    }

    public LocalDateTime getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.TimeStamp = timeStamp;
    }

    public int getMovieID() {
        return movieID;
    }

    public boolean containsMovieID(int movieID) {
        return this.movieID == movieID;
    }

    public int size(){
        return size;
    }
}
