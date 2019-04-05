package repository.util.xml;
import static repository.util.xml.HelperXML.*;
import common.domain.Movie;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MovieXML {

    /**
     * Saves a client into XML format
     * @param document, the XML document object
     * @param movieElement, the root of the movie element in XML
     * @param movie, the Movie to save
     */
    public static void movieToXML(Document document, Element movieElement, Movie movie){
        appendChildWithText(document, movieElement, "id", String.valueOf(movie.getId()));
        appendChildWithText(document, movieElement, "title", movie.getTitle());
        appendChildWithText(document, movieElement, "genre",movie.getGenre());
        appendChildWithText(document, movieElement, "rating", String.valueOf(movie.getRating()));
    }

    /**
     * Reads a Movie from XML
     * @param movieElement, the element to read the Movie from
     * @return the Movie
     */
    public static Movie movieFromXML(Element movieElement){
        return new Movie(Integer.parseInt(getTextFromTagName(movieElement,"id")),getTextFromTagName(movieElement,"title"),
                getTextFromTagName(movieElement,"genre"),Float.parseFloat(getTextFromTagName(movieElement,"rating")));
    }


    /**
     * Function to determine the tag of the xml entity
     * @return the Movie tag
     */
    public static String getMovieTag(){
        return "movie";
    }
}
