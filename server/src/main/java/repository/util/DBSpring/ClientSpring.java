package repository.util.DBSpring;

import common.domain.Client;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Optional;

public class ClientSpring {

    public static Iterable<Client> findAll(JdbcOperations jdbcOperations){

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

    public static Optional<Client> findOne(JdbcOperations jdbcOperations,Long aLong){
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

    public static Optional<Client> save(JdbcOperations jdbcOperations,Client entity) {
        if(findOne(jdbcOperations,entity.getId()).isPresent())
            return Optional.empty();
        String sql = "insert into client (id, name, email, age) values (?,?,?,?)";
        jdbcOperations.update(sql, entity.getId(), entity.getName(), entity.getEmail(), entity.getAge());
        return Optional.of(entity);
    }

    public static Optional<Client> delete(JdbcOperations jdbcOperations,Long aLong){
        Optional<Client> entity = findOne(jdbcOperations,aLong);
        if(!entity.isPresent()){
            return entity;
        }
        String sql = "delete from client where id = (?)";
        jdbcOperations.update(sql, aLong);
        return entity;
    }

    public static Optional<Client> update(JdbcOperations jdbcOperations,Client client){
        if(!findOne(jdbcOperations,client.getId()).isPresent())
            return Optional.of(client);
        String sql= "update Client set name=?, email=?, age=? where ID=?";
        if(jdbcOperations.update(sql,client.getName(),client.getEmail(),client.getAge(),client.getId())==1){
            return Optional.empty();
        }
        return Optional.of(client);
    }
}
