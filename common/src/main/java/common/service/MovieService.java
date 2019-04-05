package common.service;

import common.domain.Movie;
import common.domain.exceptions.ValidatorException;
import common.message.utils.MockPredicate;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface MovieService {

    /**
     * Adds a {@code Movie} to the corresponding repository.
     * @param movie
     *              must not be null/ in a sound state
     * @return a boolean, true if successfully added a movie or false otherwise
     * @throws ValidatorException
     *          if the movie is not in a sound state
     */
    public Optional<Movie> addMovie(Movie movie) throws ValidatorException;

    /**
     * Deletes a {@code Movie} from the corresponding repository.
     * @param ID
     *          integer, the ID of the movie to delete
     * @return a boolean, true if successfully deleted the movie or false otherwise.
     * @throws ValidatorException
     *          to comply with the signature of the repository.
     */
    public Optional<Movie> deleteMovie(int ID) throws ValidatorException;

    /**
     *
     * @return an {@code Set<Movie>} of the entire collection of movies.
     */
    public Set<Movie> getAllMovies();
    /**
     * filter the movies based on a predice
     * @param predicate a {@code Predicate} predicate which takes in a Movie and returns true if the Client matches the conditions
     * @return A set of movies, which match the predicate
     */
    public Set<Movie> filterCustom(Predicate<? super Movie> predicate);
    /**
     * Updates a movie information
     * @param newMovie the movie with the new information
     * @return an {@code Optional} which contains the previous movie information
     */
    public Optional<Movie> update(Movie newMovie);

    /**
     * Retrieves a movie by its id
     * @param id, the movie id
     * @return an {@code Optional} containing the movie with the given id or null if it does not exist
     */
    public Optional<Movie> findOne(int id);

    public Stream<Movie> findAllPaged();

    public void setPageSize(int size);
}
