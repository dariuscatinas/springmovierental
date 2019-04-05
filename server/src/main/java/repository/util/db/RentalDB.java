package repository.util.db;

import common.domain.Rental;
import common.domain.exceptions.MovieRentalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalDB {

    /**
     * Builds a select statement string
     * @return the selec statement string
     */
    public static String getRentalSelect(){
        return "select * from Rental";
    }

    /**
     * Retrieves the rentals from a result set
     * @param resultSet the restult set from a select statement
     * @return the list of rentals
     */
    public static List<Rental> findAll(ResultSet resultSet)  {

        try {
            List<Rental> Rentals = new ArrayList<>();

            while (resultSet.next()) {
                int ID = resultSet.getInt("ID");
                long cId = resultSet.getLong("clientId");
                int mId = resultSet.getInt("movieId");
                LocalDate startDate = LocalDate.parse(resultSet.getString("startDate"));
                LocalDate endDate = LocalDate.parse(resultSet.getString("endDate"));
                Rentals.add(new Rental(ID, cId, mId, startDate, endDate));
            }

            return Rentals;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Prepares a statement to save a rental to the DB
     * @param conn the connection with the db
     * @param Rental the rental to save to the DB
     * @return a statement which will add a rental to the DB
     */
    public static PreparedStatement saveRental(Connection conn, Rental Rental) {

        try{
        String cmd="insert into Rental(ID, clientId, movieId, startDate, endDate) values(?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(cmd);
        statement.setInt(1, Rental.getId());
        statement.setLong(2, Rental.getClientId());
        statement.setInt(3,Rental.getMovieId());
        statement.setString(4, Rental.getStartDate().toString());
        statement.setString(5, Rental.getEndDate().toString());

        return statement;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Prepares a statement to update a rental in the DB
     * @param conn the connection with the db
     * @param Rental the rental to update in the DB
     * @return a statement which will update a rental in the DB
     */
    public static PreparedStatement updateRental(Connection conn,Rental Rental) {

        try {
            String cmd = "update Rental set clientId=?, movieId=?, startDate=?, endDate=?  where ID=?";
            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setInt(5, Rental.getId());
            statement.setLong(1, Rental.getClientId());
            statement.setInt(2, Rental.getMovieId());
            statement.setString(3, Rental.getStartDate().toString());
            statement.setString(4, Rental.getEndDate().toString());

            return statement;
        }catch (SQLException s){
        throw new MovieRentalException("DB Error");
    }
    }

    /**
     * Deletes a rental from the DB
     * @param conn the connection with the db
     * @param id the id of the rental to delete
     * @return the deleted rental or null if it does not exist
     */
    public static Rental deleteRental(Connection conn,Integer id) {

        String preCmd="select * from Rental where ID=?";
        try(PreparedStatement preStatement=conn.prepareStatement(preCmd)) {
            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {

                Rental r=new Rental(rs.getInt("ID"), rs.getLong("clientId"),
                        rs.getInt("movieId"), LocalDate.parse(rs.getString("startDate")),
                        LocalDate.parse(rs.getString("endDate")));
                String cmd = "delete from Rental where ID=?";
                PreparedStatement statement = conn.prepareStatement(cmd);
                statement.setInt(1, id);
                statement.executeUpdate();

                return r;
            }

            return null;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Finds a rental in the DB
     * @param conn the connection with the db
     * @param id the id of the rental to find
     * @return the rental with that id or null if it does not exist
     */
    public static Rental findOneRental(Connection conn, Integer id) {
        String cmd="select * from Rental where ID=?";
        try(PreparedStatement statement=conn.prepareStatement(cmd)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                return new Rental(rs.getInt("ID"), rs.getLong("clientId"),
                        rs.getInt("movieId"), LocalDate.parse(rs.getString("startDate")),
                        LocalDate.parse(rs.getString("endDate")));
            }
            return null;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }
}
