package repository.util.txt;

import common.domain.Client;
import common.domain.Movie;

public class MovieTXT {



    public static String fromMovie(Movie m){

        return m.getId() + "," +  m.getTitle() + "," + m.getGenre() +"," +  m.getRating();
    }
    /**
     * Creates a movie from a line
     * @param line represents the line to be parsed
     * @return a Movie entity containing attributes from the line or null if cannot parse line properly
     */
    public static Movie movieFromLine(String line){
        String[] tokens = line.split(",");
        int ID = -1;
        float rating = -1f;
        try{
            ID = Integer.parseInt(tokens[0]);
            rating = Float.parseFloat(tokens[3]);

        }
        catch(NumberFormatException nex){
            return null;
        }
        return new Movie(ID, tokens[1], tokens[2], rating);
    }


}
