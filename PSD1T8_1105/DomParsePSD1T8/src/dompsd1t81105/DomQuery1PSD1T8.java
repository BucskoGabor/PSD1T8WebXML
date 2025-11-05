package dompsd1t81105;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomQuery1PSD1T8 {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("orarendPSD1T8.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 1. Course names
            List<String> kurzusok = new ArrayList<>();
            NodeList oraNodes = doc.getElementsByTagName("ora");
            for (int i = 0; i < oraNodes.getLength(); i++) {
                Node oraNode = oraNodes.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;
                    kurzusok.add(oraElement.getElementsByTagName("targy").item(0).getTextContent());
                }
            }
            System.out.println("Kurzusnév: " + kurzusok);

            // 2. First course
            Node firstOraNode = doc.getElementsByTagName("ora").item(0);
            Document newDoc = dBuilder.newDocument();
            Node importedNode = newDoc.importNode(firstOraNode, true);
            newDoc.appendChild(importedNode);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(newDoc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            StreamResult fileResult = new StreamResult(new File("orarend1PSD1T8.xml"));
            transformer.transform(source, fileResult);

            // 3. Lecturers
            List<String> oktatok = new ArrayList<>();
            for (int i = 0; i < oraNodes.getLength(); i++) {
                Node oraNode = oraNodes.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;
                    oktatok.add(oraElement.getElementsByTagName("oktato").item(0).getTextContent());
                }
            }
            System.out.println("\nOktatók: " + oktatok);

            // 4. Complex Query: Courses on Monday
            System.out.println("\nÖsszetett lekérdezés: Hétfői kurzusok:");
            for (int i = 0; i < oraNodes.getLength(); i++) {
                Node oraNode = oraNodes.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;
                    String nap = oraElement.getElementsByTagName("nap").item(0).getTextContent();
                    if ("Hétfő".equals(nap)) {
                        System.out.println(" - " + oraElement.getElementsByTagName("targy").item(0).getTextContent());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
