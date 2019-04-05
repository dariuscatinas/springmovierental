package repository;

import common.domain.BaseEntity;
import common.domain.Client;
import common.domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import repository.util.TriConsumer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


public class XMLRepo<ID,T extends BaseEntity<ID>> extends InMemoryRepo<ID,T> {
    private String filename;
    private Function<Element,T> fromXMLFactory;
    private Supplier<String> tagType;
    private TriConsumer<Document,Element,T> tToXML;

    /**
     * Constructor for an XML Repository
     * @param validator, the validator for the entity to store in the repository
     * @param filename, the filename to load data from
     * @param fromXMLFactory, a factory which transforms XML text into entities
     * @param tagType, the root tag of the xml
     * @param tToXML, a consumer which turns an entity into XML text
     */
    public XMLRepo(Validator<T> validator,String filename,Function<Element,T> fromXMLFactory,Supplier<String> tagType,
                   TriConsumer<Document,Element,T> tToXML){
        super(validator);
        this.filename=filename;
        this.fromXMLFactory=fromXMLFactory;
        this.tagType=tagType;
        this.tToXML=tToXML;
        loadData();
        saveFile();
    }

    /**
     * Loads the entities from the XML file
     */
    private void loadData(){


        DocumentBuilderFactory documentBuilderFactory =
                DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder =
                    documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(this.filename);
            Element root = document.getDocumentElement();

            NodeList nodes = root.getChildNodes();
            int len = nodes.getLength();
            for (int i = 0; i < len; i++) {
                Node tNode = nodes.item(i);
                if(tNode instanceof Element) {
                    T tElem = fromXMLFactory.apply((Element)tNode);
                    entities.put(tElem.getId(), tElem);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Saves an entity to an XML document
     * @param document, the XML document object
     * @param root, the root of the XML document
     * @param entity, the entity to save
     */
    private void saveEntity(Document document,Element root,T entity){

        Element  tElement= document.createElement(tagType.get());
        root.appendChild(tElement);
        tToXML.accept(document,tElement,entity);
    }

    /**
     * Saves entities to the XML file
     */
    private void saveFile(){

        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement(tagType.get() + "s");
            entities.values().forEach(e -> saveEntity(document,root,e));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(root), new StreamResult(new FileOutputStream(this.filename)));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Optional<T> save(T entity){
        Optional<T> superValue = super.save(entity);
        saveFile();
        return superValue;
    }

    @Override
    public Optional<T> delete(ID id){
        Optional<T> superValue = super.delete(id);
        saveFile();
        return superValue;
    }
    @Override
    public Optional<T> update(T entity){
        Optional<T> superValue = super.update(entity);
        saveFile();
        return superValue;
    }
}
