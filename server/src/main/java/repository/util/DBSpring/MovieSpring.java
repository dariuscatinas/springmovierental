package repository.util.DBSpring;

import common.domain.Movie;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Optional;

public class MovieSpring {

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

    public static Optional<Movie> save(JdbcOperations jdbcOperations,Movie entity){
        if(findOne(jdbcOperations,entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into movie (id, title, genre, rating) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getTitle(), entity.getGenre(), entity.getRating());
        return Optional.of(entity);
    }

    public static Optional<Movie> delete(JdbcOperations jdbcOperations,int integer){
        Optional<Movie> entity = findOne(jdbcOperations,integer);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        String sql = "delete from movie where id = (?)";
        jdbcOperations.update(sql, integer);
        return entity;
    }

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
