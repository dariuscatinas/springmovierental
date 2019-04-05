package repository;

import common.domain.Client;
import common.domain.Movie;
import common.domain.validators.ClientValidator;
import common.domain.validators.MovieValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.util.txt.MovieTXT;
import repository.util.xml.ClientXML;
import repository.util.xml.MovieXML;

import static org.junit.Assert.assertTrue;

public class TestXMLRepo {
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
        clientRepository = new XMLRepo<>(clientValidator,"./src/test/resources/clients.xml",
                ClientXML::clientFromXML, ClientXML::getClientTag, ClientXML::clientToXML);
        movieRepository = new XMLRepo<>(movieValidator,"./src/test/resources/movies.xml",
                MovieXML::movieFromXML,MovieXML::getMovieTag,MovieXML::movieToXML);
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
    public void testLoad(){
        assertTrue(clientRepository.findOne(192783L).isPresent());
        assertTrue(clientRepository.findOne(198783L).isPresent());
    }

    @Test
    public void testSaveFile(){
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        FileRepo<Integer,Movie> mRepo=new FileRepo<Integer,Movie>(movieValidator,"./src/test/resources/movies.txt",
                MovieTXT::movieFromLine, MovieTXT::fromMovie);
        assertTrue(movieRepository.findOne(1).isPresent());
        assertTrue(movieRepository.findOne(2).isPresent());
    }
}
