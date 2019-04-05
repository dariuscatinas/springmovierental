package common.domain;

import static org.junit.Assert.*;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import common.domain.validators.RentalValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static common.domain.FilterPredicates.*;
import repository.InMemoryRepo;
import repository.Repository;
import common.service.ClientService;
import common.service.MovieService;
import common.service.RentalService;

import java.time.LocalDate;
import java.util.Set;

import static common.domain.CustomFilter.customFilter;

public class TestFilters {

    private ClientValidator clientValidator;
    private MovieValidator movieValidator;
    private RentalValidator rentalValidator;

    private Repository<Long, Client> clientRepository;
    private Repository<Integer, Movie> movieRepository;
    private Repository<Integer, Rental> rentalRepository;

    private RentalService rentalService;
    private ClientService clientService;
    private MovieService movieService;

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
        rentalService.save(192783L,1, LocalDate.parse("2019-03-09"),7);
        rentalService.save(192783L,2, LocalDate.parse("2019-04-09"),5);
        rentalService.save(198783L,2, LocalDate.parse("2019-01-09"),8);
        rentalService.save(198783L,1, LocalDate.parse("2019-03-12"),3);
        movieService=new MovieService(movieRepository);
        clientService=new ClientService(clientRepository);
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
        movieService=null;
        clientService=null;
    }

    @Test
    public void testFilters(){
        Set<Client> filterClients=customFilter(clientService,filterCA(20,">"));
        filterClients.forEach(c->assertTrue(c.getAge()>20));
        Set<Client> filterClients2=customFilter(clientService,filterCA(21,"<"));
        filterClients2.forEach(c->assertTrue(c.getAge()<21));
        Set<Movie> filterMovies=customFilter(movieService,filterMG("Misc"));
        filterMovies.forEach(m->assertTrue(m.getGenre()=="Misc"));
        Set<Movie> filterMovies2=customFilter(movieService,filterMR(4));
        filterMovies2.forEach(m->assertTrue(m.getRating()>4));
        Set<Rental> filterRentalC=customFilter(rentalService,filterRC(192783L));
        filterRentalC.forEach(r->assertTrue(r.getClientId()==192783L));
        Set<Rental> filterRentalM=customFilter(rentalService,filterRM(2));
        filterRentalM.forEach(m->assertTrue(m.getMovieId()==2));
        Set<Rental> filterRentalD=customFilter(rentalService,filterRD(6,">"));
        filterRentalD.forEach(r->assertTrue(r.getDuration()>6));
        Set<Client> nC=customFilter(clientService,filterCA(20,"."));
        assertTrue(nC.isEmpty());
        Set<Rental> rD=customFilter(rentalService,filterRD(6,"."));
        assertTrue(rD.isEmpty());

    }
}
