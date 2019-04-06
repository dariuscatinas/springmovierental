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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.DBGenericRepo;
import repository.Repository;
import repository.util.db.ClientDB;
import repository.util.db.MovieDB;
import repository.util.db.RentalDB;
import service.Server_ClientService;
import service.Server_MovieService;
import service.Server_RentalService;


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
        return new DBGenericRepo<>(ClientDB::getClientSelect,ClientDB::findAll,
                ClientDB::saveClient,ClientDB::updateClient,ClientDB::findOneClient,ClientDB::deleteClient,clientValidator());
    }

    @Bean
    Repository<Integer, Movie> movieRepository(){
        return new DBGenericRepo<>(MovieDB::getMovieSelect,MovieDB::findAll,
                MovieDB::saveMovie,MovieDB::updateMovie,MovieDB::findOneMovie,MovieDB::deleteMovie,movieValidator());
    }

    @Bean
    Repository<Integer, Rental> rentalRepository(){
        return new DBGenericRepo<>(RentalDB::getRentalSelect,RentalDB::findAll,
                RentalDB::saveRental,RentalDB::updateRental,RentalDB::findOneRental,RentalDB::deleteRental,rentalValidator());
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
