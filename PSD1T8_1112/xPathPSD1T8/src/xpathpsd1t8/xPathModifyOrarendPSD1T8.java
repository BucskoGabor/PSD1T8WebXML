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
import java.io.File;

public class xPathModifyOrarendPSD1T8 {
    
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("orarendPSD1T8.xml");
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            
            String neptunkod = "PSD1T8";
            
            System.out.println("Óarend XPath módosítások a " + neptunkod + " kódhoz:");
            System.out.println("============================================");
            
            String expression1 = "//ora[targy='Elektro']/helyszin";
            Node eletroHelyszin = (Node) xpath.evaluate(expression1, document, XPathConstants.NODE);
            if (eletroHelyszin != null) {
                String eredetiHelyszin = eletroHelyszin.getTextContent();
                eletroHelyszin.setTextContent("A1/101");
                System.out.println("1. Elektro helyszín módosítva: " + eredetiHelyszin + " -> A1/101");
            }
            
            String expression2 = "//ora[oktato='Agárdi Anita']/oktato";
            NodeList agardiOktatok = (NodeList) xpath.evaluate(expression2, document, XPathConstants.NODESET);
            for (int i = 0; i < agardiOktatok.getLength(); i++) {
                agardiOktatok.item(i).setTextContent("Dr. Agárdi Anita");
            }
            System.out.println("2. Agárdi Anita neve megváltoztatva 'Dr. Agárdi Anita'-ra (" + agardiOktatok.getLength() + " óra)");
            
            Element rootElement = document.getDocumentElement();
            Element ujOra = document.createElement("ora");
            ujOra.setAttribute("id", "13");
            ujOra.setAttribute("tipus", "eloadas");
            
            Element targy = document.createElement("targy");
            targy.setTextContent("Adatbázisok");
            ujOra.appendChild(targy);
            
            Element idopont = document.createElement("idopont");
            Element nap = document.createElement("nap");
            nap.setTextContent("Csütörtök");
            idopont.appendChild(nap);
            
            Element tol = document.createElement("tol");
            tol.setTextContent("1400");
            idopont.appendChild(tol);
            
            Element ig = document.createElement("ig");
            ig.setTextContent("1600");
            idopont.appendChild(ig);
            
            ujOra.appendChild(idopont);
            
            Element helyszin = document.createElement("helyszin");
            helyszin.setTextContent("A2/201");
            ujOra.appendChild(helyszin);
            
            Element oktato = document.createElement("oktato");
            oktato.setTextContent("Kovács László");
            ujOra.appendChild(oktato);
            
            Element szak = document.createElement("szak");
            szak.setTextContent("Mérnökinformatikus");
            ujOra.appendChild(szak);
            
            rootElement.appendChild(ujOra);
            System.out.println("3. Új óra hozzáadva: Adatbázisok (Csütörtök 14:00-16:00)");
            
            String expression4 = "//ora[targy='Webtech' and @tipus='gyakorlat']/helyszin";
            Node webtechGyakTerem = (Node) xpath.evaluate(expression4, document, XPathConstants.NODE);
            if (webtechGyakTerem != null) {
                String eredetiTerem = webtechGyakTerem.getTextContent();
                webtechGyakTerem.setTextContent("In/102");
                System.out.println("4. Webtech gyakorlat terem módosítva: " + eredetiTerem + " -> In/102");
            }
            
            System.out.println("\nMódosított órarend:");
            NodeList orak = document.getElementsByTagName("ora");
            for (int i = 0; i < orak.getLength(); i++) {
                Node ora = orak.item(i);
                if (ora.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) ora;
                    String targyNev = oraElement.getElementsByTagName("targy").item(0).getTextContent();
                    String idopontNap = oraElement.getElementsByTagName("nap").item(0).getTextContent();
                    String idopontTol = oraElement.getElementsByTagName("tol").item(0).getTextContent();
                    System.out.println("   " + targyNev + " - " + idopontNap + " " + idopontTol);
                }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("orarendPSD1T81.xml"));
            transformer.transform(source, result);
            System.out.println("\nA módosított dokumentum mentve: orarendPSD1T81.xml");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
