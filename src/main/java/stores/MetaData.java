package stores;
import structures.*;
import java.time.LocalDate;

public class MetaData {

    private int Id;
    private String Title;
    private String OriginalTitle;
    private String Overview;
    private String Tagline;
    private String Status;
    private Genre[] Genres;
    private LocalDate Release;
    private long Budget;
    private long Revenue;
    private String[] Languages;
    private String OriginalLanguage;
    private double Runtime;
    private String Homepage;
    private boolean Adult;
    private boolean Video;
    private String Poster;
    private double VoteAverage;
    private int VoteCount;
    private String imdb_id;
    private double popularity;
    private MyArrayList<Company> productionCompanies;
    private MyArrayList<String> productionCountries;

    public MetaData(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster){
        Id = id;
        Title = title;
        OriginalTitle = originalTitle;
        Overview = overview;
        Tagline = tagline;
        Status = status;
        Genres = genres;
        Release = release;
        Budget = budget;
        Revenue = revenue;
        Languages = languages;
        OriginalLanguage = originalLanguage;
        Runtime = runtime;
        Homepage = homepage;
        Adult = adult;
        Video = video;
        Poster = poster;
        VoteAverage = 0;
        VoteCount = 0;
        productionCompanies = new MyArrayList<>();
        productionCountries = new MyArrayList<>();
    }

    public int getID(){
        return Id;
    }

    public String getTitle(){
        return Title;
    }

    public String getOriginalTitle(){
        return OriginalTitle;
    }

    public String getOverview(){
        return Overview;
    }

    public String getTagline(){
        return Tagline;
    }

    public String getStatus(){
        return Status;
    }

    public Genre[] getGenres(){
        return Genres;
    }

    public LocalDate getRelease(){
        return Release;
    }

    public long getBudget(){
        return Budget;
    }

    public long getRevenue(){
        return Revenue;
    }

    public String[] getLanguages(){
        return Languages;
    }

    public String getOriginalLanguage(){
        return OriginalLanguage;
    }

    public double getRuntime(){
        return Runtime;
    }

    public String getHomepage(){
        return Homepage;
    }

    public Boolean getAdult(){
        return Adult;
    }

    public Boolean getVideo(){
        return Video;
    }

    public String getPoster(){
        return Poster;
    }

    public double getVoteAverage(){
        return VoteAverage;
    }

    public int getVoteCount(){
        return VoteCount;
    }

    public String getIMDB(){
        return imdb_id;
    }

    public double getPopularity(){
        return popularity;
    }

    public MyArrayList<Company> getCompanies(){
        return productionCompanies;
    }

    public MyArrayList<String> getCountries(){
        return productionCountries;
    }

    public boolean setPopularity(double pop){
        popularity = pop;
        return true;
    }

    public boolean setIMDB(String id){
        imdb_id = id;
        return true;
    }

    public boolean setVoteAvg(double voteAverage){
        VoteAverage = voteAverage;
        return true;
    }

    public boolean setVoteCount(int voteCount){
        VoteCount = voteCount;
        return true;
    }

    public boolean addCompany(Company company){
        productionCompanies.add(company);
        return true;
    }

    public boolean addCountry(String country){
        productionCountries.add(country);
        return true;
    }
}
