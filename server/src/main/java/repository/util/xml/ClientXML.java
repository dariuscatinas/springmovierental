package repository.util.xml;
import static repository.util.xml.HelperXML.*;
import common.domain.Client;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ClientXML {

    /**
     * Saves a client into XML format
     * @param document, the XML document object
     * @param clientElement, the root of the client element in XML
     * @param client, the Client to save
     */
    public static void clientToXML(Document document, Element clientElement, Client client){
        appendChildWithText(document, clientElement, "id", String.valueOf(client.getId()));
        appendChildWithText(document, clientElement, "name", client.getName());
        appendChildWithText(document, clientElement, "email",client.getEmail());
        appendChildWithText(document, clientElement, "age", String.valueOf(client.getAge()));
    }

    /**
     * Reads a Client from XML
     * @param clientElement, the element to read the Client from
     * @return the Client
     */
    public static Client clientFromXML(Element clientElement){

        return new Client(Long.parseLong(getTextFromTagName(clientElement,"id")),getTextFromTagName(clientElement,"name"),
                getTextFromTagName(clientElement,"email"),Integer.parseInt(getTextFromTagName(clientElement,"age")));
    }


    /**
     * Function to determine the tag of the xml entity
     * @return the Client tag
     */
    public static String getClientTag(){
        return "client";
    }
}
