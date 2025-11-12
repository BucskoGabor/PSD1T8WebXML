package xpathpsd1t8;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xPathModifyPSD1T8 {
    
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("studentPSD1T8.xml");
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            String expression = "//student[@id='01']/keresztnev";
            Node keresztnevNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            
            if (keresztnevNode != null) {
                String eredetiKeresztnev = keresztnevNode.getTextContent();
                System.out.println("Eredeti keresztnév (id=01): " + eredetiKeresztnev);
                
                keresztnevNode.setTextContent("Géza");
                System.out.println("Módosított keresztnév (id=01): Géza");
            }
            
            String studentExpression = "//student[@id='01']";
            Node studentNode = (Node) xpath.evaluate(studentExpression, document, XPathConstants.NODE);
            
            System.out.println("\nA módosított diák (id=01) teljes adatai:");
            if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;
                System.out.println("ID: " + studentElement.getAttribute("id"));
                
                NodeList childNodes = studentElement.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node child = childNodes.item(i);
                    if (child.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println(child.getNodeName() + ": " + child.getTextContent());
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
