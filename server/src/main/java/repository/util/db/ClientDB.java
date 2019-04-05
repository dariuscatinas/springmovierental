package repository.util.db;

import common.domain.Client;
import common.domain.exceptions.MovieRentalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDB {

    /**
     * The string to be used in the findAll statement
     * @return an {@code String} representing the sql statement to select all clients from the DB
     */
    public static String getClientSelect(){
        return "select * from Client";
    }

    /**
     * Retrieves the list of all the clients in the DB
     * @param resultSet the set of records in the DB resulted from a select statement
     * @return the list of all Clients
     */
    public static List<Client> findAll(ResultSet resultSet) {

        try {
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                long ID = resultSet.getLong("ID");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                clients.add(new Client(ID, name, email, age));
            }

            return clients;
        }catch (SQLException s){
            s.printStackTrace();
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Prepares the statement to add a client to the DB
     * @param conn the connection to the DB
     * @param client the client to add to the DB
     * @return the statement which when executed adds the client to the db
     */
    public static PreparedStatement saveClient(Connection conn,Client client) {

        try {
            String cmd = "insert into Client(ID, name, email, age) values(?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setLong(1, client.getId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getEmail());
            statement.setInt(4, client.getAge());

            return statement;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Prepares the statement to update a client from the DB
     * @param conn the connection with the DB
     * @param client the client to update the DB with
     * @return the statement which when executed will update the client in the DB
     */
    public static PreparedStatement updateClient(Connection conn,Client client) {
        try {
            String cmd = "update Client set name=?, email=?, age=? where ID=?";
            PreparedStatement statement = conn.prepareStatement(cmd);
            statement.setLong(4, client.getId());
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setInt(3, client.getAge());

            return statement;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }

    /**
     * Deletes a client from the DB and returns the deleted client
     * @param conn the connection with the DB
     * @param id the id of the client to delete
     * @return the deleted client or null if it does not exist
     */
    public static Client deleteClient(Connection conn,Long id) {


        String preCmd="select * from Client where ID=?";
        try(PreparedStatement preStatement=conn.prepareStatement(preCmd)) {
            preStatement.setLong(1, id);
            ResultSet rs = preStatement.executeQuery();
            if (rs.next()) {

                Client c=new Client(rs.getLong("ID"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age"));

                String cmd = "delete from Client where ID=?";
                PreparedStatement statement = conn.prepareStatement(cmd);
                statement.setLong(1, id);
                statement.executeUpdate();

                return c;
            }

            return null;
        }catch (SQLException s){
        throw new MovieRentalException("DB Error");
    }
    }

    /**
     * Finds and retrieves a client from the DB
     * @param conn the connection with the DB
     * @param id the id of the client to find
     * @return the client with that respective ID
     */
    public static Client findOneClient(Connection conn, Long id) {
        String cmd="select * from Client where ID=?";
        try(PreparedStatement statement=conn.prepareStatement(cmd)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                return new Client(rs.getLong("ID"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age"));
            }
            return null;
        }catch (SQLException s){
            throw new MovieRentalException("DB Error");
        }
    }
}
