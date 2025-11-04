package dompsd1t81015;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DomReadNeptunkod {
    public static void main(String[] args) {
        try {
            // XML dokumentum beolvasása
            File xmlFile = new File("PSD1T8_orarend.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            
            // Normalizálás a konzisztens eredményekért
            document.getDocumentElement().normalize();
            
            System.out.println("DOM fa szerkezete - teljes tartalom:");
            System.out.println("=====================================");
            
            // Rekurzív metódus hívása a fa teljes tartalmának megjelenítéséhez
            displayNodeTree(document.getDocumentElement(), 0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Rekurzív metódus, amely egy DOM csomópontot és annak minden gyermekét megjeleníti
     * fa struktúrában, behúzással jelölve a szinteket.
     * 
     * @param node A megjelenítendő csomópont
     * @param level A behúzási szint
     */
    private static void displayNodeTree(Node node, int level) {
        // Behúzás generálása a szint alapján
        String indentation = "  ".repeat(level);
        
        // Csomópont típusának és tartalmának megjelenítése
        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println(indentation + "[DOCUMENT]");
                break;
                
            case Node.ELEMENT_NODE:
                // Elem neve és attribútumok
                StringBuilder elementInfo = new StringBuilder(indentation + "<" + node.getNodeName());
                
                // Attribútumok hozzáadása
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attr = attributes.item(i);
                        elementInfo.append(" ").append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
                    }
                }
                elementInfo.append(">");
                System.out.println(elementInfo.toString());
                break;
                
            case Node.TEXT_NODE:
                // Szöveges tartalom (csak ha nem üres vagy csak whitespace)
                String textContent = node.getTextContent().trim();
                if (!textContent.isEmpty()) {
                    System.out.println(indentation + "  " + textContent);
                }
                break;
                
            case Node.COMMENT_NODE:
                System.out.println(indentation + "<!-- " + node.getTextContent() + " -->");
                break;
                
            default:
                System.out.println(indentation + "[UNKNOWN_TYPE: " + node.getNodeType() + "]");
        }
        
        // Gyermekcsomópontok rekurzív feldolgozása
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            displayNodeTree(children.item(i), level + 1);
        }
        
        // Záró elem megjelenítése (ha elem csomópontról van szó)
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.println(indentation + "</" + node.getNodeName() + ">");
        }
    }
}
