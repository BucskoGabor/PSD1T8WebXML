package xpathpsd1t8;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class xPathQueryPSD1T8 {
    
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("studentPSD1T8.xml");
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            String neptunkod = "PSD1T8";
            
            System.out.println("XPath lekérdezések a " + neptunkod + " kódhoz:");
            System.out.println("=====================================");
            
            String expression1 = "//keresztnev";
            NodeList keresztnevek = (NodeList) xpath.evaluate(expression1, document, XPathConstants.NODESET);
            System.out.println("1. Az összes diák keresztneve:");
            for (int i = 0; i < keresztnevek.getLength(); i++) {
                System.out.println("   " + keresztnevek.item(i).getTextContent());
            }
            
            String expression2 = "//student[kor>20]/keresztnev";
            NodeList idosebbDiakok = (NodeList) xpath.evaluate(expression2, document, XPathConstants.NODESET);
            System.out.println("\n2. 20 évnél idősebb diákok:");
            for (int i = 0; i < idosebbDiakok.getLength(); i++) {
                System.out.println("   " + idosebbDiakok.item(i).getTextContent());
            }
            
            String expression3 = "//student[@id='01']/keresztnev | //student[@id='01']/vezeteknev";
            NodeList diak01 = (NodeList) xpath.evaluate(expression3, document, XPathConstants.NODESET);
            System.out.println("\n3. Az '01' azonosítójú diák neve:");
            for (int i = 0; i < diak01.getLength(); i++) {
                System.out.println("   " + diak01.item(i).getTextContent());
            }
            
            String expression4 = "//becenev";
            NodeList becenevek = (NodeList) xpath.evaluate(expression4, document, XPathConstants.NODESET);
            System.out.println("\n4. Az összes diák beceneve:");
            for (int i = 0; i < becenevek.getLength(); i++) {
                System.out.println("   " + becenevek.item(i).getTextContent());
            }
            
            String expression5 = "//student[2]/*";
            NodeList masodikDiak = (NodeList) xpath.evaluate(expression5, document, XPathConstants.NODESET);
            System.out.println("\n5. A második diák adatai:");
            for (int i = 0; i < masodikDiak.getLength(); i++) {
                System.out.println("   " + masodikDiak.item(i).getNodeName() + ": " + masodikDiak.item(i).getTextContent());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
