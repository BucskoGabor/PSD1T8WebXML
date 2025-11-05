package dompsd1t81029;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomReadPSD1T8 {
    public static void main(String[] args) {
        try {
            // XML dokumentum beolvasása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("hallgatoPSD1T8.xml");
            
            // Normalizálás
            document.getDocumentElement().normalize();
            
            System.out.println("Gyökér elem: " + document.getDocumentElement().getNodeName());
            System.out.println("=====================================");
            
            // Fa struktúra kiírása blokk formában
            printNodeTree(document.getDocumentElement(), 0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void printNodeTree(Node node, int depth) {
        // Behúzás generálása a mélység alapján
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        
        // Csomópont típusának és nevének kiírása
        String nodeType = getNodeTypeName(node.getNodeType());
        System.out.println(indent + nodeType + ": " + node.getNodeName());
        
        // Ha van értéke, kiírjuk
        if (node.getNodeValue() != null && !node.getNodeValue().trim().isEmpty()) {
            System.out.println(indent + "  Érték: " + node.getNodeValue().trim());
        }
        
        // Ha vannak attribútumai, kiírjuk őket
        if (node.hasAttributes()) {
            for (int i = 0; i < node.getAttributes().getLength(); i++) {
                Node attr = node.getAttributes().item(i);
                System.out.println(indent + "  Attribútum: " + attr.getNodeName() + " = " + attr.getNodeValue());
            }
        }
        
        // Gyermek csomópontok rekurzív feldolgozása
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            // Csak az elem csomópontokat és a szöveges csomópontokat dolgozzuk fel
            if (child.getNodeType() == Node.ELEMENT_NODE || 
                (child.getNodeType() == Node.TEXT_NODE && !child.getNodeValue().trim().isEmpty())) {
                printNodeTree(child, depth + 1);
            }
        }
    }
    
    private static String getNodeTypeName(short nodeType) {
        switch (nodeType) {
            case Node.ELEMENT_NODE: return "Elem";
            case Node.ATTRIBUTE_NODE: return "Attribútum";
            case Node.TEXT_NODE: return "Szöveg";
            case Node.CDATA_SECTION_NODE: return "CDATA";
            case Node.ENTITY_REFERENCE_NODE: return "Entity Reference";
            case Node.ENTITY_NODE: return "Entity";
            case Node.PROCESSING_INSTRUCTION_NODE: return "Processing Instruction";
            case Node.COMMENT_NODE: return "Komment";
            case Node.DOCUMENT_NODE: return "Dokumentum";
            case Node.DOCUMENT_TYPE_NODE: return "Document Type";
            case Node.DOCUMENT_FRAGMENT_NODE: return "Document Fragment";
            case Node.NOTATION_NODE: return "Notation";
            default: return "Ismeretlen";
        }
    }
}
