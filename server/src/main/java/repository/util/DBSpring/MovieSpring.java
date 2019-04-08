package repository.util.DBSpring;

import common.domain.Movie;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Optional;

public class MovieSpring {

    /**
     * Delegate to find all movies from the DB
     * @param jdbcOperations the DB operations
     * @return an {@code Iterable} containing all movies from the DB
     */
    public static Iterable<Movie> findAll(JdbcOperations jdbcOperations){
        String sql = "select * from movie";
        List<Movie> movies = jdbcOperations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            float rating = rs.getInt("rating");
            String genre = rs.getString("genre");

            return new Movie(id, title, genre, rating);
        });
        return movies;
    }

    /**
     * Delegate to find a movie in the DB
     * @param jdbcOperations the DB operations
     * @param integer the id of the movie to find
     * @return an {@code Optional} with the movie or empty
     */
    public static Optional<Movie> findOne(JdbcOperations jdbcOperations,int integer){
        String sql = "select * from movie where id = " + integer;
        Optional<Movie> opt = Optional.empty();
        List<Movie> movies = jdbcOperations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            float rating = rs.getInt("rating");
            String genre = rs.getString("genre");

            return new Movie(id, title, genre, rating);
        });
        if(movies.size() == 0){
            return Optional.empty();
        }
        return Optional.ofNullable(movies.get(0));
    }

    /**
     * Delegate to save a movie in the DB
     * @param jdbcOperations the DB operations
     * @param entity the movie to add to the DB
     * @return an {@code Optional} null if the movie was added or contains the movie otherwise
     */
    public static Optional<Movie> save(JdbcOperations jdbcOperations,Movie entity){
        if(findOne(jdbcOperations,entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into movie (id, title, genre, rating) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getTitle(), entity.getGenre(), entity.getRating());
        return Optional.of(entity);
    }

    /**
     * Delegate to delete a movie from the DB
     * @param jdbcOperations the DB operations
     * @param integer the id of the movie to delete
     * @return an {@code Optional} containing the deleted movie
     */
    public static Optional<Movie> delete(JdbcOperations jdbcOperations,int integer){
        Optional<Movie> entity = findOne(jdbcOperations,integer);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        String sql = "delete from movie where id = (?)";
        jdbcOperations.update(sql, integer);
        return entity;
    }

    /**
     * Delegate to update a movie in the DB
     * @param jdbcOperations the DB operations
     * @param movie the movie with the new info
     * @return an {@code Optional} with the updated movie
     */
    public static Optional<Movie> update(JdbcOperations jdbcOperations,Movie movie){
        if(!findOne(jdbcOperations,movie.getId()).isPresent())
            return Optional.of(movie);
        String sql= "update Movie set title=?, genre=?, rating=? where ID=?";
        if(jdbcOperations.update(sql,movie.getTitle(),movie.getGenre(),movie.getRating(),movie.getId())==1){
            return Optional.empty();
        }
        return Optional.of(movie);
    }

}
