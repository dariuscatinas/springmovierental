package common.domain;


import java.io.Serializable;
import java.util.function.Predicate;

/**
 * A class containing static methods to build predicates for the filtering of entities
 */
public class FilterPredicates {

    /**
     * Constructs a filtering predicate for movies based on the rating
     * @param rating the rating of a Movie
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Movie> filterMR(float rating) {
        return (Predicate<Movie> & Serializable) movie -> movie.getRating() > rating;
    }

    /**
     * Constructs a filtering predicate for movies based on the genre
     * @param genre the Genre of the movies
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Movie> filterMG(String genre) {
        return (Predicate<Movie> & Serializable) movie -> movie.getGenre().equals(genre);
    }

    /**
     * Constructs a filtering predicate for clients based on the age
     * @param age
     *        the filtered age
     * @param sign
     *        a {@code String} representing either "<", or ">"
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Client> filterCA(int age, String sign) {
        switch (sign) {
            case "<":
                return (Predicate<Client> & Serializable)c -> c.getAge() < age;
            case ">":
                return (Predicate<Client> & Serializable)c -> c.getAge() > age;
            default:
                return (Predicate<Client> & Serializable)c->false;

        }
    }

    /**
     * Constructs a filtering predicate for rentals based on the duration
     * @param duration
     *        the filtered duration
     * @param sign
     *        a {@code String} representing either "<", or ">"
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Rental> filterRD(int duration, String sign) {
        switch (sign) {
            case "<":
                return (Predicate<Rental> & Serializable)r -> r.getDuration() < duration;
            case ">":
                return (Predicate<Rental> & Serializable)r -> r.getDuration() > duration;
            default:
                return (Predicate<Rental> & Serializable)r->false;
        }
    }

    /**
     * Constructs a filtering predicate for rentals based on the movie they are rented
     * @param mID the ID of the movie
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Rental> filterRM(int mID){
        return (Predicate<Rental> & Serializable)r -> r.getMovieId() == mID;
    }
    /**
     * Constructs a filtering predicate for rentals based on the client they are rented
     * @param cID the ID of the client
     * @return a {@code Predicate} to use in the filtering function taking into account the input parameter
     */
    public static Predicate<Rental> filterRC(long cID){
        return (Predicate<Rental> & Serializable)r -> r.getClientId() == cID;
    }
}
