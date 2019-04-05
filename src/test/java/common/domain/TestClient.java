package common.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class TestClient {

    private Client client;

    private static final long ID = 1L;
    private static final String NAME = "aName";
    private static final String EMAIL = "abc@scs";
    private static final int AGE = 22;

    @Before
    public void setUp(){
        client = new Client(ID, NAME, EMAIL, AGE);
    }
    @After
    public void tearDown(){
        client = null;
    }

    @Test
    public void testGetters(){
        assertTrue(client.getId() == ID);
        assertEquals(client.getName(), NAME);
        assertEquals(client.getEmail(),EMAIL);
        assertTrue(client.getAge()==22);
    }
    @Test
    public void testEquals(){
        assertEquals(client, new Client(1L,"aName","abc@scs",22));
        assertNotEquals(client, new Movie(1,"Spartacus", "Misc", 3.8f));
        assertNotEquals(client, new Client(2L, NAME, EMAIL, AGE));
    }

    @Test
    public void testToString(){
        String repr = client.toString();
        String[] tokenized = repr.split("\n");
        assertEquals("String format unexpected", tokenized.length, 4);
        assertEquals("Unexpected string format", tokenized[1],"Client name:"+client.getName());
    }

    @Test
    public void testSetters(){
        client.setName("another");
        client.setEmail("email");
        client.setAge(99);
        assertTrue(client.getAge()==99);
        assertTrue(client.getName()=="another");
        assertTrue(client.getEmail()=="email");
    }
}
