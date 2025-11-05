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

public class DomModify1PSD1T8 {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("orarendPSD1T8.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList oraNodes = doc.getElementsByTagName("ora");

            // 1. Add new element
            Node oraNode = oraNodes.item(0);
            if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                Element oraElement = (Element) oraNode;
                Element oraado = doc.createElement("oraado");
                oraado.appendChild(doc.createTextNode("Dr. Kovács Ádám"));
                oraElement.appendChild(oraado);
            }

            // 2. Modify attribute
            for (int i = 0; i < oraNodes.getLength(); i++) {
                Node node = oraNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if ("gyakorlat".equals(element.getAttribute("tipus"))) {
                        element.setAttribute("tipus", "eloadas");
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            StreamResult fileResult = new StreamResult(new File("orarendModify1PSD1T8.xml"));
            transformer.transform(source, fileResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
