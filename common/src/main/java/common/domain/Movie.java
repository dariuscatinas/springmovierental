package common.domain;

public class Movie extends BaseEntity<Integer> {

    private String title;
    private String genre;
    private float rating;

    public Movie(int ID, String title, String genre, float rating){
        setId(ID);
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public Movie() {
    }

    public String getTitle(){
        return title;
    }
    public String getGenre(){
        return genre;
    }
    public float getRating(){
        return rating;
    }
    public String toString(){
        return "Movie ID: " + getId() + ", name: " + title +", Genre: " + genre + ", Rating: " + rating;
    }

    /**
     * Identifies if two movies are logically equivalent
     * @param obj an {@code Object} which should represent a movie
     * @return a boolean, true if obj is a movie and the title, name and ID are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie){
            Movie temp = (Movie) obj;
            if(temp.genre.equals(genre) && temp.title.equals(title) && temp.getId().equals(getId()))
                return true;
        }
        return false;
    }
    
}
