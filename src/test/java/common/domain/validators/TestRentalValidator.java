package common.domain.validators;

import common.domain.Rental;
import common.domain.exceptions.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.fail;

public class TestRentalValidator {

    private Rental okRental1,okRental2,failRental1,failRental2;
    private Validator<Rental> validator=new RentalValidator();


    @Before
    public void setUp(){
        okRental1=new Rental(1,1L,2, LocalDate.parse("1999-09-09")
                ,LocalDate.parse("1999-09-10"));
        okRental2=new Rental(2,2L,3, LocalDate.parse("2010-09-09")
                ,LocalDate.parse("2011-09-10"));
        failRental1=new Rental(2,2L,3, LocalDate.parse("2010-09-09")
                ,LocalDate.parse("2010-09-08"));
        failRental2=new Rental(2,2L,3, LocalDate.parse("2010-09-09")
                ,LocalDate.parse("2009-09-10"));

    }

    @After
    public void tearDown(){
        okRental1=null;
        okRental2=null;
        failRental1=null;
        failRental2=null;
    }

    @Test
    public void testValidator(){
        try{
            validator.validate(okRental1);
        }catch (ValidatorException ve)
        {
            fail();
        }
        try{
            validator.validate(okRental2);
        }catch (ValidatorException ve)
        {
            fail();
        }
        try{
            validator.validate(failRental1);
            fail();
        }catch (ValidatorException ve)
        {

        }
        try{
            validator.validate(failRental2);
            fail();
        }catch (ValidatorException ve)
        {

        }
    }
}
