package repository;

import common.domain.Rental;
import common.domain.exceptions.ValidatorException;
import common.domain.validators.RentalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import pagination.Page;
import pagination.PageGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JdbcRentalRepo implements PagingRepository<Integer, Rental> {
    @Autowired
    private JdbcOperations jdbcOperations;
    private RentalValidator rentalValidator;
    @Autowired
    public JdbcRentalRepo(JdbcOperations op, RentalValidator validator) {
        this.jdbcOperations = op;
        this.rentalValidator = validator;
    }

    @Override
    public Page<Rental> findAll(PageGenerator pageGenerator) {
        return null;
    }

    @Override
    public Optional<Rental> findOne(Integer integer) {
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


    @Override
    public Iterable<Rental> findAll() {
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

    @Override
    public Optional<Rental> save(Rental entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into rental (id, clientid, movieid, startdate, enddate) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getClientId(), entity.getMovieId(), entity.getStartDate().toString(),
                entity.getEndDate().toString());
        return Optional.of(entity);
    }

    @Override
    public Optional<Rental> delete(Integer integer) {
        Optional<Rental> entity = findOne(integer);
        if(!entity.isPresent()){
            return Optional.empty();
        }
        String sql = "delete from rental where id = (?)";
        jdbcOperations.update(sql, integer);
        return entity;
    }

    @Override
    public Optional<Rental> update(Rental entity) throws ValidatorException {
        return Optional.empty();
    }
}
