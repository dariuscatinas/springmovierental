package repository;

import common.domain.Movie;
import common.domain.exceptions.ValidatorException;
import common.domain.validators.MovieValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import pagination.Page;
import pagination.PageGenerator;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class JdbcMovieRepo implements PagingRepository<Integer, Movie> {
    @Autowired
    private JdbcOperations jdbcOperations;
    private MovieValidator movieValidator;
    public JdbcMovieRepo(JdbcOperations op, MovieValidator m) {
          this.jdbcOperations = op;
          this.movieValidator = m;
    }

    @Override
    public Page<Movie> findAll(PageGenerator pageGenerator) {
        return null;
    }

    @Override
    public Optional<Movie> findOne(Integer integer) {
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

    @Override
    public Iterable<Movie> findAll() {
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

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into movie (id, title, genre, rating) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getTitle(), entity.getGenre(), entity.getRating());
        return Optional.of(entity);
    }

    @Override
    public Optional<Movie> delete(Integer integer) {
        Optional<Movie> entity = findOne(integer);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        String sql = "delete from movie where id = (?)";
        jdbcOperations.update(sql, integer);
        return entity;
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ValidatorException {
        return null;
    }
}
