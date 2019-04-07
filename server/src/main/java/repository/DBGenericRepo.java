package repository;

import common.domain.BaseEntity;
import common.domain.Movie;
import common.domain.exceptions.MovieRentalException;
import common.domain.validators.Validator;
import pagination.Page;
import pagination.PageGenerator;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DBGenericRepo<ID, T extends BaseEntity<ID>> implements PagingRepository<ID, T> {

    private Supplier<String> selectSupplier;
    private Function<ResultSet, List<T>> allCreator;
    private BiFunction<Connection, T, PreparedStatement> addHandler;
    private BiFunction<Connection, T, PreparedStatement> updateHandler;
    private BiFunction<Connection,ID,  T> findOneHandler;
    private BiFunction<Connection, ID, T> deleteHandler;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345678";
    private static final String URL = "jdbc:postgresql://localhost:5432/movierental";
    private Validator<T> validator;

    public DBGenericRepo(Supplier<String> selectSupplier, Function<ResultSet, List<T>> allCreator,
                         BiFunction<Connection, T, PreparedStatement> addHandler,
                         BiFunction<Connection, T, PreparedStatement> updateHandler,
                         BiFunction<Connection, ID, T> findOneHandler, BiFunction<Connection, ID, T> deleteHandler,
                         Validator<T> validator) {
        this.selectSupplier = selectSupplier;
        this.allCreator = allCreator;
        this.addHandler = addHandler;
        this.updateHandler = updateHandler;
        this.findOneHandler = findOneHandler;
        this.deleteHandler = deleteHandler;
        this.validator = validator;
    }


    public Iterable<T> findAll(){
        String cmd = selectSupplier.get();
        try(
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(cmd);
                ResultSet resultSet = preparedStatement.executeQuery();
                )
        {
         return allCreator.apply(resultSet);
        }
        catch(SQLException sql){
            sql.printStackTrace();
            throw new MovieRentalException("Cannot connect to db");
        }
    }
    public Optional<T> save(T entity){
        validator.validate(entity);
        try(
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement cmd = addHandler.apply(conn, entity);


        ) {
            int res = cmd.executeUpdate();
            if(res == 1){
                return Optional.empty();
            }
            return Optional.ofNullable(entity);
        }
        catch(SQLException sqlx){
            throw new MovieRentalException("Database inconsistent");
        }
    }
    @Override
    public Optional<T> update(T entity){
        validator.validate(entity);
        try(
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        ) {
            PreparedStatement exe = updateHandler.apply(conn, entity);
            int res = exe.executeUpdate();
            if(res == 1){
                return Optional.empty();
            }
            return Optional.ofNullable(entity);
        }
        catch(SQLException sqlx){
            throw new MovieRentalException("Database inconsistency while update");
        }

    }
    @Override
    public Optional<T> delete(ID id){
        try(
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        ) {
            return Optional.ofNullable(deleteHandler.apply(conn, id));
        }
        catch(SQLException sqlx){
            throw new MovieRentalException("Database inconsistency while update");
        }
    }
    @Override
    public Optional<T> findOne(ID id) {
        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        ){
                return Optional.ofNullable(findOneHandler.apply(conn, id));
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new MovieRentalException("DB connection inconsistent");
        }
    }
    @Override
    public Page<T> findAll(PageGenerator pageGenerator){

        int baseL = pageGenerator.getCurrentPage()* pageGenerator.getPageSize();
        PageGenerator next = new PageGenerator(pageGenerator.getCurrentPage() + 1, pageGenerator.getPageSize());
        List<T> entities = StreamSupport.stream(findAll().spliterator(), false).skip(baseL).limit(pageGenerator.getPageSize()).collect(Collectors.toList());
        return new Page<>(entities, next);
    }
}
