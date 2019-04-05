package repository;
import common.domain.Client;
import common.domain.Movie;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import common.domain.exceptions.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static junit.framework.TestCase.*;

public class TestInMemoryRepo {

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
    public void testFindOne(){
        movieRepository.save(movie1);
        Optional<Movie> movieOptional = movieRepository.findOne(movie1.getId());
        assertTrue(movieOptional.isPresent());
        assertEquals(movieOptional.get(), movie1);
        clientRepository.save(client1);
        Optional<Client> clientOptional = clientRepository.findOne(client1.getId());
        assertTrue(clientOptional.isPresent());
        assertEquals(clientOptional.get().getName(), client1.getName());
        movieOptional = movieRepository.findOne(32);
        assertFalse(movieOptional.isPresent());
    }

    @Test
    public void testFindAll(){
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        clientRepository.save(client1);

        movieRepository.findAll().forEach( m -> {
            assertTrue(m.equals(movie1) || m.equals(movie2));
        });
        assertTrue(StreamSupport.stream(movieRepository.findAll().spliterator(), false).anyMatch(m -> m.equals(movie1)));
        assertTrue(StreamSupport.stream(clientRepository.findAll().spliterator(), false).anyMatch(c -> c.getName().equals(client1.getName())));
    }

    @Test
    public void testUpdate(){
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        Movie newMovie=new Movie(movie2.getId(),"Another",movie2.getGenre(),movie2.getRating());
        movieRepository.update(newMovie);
        assertTrue(movieRepository.findOne(newMovie.getId()).get().getTitle()=="Another");
    }

    @Test
    public void testDelete(){
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        clientRepository.save(client1);
        Optional<Movie> movieOptional = movieRepository.delete(movie1.getId());
        assertTrue(movieOptional.isPresent());
        assertEquals(movieOptional.get(), movie1);
        movieOptional = movieRepository.delete(movie1.getId());
        assertFalse(movieOptional.isPresent());
        Optional<Client> clientOptional = clientRepository.delete(client1.getId());
        assertTrue(clientOptional.isPresent());

    }

    @Test
    public void testSave(){
        try{
            assertFalse(movieRepository.save(movie1).isPresent());
            Optional<Movie> optionalMovie =  movieRepository.save(movie2);
            assertFalse(optionalMovie.isPresent());
            Optional <Client> optionalClient = clientRepository.save(client1);
            assertFalse(optionalClient.isPresent());
            optionalMovie = movieRepository.save(movie1);
            assertTrue(optionalMovie.isPresent());
            assertEquals(optionalMovie.get(), movie1);

        }
        catch(Exception ex){
            fail();
        }

        try{
            movieRepository.save(new Movie(1, null, "SF", 2.2f));
            fail();
        }
        catch(ValidatorException ex){
            try{
                clientRepository.save(new Client(null, "Client1", "whatever@yahoo.com", 3));
                fail();
            }
            catch (ValidatorException ex2){
                assertEquals(ex2.getMessage(), "Client details not valid!");

            }
        }

    }

    @Test
    public void testInvalidArgument(){
        try
        {
            movieRepository.save(null);
        }
        catch(IllegalArgumentException ex)
        {
            assertEquals(ex.getMessage(),"Argument cannot be null!");
        }

        try
        {
            movieRepository.findOne(null);
        }
        catch(IllegalArgumentException ex)
        {
            assertEquals(ex.getMessage(),"Argument cannot be null");
        }
        try
        {
            movieRepository.delete(null);
        }
        catch(IllegalArgumentException ex)
        {
            assertEquals(ex.getMessage(),"Argument cannot be null");
        }
        try
        {
            movieRepository.update(null);
        }
        catch(IllegalArgumentException ex)
        {
            assertEquals(ex.getMessage(),"Argument cannot be null");
        }
    }

}
