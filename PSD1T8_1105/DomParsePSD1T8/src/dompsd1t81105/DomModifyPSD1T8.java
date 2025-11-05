package dompsd1t81105;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

public class DomModifyPSD1T8 {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("hallgatoPSD1T8.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList hallgatoNodes = doc.getElementsByTagName("hallgato");

            for (int i = 0; i < hallgatoNodes.getLength(); i++) {
                Node hallgatoNode = hallgatoNodes.item(i);
                if (hallgatoNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element hallgatoElement = (Element) hallgatoNode;
                    if (hallgatoElement.getAttribute("id").equals("01")) {
                        NodeList childNodes = hallgatoElement.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node childNode = childNodes.item(j);
                            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                                if ("keresztnev".equals(childNode.getNodeName())) {
                                    childNode.setTextContent("Béla");
                                }
                                if ("vezeteknev".equals(childNode.getNodeName())) {
                                    childNode.setTextContent("Varga");
                                }
                            }
                        }
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
