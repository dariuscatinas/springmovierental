package repository.util.xml;

import common.domain.Rental;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static repository.util.xml.HelperXML.*;
import java.time.LocalDate;

public class RentalXML {

    /**
     * Saves a client into XML format
     * @param document, the XML document object
     * @param rentalElement, the root of the rental element in XML
     * @param rental, the Rental to save
     */
    public static void rentalToXML(Document document, Element rentalElement, Rental rental){
        appendChildWithText(document, rentalElement, "id", String.valueOf(rental.getId()));
        appendChildWithText(document, rentalElement, "cId", String.valueOf(rental.getClientId()));
        appendChildWithText(document, rentalElement, "mId",String.valueOf(rental.getMovieId()));
        appendChildWithText(document, rentalElement, "startDate", rental.getStartDate().toString());
        appendChildWithText(document, rentalElement, "endDate", rental.getEndDate().toString());
    }
    /**
     * Reads a Rental from XML
     * @param rentalElement, the element to read the Rental from
     * @return the Rental
     */
    public static Rental rentalFromXML(Element rentalElement){
        return new Rental(Integer.parseInt(getTextFromTagName(rentalElement,"id")),
                Long.parseLong(getTextFromTagName(rentalElement,"cId")),Integer.parseInt(getTextFromTagName(rentalElement,"mId")),
                LocalDate.parse(getTextFromTagName(rentalElement,"startDate")),
                LocalDate.parse(getTextFromTagName(rentalElement,"endDate")));
    }


    /**
     * Function to determine the tag of the xml entity
     * @return the Rental tag
     */
    public static String getRentalTag(){
        return "rental";
    }
}
