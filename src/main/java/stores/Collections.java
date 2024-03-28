package stores;
import structures.*;

public class Collections {
    private int size;
    private int collectionID;
    private String collectionName;
    private String collectionPoster;
    private String collectionBackground;
    private MyLinkedList<MetaData> films;

    public Collections(int collectionID, String collectionName, String collectionPoster, String collectionBackground) {
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.collectionPoster = collectionPoster;
        this.collectionBackground = collectionBackground;
        this.films = new MyLinkedList<>();
        this.size = 0;

        
    }

    public boolean add(MetaData newMovie) {
        try {
            films.add(newMovie);
            size++;
            return true;
        } catch (Exception e) {
            return false;
        }
         
    }
    public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int newCollectionID) {
        this.collectionID = newCollectionID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String newCollectionName) {
        this.collectionName = newCollectionName;
    }

    public String getCollectionPoster() {
        return collectionPoster;
    }

    public void setCollectionPoster(String newCollectionPoster) {
        this.collectionPoster = newCollectionPoster;
    }

    public String getCollectionBackground() {
        return collectionBackground;
    }

    public void setCollectionBackground(String newCollectionBackground) {
        this.collectionBackground = newCollectionBackground;
    }

    public MyLinkedList<MetaData> getFims() {
        return films;
    }

    public MetaData getFilm(int id) {
        return films.get(id);
    }

    public int getSize() {
        return size;
    }
}
