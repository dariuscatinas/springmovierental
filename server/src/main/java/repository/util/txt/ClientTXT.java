package repository.util.txt;

import common.domain.Client;
import common.domain.Movie;

public class ClientTXT {

    public static String fromClient(Client c){
        return c.getId() + "," + c.getName() + "," + c.getEmail() + "," + c.getAge();
    }

    /**
     * Creates a client from a line
     * @param line represents the line to be parsed
     * @return a Client entity containing attributes from the line or null if cannot parse line properly
     */
    public static Client clientFromLine (String line){
        String[] tokens = line.split(",");
        long ID = -1;
        int age = -1;
        try{
            ID = Integer.parseInt(tokens[0]);
            age = Integer.parseInt(tokens[3]);

        }
        catch(NumberFormatException nex){
            return null;
        }
        return new Client(ID, tokens[1], tokens[2], age);
    }


}
