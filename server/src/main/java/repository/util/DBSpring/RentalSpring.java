package repository.util.DBSpring;

import common.domain.Rental;
import org.springframework.jdbc.core.JdbcOperations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentalSpring {

    public static Iterable<Rental> findAll(JdbcOperations jdbcOperations){
        String sql = "select * from rental";
        List<Rental> rentals = jdbcOperations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            Long cid = rs.getLong("clientID");
            Integer mid = rs.getInt("movieID");
            LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
            LocalDate endDate = LocalDate.parse(rs.getString("endDate"));

            return new Rental(id, cid, mid, startDate, endDate);
        });
        return rentals;
    }

    public static Optional<Rental> findOne(JdbcOperations jdbcOperations,int integer){
        String sql = "select * from rental where id = " + integer;
        Optional<Rental> opt = Optional.empty();
        List<Rental> rentals = jdbcOperations.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("id");
            Long cid = rs.getLong("clientID");
            Integer mid = rs.getInt("movieID");
            LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
            LocalDate endDate = LocalDate.parse(rs.getString("endDate"));

            return new Rental(id, cid, mid, startDate, endDate);
        });
        if(rentals.size() == 0){
            return Optional.empty();
        }
        return Optional.ofNullable(rentals.get(0));
    }

    public static Optional<Rental> save(JdbcOperations jdbcOperations,Rental entity){
        if(findOne(jdbcOperations,entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into rental (id, clientid, movieid, startdate, enddate) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getClientId(), entity.getMovieId(), entity.getStartDate().toString(),
                entity.getEndDate().toString());
        return Optional.of(entity);
    }

    public static Optional<Rental> delete(JdbcOperations jdbcOperations,int integer){
        Optional<Rental> entity = findOne(jdbcOperations,integer);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        String sql = "delete from rental where id = (?)";
        jdbcOperations.update(sql, integer);
        return entity;
    }

    public static Optional<Rental> update(JdbcOperations jdbcOperations,Rental rental){
        if(!findOne(jdbcOperations,rental.getId()).isPresent())
            return Optional.of(rental);
        String sql = "update Rental set clientId=?, movieId=?, startDate=?, endDate=?  where ID=?";
        if(jdbcOperations.update(sql,rental.getClientId(),rental.getMovieId(),rental.getStartDate().toString()
                ,rental.getEndDate().toString())==1){
            return Optional.empty();
        }
        return Optional.of(rental);
    }
}
