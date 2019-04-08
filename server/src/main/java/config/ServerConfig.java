package config;

import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import common.domain.validators.RentalValidator;
import common.service.ClientService;
import common.service.MovieService;
import common.service.RentalService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.*;
import repository.util.DBSpring.ClientSpring;
import repository.util.DBSpring.MovieSpring;
import repository.util.DBSpring.RentalSpring;
import repository.util.db.ClientDB;
import repository.util.db.MovieDB;
import repository.util.db.RentalDB;
import service.Server_ClientService;
import service.Server_MovieService;
import service.Server_RentalService;

import javax.sql.DataSource;


@Configuration
public class ServerConfig {

    @Bean
    ClientValidator clientValidator(){
        return new ClientValidator();
    }

    @Bean
    MovieValidator movieValidator(){
        return new MovieValidator();
    }

    @Bean
    RentalValidator rentalValidator(){
        return new RentalValidator();
    }

    @Bean
    Repository<Long, Client> clientRepository(){
        return new DBSpring<>(ClientSpring::findAll,ClientSpring::findOne
                ,ClientSpring::save,ClientSpring::delete,ClientSpring::update,clientValidator());
    }

    @Bean
    Repository<Integer, Movie> movieRepository(){
        return new DBSpring<>(MovieSpring::findAll,MovieSpring::findOne,
                MovieSpring::save,MovieSpring::delete,MovieSpring::update,movieValidator());
    }

    @Bean
    Repository<Integer, Rental> rentalRepository()
    {
        return new DBSpring<>(RentalSpring::findAll,RentalSpring::findOne,RentalSpring::save,
                RentalSpring::delete,RentalSpring::update, rentalValidator());
    }

    @Bean
    RmiServiceExporter rmiServiceExporterMovie() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("MovieService");
        exporter.setServiceInterface(MovieService.class);
        exporter.setService(movieService());
        return exporter;
    }

    @Bean
    MovieService movieService() {
        return new Server_MovieService(movieRepository());
    }

    @Bean
    RmiServiceExporter rmiServiceExporterClient() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("ClientService");
        exporter.setServiceInterface(ClientService.class);
        exporter.setService(clientService());
//        System.out.println("rmi client");
        return exporter;
    }

    @Bean
    ClientService clientService() {
        return new Server_ClientService(clientRepository());
    }

    @Bean JdbcOperations createJdbc() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }


    @Bean
    DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        //TODO use env props (or property files)
        basicDataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        basicDataSource.setUsername("postgres");
        basicDataSource.setPassword("postgres");
        basicDataSource.setInitialSize(2);

        return basicDataSource;
    }


    @Bean
    RmiServiceExporter rmiServiceExporterRental() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("RentalService");
        exporter.setServiceInterface(RentalService.class);
        exporter.setService(rentalService());
        return exporter;
    }

    @Bean
    RentalService rentalService() {
        return new Server_RentalService(rentalRepository(),movieRepository(),clientRepository());
    }

}
