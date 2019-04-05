package common.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestMovie {

    private Movie movie;

    private static final int ID = 1;
    private static final String GENRE = "Misc";
    private static final String TITLE = "A space odyssey";
    private static final float RATING = (float)3.8;

    @Before
    public void setUp(){
        movie = new Movie(ID, TITLE, GENRE, RATING);
    }
    @After
    public void tearDown(){
        movie = null;
    }

    @Test
    public void testGetters(){
        assertTrue(movie.getId() == ID);
        assertEquals(movie.getGenre(), GENRE);
        assertEquals(movie.getTitle(), TITLE);
        assertTrue(movie.getRating() == RATING);
    }
    @Test
    public void testEquals(){
        assertEquals(movie, new Movie(1, "A space odyssey", "Misc", 4.4f));
        assertNotEquals(movie, new Movie(1,"Spartacus", "Misc", 3.8f));
        assertNotEquals(movie, new Movie(2, TITLE, GENRE, RATING));
    }
    @Test
    public void testID(){
        //The cast is performed due to ambiguous compile error between two signatures of assertEquals
        assertEquals("Movie ID not equal to expected value",(Integer)ID, movie.getId());
    }

    @Test
    public void testToString(){
        String repr = movie.toString();
        String[] tokenized = repr.split(",");
        assertEquals("String format unexpected", tokenized.length, 4);
        assertEquals("Unexpected string format", tokenized[1], " name: " + movie.getTitle());
    }

}
