package repository.util.db;

import common.domain.Movie;
import common.domain.exceptions.MovieRentalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDB {

    /**
     * Builds a select string
     * @return a select string
     */
    public static String getMovieSelect(){
        return "select * from Movie";
    }

    /**
     * Finds all movies from a result set
     * @param resultSet the result set of a select statement execution
     * @return the list of movies from the result set
     */
    public static List<Movie> findAll(ResultSet resultSet) {

        List<Movie> Movies=new ArrayList<>();
        try {
            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                float rating = resultSet.getFloat("rating");
                Movies.add(new Movie(ID, title, genre, rating));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new MovieRentalException("DB Error!");
        }
        return Movies;
    }

    /**
     *Prepares a statement to save a movie to the DB
     * @param conn the connection with the db
     * @param Movie the movie to add to the dB
     * @return a statement which will add the movie to the DB
     */
    public static PreparedStatement saveMovie(Connection conn, Movie Movie) {

        try {
            String cmd = "insert into Movie(ID, title, genre, rating) values(?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(1, Movie.getId());
            statement.setString(2, Movie.getTitle());
            statement.setString(3, Movie.getGenre());
            statement.setFloat(4, Movie.getRating());
            return statement;
        }
        catch(SQLException sql){
            sql.printStackTrace();
            throw new MovieRentalException("DB Error!");
        }


    }

    /**
     * Prepares a statement to update a movie from the DB
     * @param conn the connection with the db
     * @param Movie the movie to update
     * @return the statement which will update a movie from the DB
     */
    public static PreparedStatement updateMovie(Connection conn,Movie Movie) {
        try {
            String cmd = "update Movie set title=?, genre=?, rating=? where ID=?";
            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(4, Movie.getId());
            statement.setString(1, Movie.getTitle());
            statement.setString(2, Movie.getGenre());
            statement.setFloat(3, Movie.getRating());

            return statement;
        }
        catch(SQLException ex){
            throw new MovieRentalException("...");
        }
    }

    /**
     * Deletes a movie from the db
     * @param conn the connection with the db
     * @param id the id of the movie to delete
     * @return the deleted movie or null if it does not exist
     */
    public static Movie deleteMovie(Connection conn,Integer id) {
        try {
            String preCmd = "select * from Movie where ID=?";
            try (PreparedStatement preStatement = conn.prepareStatement(preCmd)) {
                preStatement.setInt(1, id);
                ResultSet rs = preStatement.executeQuery();
                if (rs.next()) {

                    Movie m = new Movie(rs.getInt("ID"), rs.getString("title"),
                            rs.getString("genre"), rs.getFloat("rating"));
                    String cmd = "delete from Movie where ID=?";
                    PreparedStatement statement = conn.prepareStatement(cmd);
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    return m;
                }

                return null;
            }
        }
        catch(SQLException ex){
            throw new MovieRentalException("DB Error!");
        }
    }

    /**
     * Finds a movie with a given id
     * @param conn the connection with the db
     * @param id the id of the movie to find
     * @return the found movie or null if it does not exist
     */
    public static Movie findOneMovie(Connection conn, Integer id) {
        try {
            String cmd = "select * from Movie where ID=?";
            try (PreparedStatement statement = conn.prepareStatement(cmd)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    return new Movie(rs.getInt("ID"), rs.getString("title"),
                            rs.getString("genre"), rs.getFloat("rating"));
                }
                return null;
            }
        } catch (SQLException ex) {
            //ex.printStackTrace();
            throw new MovieRentalException("DB Error!");
        }
    }
}
