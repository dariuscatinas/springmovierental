package common.service;
import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import common.domain.exceptions.RentalNotAdded;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import static common.domain.FilterPredicates.filterRD;
import common.domain.validators.RentalValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.InMemoryRepo;
import repository.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;
import static common.domain.CustomFilter.customFilter;

public class TestRentalService {

    private ClientValidator clientValidator;
    private MovieValidator movieValidator;
    private RentalValidator rentalValidator;

    private Repository<Long, Client> clientRepository;
    private Repository<Integer, Movie> movieRepository;
    private Repository<Integer, Rental> rentalRepository;

    private RentalService rentalService;

    private Client client1, client2;
    private Movie movie1, movie2;

    @Before
    public void setUp(){
        clientValidator = new ClientValidator();
        movieValidator = new MovieValidator();
        rentalValidator=new RentalValidator();
        clientRepository = new InMemoryRepo<>(clientValidator);
        movieRepository = new InMemoryRepo<>(movieValidator);
        client1 = new Client(192783L, "Raul", "brie4321@yahoo.com", 20);
        client2 = new Client(198783L, "Darius", "cdie2270@yahoo.com", 21);
        movie1 = new Movie(1, "Star Wars", "SF", 3.7f);
        movie2 = new Movie(2, "Coco", "Misc", 5.0f);
        clientRepository.save(client1);
        clientRepository.save(client2);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        rentalRepository=new InMemoryRepo<>(rentalValidator);
        rentalService=new RentalService(rentalRepository,movieRepository,clientRepository);

    }

    @After
    public void tearDown(){
        clientValidator = null;
        movieValidator = null;
        clientRepository = null;
        movieRepository= null;
        rentalService=null;
        client1=null;
        client2=null;
        movie1=null;
        movie2=null;
    }


    @Test
    public void testSave(){
        Optional<Rental> rSave1=rentalService.save(192783L,1, LocalDate.parse("2019-03-09"),7);
        rSave1.ifPresent(r->fail());
        Optional<Rental> rSave2=rentalService.save(192783L,1, LocalDate.parse("2019-03-09"),7);
        try {
            rSave2.orElseThrow(() -> new RentalNotAdded("Rental not added"));
            fail();
        }catch (RentalNotAdded rna){

        }
    }

    @Test
    public void testRentalGets(){

        rentalService.save(192783L,1, LocalDate.parse("2019-03-09"),7);
        rentalService.save(192783L,2, LocalDate.parse("2019-04-09"),5);
        rentalService.save(198783L,2, LocalDate.parse("2019-01-09"),8);
        rentalService.save(198783L,1, LocalDate.parse("2019-03-12"),3);

        Set<Rental> rentals=rentalService.getAllRentals();
        assertTrue(rentals.size()==4);
        Set<Rental> clientRentals=rentalService.getRentalsByClient(192783L);
        assertTrue(clientRentals.size()==clientRentals.stream().filter(r->r.getClientId()==192783L).collect(Collectors.toSet()).size());
        Set<Rental> movieRentals=rentalService.getRentalByMovie(2);
        assertTrue(movieRentals.size()==movieRentals.stream().filter(m->m.getMovieId()==2).collect(Collectors.toSet()).size());
        Set<Rental> filterRentals=customFilter(rentalService,filterRD(6,"<"));
        filterRentals.forEach(r->assertTrue(r.getDuration()<6));
        Map<Client,Set<Rental>> cMap=rentalService.getClientRental();
        cMap.values().forEach(v->assertTrue(v.size()==2));
        Map<Movie,Set<Rental>> mMap=rentalService.getMovieRental();
        cMap.values().forEach(v->assertTrue(v.size()==2));
    }

}
