package common.domain.exceptions;

import common.domain.Client;
import common.domain.Movie;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.InMemoryRepo;
import repository.Repository;

import java.util.Optional;

import static junit.framework.TestCase.fail;

public class TestExceptions {

    private ClientValidator clientValidator;
    private MovieValidator movieValidator;

    private Repository<Long, Client> clientRepository;
    private Repository<Integer, Movie> movieRepository;

    private Client client1, client2;
    private Movie movie1, movie2;

    @Before
    public void setUp(){
        clientValidator = new ClientValidator();
        movieValidator = new MovieValidator();
        clientRepository = new InMemoryRepo<>(clientValidator);
        movieRepository = new InMemoryRepo<>(movieValidator);
        client1 = new Client(192783L, "Raul", "brie4321@yahoo.com", 20);
        client2 = new Client(198783L, "Darius", "cdie2270@yahoo.com", 21);
        movie1 = new Movie(1, "Star Wars", "SF", 3.7f);
        movie2 = new Movie(2, "Coco", "Misc", 5.0f);
    }

    @After
    public void tearDown(){
        clientValidator = null;
        movieValidator = null;
        clientRepository = null;
        movieRepository= null;
    }

    @Test
    public void testAddExceptions(){
        Optional<Client> aClient1=clientRepository.save(client1);
        try {
            aClient1.orElseThrow(() -> new ClientAdded(""));
            fail();
        }catch(ClientAdded ca)
        {

        }
        Optional<Movie> mClient1=movieRepository.save(movie1);
        try {
            mClient1.orElseThrow(() -> new MovieAdded(""));
            fail();
        }catch(MovieAdded ma)
        {

        }
    }

    @Test
    public void testDeleteExceptions(){
        Optional<Client> aClient1=clientRepository.delete(5L);
        try {
            aClient1.orElseThrow(() -> new ClientNotFound(""));
            fail();
        }catch(ClientNotFound ca)
        {

        }
        Optional<Movie> mClient1=movieRepository.delete(99);
        try {
            mClient1.orElseThrow(() -> new MovieNotFound(""));
            fail();
        }catch(MovieNotFound ma)
        {

        }
    }
}
