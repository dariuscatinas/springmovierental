package repository.util.xml;

import common.domain.Client;
import common.domain.Movie;
import common.domain.Rental;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HelperXML {

    /**
     * Helper function which adds to a parent node child elements
     * @param document, the XML document object
     * @param parent, the parent node to add a child element to
     * @param tagName, the name of the child tag
     * @param textContent, the content of the child tag
     */
    public static void appendChildWithText(Document document,
                                            Node parent, String tagName, String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    /**
     * Helper function which gets the text from a tag of an element
     * @param element, the element which contains the tag
     * @param tagName, the name of the tag to retrieve the text from
     * @return the text of the tag of the element with the respective tag name
     */
    public static String getTextFromTagName(Element element, String tagName) {
        NodeList elements = element.getElementsByTagName(tagName);
        Node node = elements.item(0);
        return node.getTextContent();
    }








}
