package common.domain.validators;

import common.domain.Movie;
import common.domain.exceptions.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class MovieValidator implements Validator<Movie> {

    private final List<String> genres;
    {
        genres = new ArrayList<String>();
        genres.add("Thriller");
        genres.add("Drama");
        genres.add("Comedy");
        genres.add("SF");
        genres.add("Horror");
        genres.add("Misc");
    }

    /**
     * Validates an entity of type movie
     * Genres : Thriller, Drama, Comedy, SF, Horror, Misc
     * @param entity
     *        Should not have null title/genre, genre should be in Genresm the rating between 0 and 5.
     * @throws ValidatorException
     *          if the entity is not in a sound state
     */
    public void validate(Movie entity) throws ValidatorException{
        Boolean b = (entity.getTitle() == null || entity.getGenre() == null || entity.getId() < 0 || entity.getTitle().length() <=2 || !genres.contains(entity.getGenre()) || entity.getRating()<0 || entity.getRating()>5);
        Optional<Boolean> booleanOptional= Stream.of(b).filter(v -> !v).findAny();
        booleanOptional.orElseThrow(() -> new ValidatorException("Movie details not valid!"));

    }
}
