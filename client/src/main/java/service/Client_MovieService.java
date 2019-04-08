package service;
import common.domain.Movie;
import common.domain.exceptions.ValidatorException;
import common.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Client_MovieService implements MovieService {

    @Autowired
    MovieService movieService;

    @Override
    public Optional<Movie> addMovie(Movie movie) throws ValidatorException {
        return movieService.addMovie(movie);
    }

    @Override
    public Optional<Movie> deleteMovie(int ID) throws ValidatorException {
        return movieService.deleteMovie(ID);
    }

    @Override
    public Set<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @Override
    public Set<Movie> filterCustom(Predicate<? super Movie> predicate) {
        return movieService.filterCustom(predicate);
    }

    @Override
    public Optional<Movie> update(Movie newMovie) {
        return movieService.update(newMovie);
    }

    @Override
    public Optional<Movie> findOne(int id) {
        return movieService.findOne(id);
    }

    @Override
    public List<Movie> findAllPaged() {
        return movieService.findAllPaged();
    }

    @Override
    public void setPageSize(int size) {
        movieService.setPageSize(size);
    }
}
