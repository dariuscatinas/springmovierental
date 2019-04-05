package common.domain.validators;

import common.domain.Client;
import common.domain.exceptions.ValidatorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class TestClientValidator {

    private ClientValidator validator;
    private Client okClient1, okClient2, failClient1, failClient2, failClient3, failClient4;

    @Before
    public void setUp(){
        validator=new ClientValidator();
        okClient1=new Client(123L,"raul","yahoo@",23);
        okClient2=new Client(456L,"darius","google@",21);
        failClient1=new Client(null,"paul","mail",11);
        failClient2=new Client(123L,null,"mail",11);
        failClient3=new Client(213L,"paul",null,11);
        failClient4=new Client(213L,"paul","mail",0);
    }

    @After
    public void tearDown(){
        validator=null;
        okClient1=null;
        okClient2=null;
        failClient4=null;
        failClient3=null;
        failClient2=null;
        failClient1=null;
    }

    @Test
    public void testValidare(){
        try{
            validator.validate(okClient1);
        }
        catch (ValidatorException ve){
            fail();
        }
        try{
            validator.validate(okClient2);
        }
        catch (ValidatorException ve){
            fail();
        }
        try{
            validator.validate(failClient1);
            fail();
        }
        catch (ValidatorException ve){

        }
        try{
            validator.validate(failClient2);
            fail();
        }
        catch (ValidatorException ve){

        }
        try{
            validator.validate(failClient3);
            fail();
        }
        catch (ValidatorException ve){

        }
        try{
            validator.validate(failClient4);
            fail();
        }
        catch (ValidatorException ve){

        }
    }


}
