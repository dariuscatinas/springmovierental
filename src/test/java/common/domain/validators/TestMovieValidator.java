package common.domain.validators;

import common.domain.Movie;
import common.domain.exceptions.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMovieValidator {
    private MovieValidator validator;

    private Movie okMovie1, okMovie2, failMovie1, failMovie2, failMovie3;

    @Before
    public void setUp(){
        validator = new MovieValidator();
        okMovie1 = new Movie(1,"Coco", "Thriller", (float)4.6);
        okMovie2 = new Movie(2, "the Godfather", "Thriller", 5);
        failMovie1 = new Movie(3, null, "Misc", (float)3.2);
        failMovie2 = new Movie(4, "LOTR", "Legend", (float)4.9);
        failMovie3 = new Movie(5, "Gogo", "SF", (float)5.3);
    }
    @After
    public void tearDown(){
        validator = null;
        okMovie1 = null;
        okMovie2 = null;
        failMovie1 = null;
        failMovie2 = null;
        failMovie3 = null;
    }
    @Test
    public void testValidate(){

        try{
            validator.validate(okMovie1);
            validator.validate(okMovie2);
        }
        catch (Exception ex){
            fail();
        }
        try{
            validator.validate(failMovie1);
            fail();
        }
        catch (ValidatorException ex){
            try{
                validator.validate(failMovie2);
                fail();
            }
            catch (ValidatorException ex2){
                try{
                    validator.validate(failMovie3);
                    fail();
                }
                catch (ValidatorException ex3){
                    assertEquals(ex3.getMessage(),ex2.getMessage());
                }
            }
        }

    }
}
