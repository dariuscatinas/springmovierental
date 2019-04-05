package common.domain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestRental {

    private Rental rental1,rental2;
    private static final long clientID=1L;
    private static final int movieID=1;
    private static final LocalDate startDate=LocalDate.parse("2019-03-08");
    private static final LocalDate endDate=LocalDate.parse("2019-03-15");

    @Before
    public void setUp(){
        rental1 = new Rental(1,clientID,movieID,startDate,endDate);
        rental2= new Rental(2,clientID,movieID,startDate,endDate);
    }
    @After
    public void tearDown(){
        rental1=null;
        rental2=null;
    }

    @Test
    public void testGetters(){

        assertEquals(rental1.getClientId(),clientID);
        assertEquals(rental1.getMovieId(),movieID);
        assertEquals(rental1.getStartDate(),startDate);
        assertEquals(rental1.getEndDate(),endDate);
        assertEquals(rental1,rental2);
        assertNotEquals(rental1,null);
        assertEquals(rental1.getPrice(),70);
    }

    @Test
    public void testToString(){

        assertEquals(rental1.toString().split(" ")[1],rental2.toString().split(" ")[1]);
    }
}
