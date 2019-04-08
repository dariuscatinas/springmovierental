package repository;

import common.domain.Client;
import common.domain.exceptions.ValidatorException;
import common.domain.validators.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import pagination.Page;
import pagination.PageGenerator;
import repository.PagingRepository;

import java.util.List;
import java.util.Optional;

@Deprecated
public class JdbcClientRepo implements PagingRepository<Long, Client> {
    @Autowired
    private JdbcOperations jdbcOperations;

    private ClientValidator clientValidator;
    public JdbcClientRepo(JdbcOperations op, ClientValidator clientValidator){
        this.jdbcOperations = op;
        this.clientValidator = clientValidator;
    }
    public void setClientValidator(ClientValidator validator){
        this.clientValidator = clientValidator;
    }

    @Override
    public Page<Client> findAll(PageGenerator pageGenerator) {
        return null;
    }

    @Override
    public Optional<Client> findOne(Long aLong) {
        String sql = "select * from client where id = " + aLong;
        Optional<Client> opt = Optional.empty();
        List<Client> clients = jdbcOperations.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            int age  = rs.getInt("age");

            return new Client(id, name, email, age);
        });
        if(clients.size() == 0){
            return Optional.empty();
        }
        return Optional.ofNullable(clients.get(0));
    }
    

    @Override
    public Iterable<Client> findAll() {
        String sql = "select * from client";
        List<Client> clients = jdbcOperations.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            int age  = rs.getInt("age");

            return new Client(id, name, email, age);
        });
        return clients;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        if(findOne(entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into client (id, name, email, age) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getEmail(), entity.getAge());
        return Optional.of(entity);
    }

    @Override
    public Optional<Client> delete(Long aLong) {
        Optional<Client> entity = findOne(aLong);
        if(!entity.isPresent()){
            return entity;
        }
        String sql = "delete from client where id = (?)";
        jdbcOperations.update(sql, aLong);
        return entity;
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        return Optional.empty();
    }
}
