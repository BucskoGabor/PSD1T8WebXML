package xpathpsd1t8;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class xPathQueryOrarendPSD1T8 {
    
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("orarendPSD1T8.xml");
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            String neptunkod = "PSD1T8";
            
            System.out.println("Óarend XPath lekérdezések a " + neptunkod + " kódhoz:");
            System.out.println("============================================");
            
            String expression1 = "//ora[@tipus='eloadas']/targy";
            NodeList eloadasok = (NodeList) xpath.evaluate(expression1, document, XPathConstants.NODESET);
            System.out.println("1. Az összes előadás:");
            for (int i = 0; i < eloadasok.getLength(); i++) {
                System.out.println("   " + eloadasok.item(i).getTextContent());
            }
            
            String expression2 = "//ora[idopont/nap='Hétfő']/targy";
            NodeList hetfoiOrak = (NodeList) xpath.evaluate(expression2, document, XPathConstants.NODESET);
            System.out.println("\n2. Hétfői órák:");
            for (int i = 0; i < hetfoiOrak.getLength(); i++) {
                System.out.println("   " + hetfoiOrak.item(i).getTextContent());
            }
            
            String expression3 = "//ora[oktato='Agárdi Anita']/targy";
            NodeList agardiOrak = (NodeList) xpath.evaluate(expression3, document, XPathConstants.NODESET);
            System.out.println("\n3. Agárdi Anita órái:");
            for (int i = 0; i < agardiOrak.getLength(); i++) {
                System.out.println("   " + agardiOrak.item(i).getTextContent());
            }
            
            String expression4 = "//ora[helyszin='In/101']/targy";
            NodeList in101Orak = (NodeList) xpath.evaluate(expression4, document, XPathConstants.NODESET);
            System.out.println("\n4. In/101 teremben lévő órák:");
            for (int i = 0; i < in101Orak.getLength(); i++) {
                System.out.println("   " + in101Orak.item(i).getTextContent());
            }
            
            String expression5 = "//ora[idopont/tol='0800']/targy";
            String nyolcasOrak = (String) xpath.evaluate(expression5, document, XPathConstants.STRING);
            System.out.println("\n5. 8:00-kor kezdődő órák:");
            System.out.println("   " + nyolcasOrak);
            
            String expression6 = "//ora[targy='Webtech']/idopont/nap";
            NodeList webtechNapok = (NodeList) xpath.evaluate(expression6, document, XPathConstants.NODESET);
            System.out.println("\n6. Webtech órák napjai:");
            for (int i = 0; i < webtechNapok.getLength(); i++) {
                System.out.println("   " + webtechNapok.item(i).getTextContent());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
