package psd1t8.domparse.hu;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DomRead osztály - XML fájl beolvasása és kiírása DOM parser használatával
 * Készítette: PSD1T8
 */
public class DomRead {
    
    public static void main(String[] args) {
        try {
            // XML fájl betöltése
            File xmlFile = new File("src/main/resources/PSD1T8_XML.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            
            System.out.println("=== XML Dokumentum Beolvasása ===\n");
            System.out.println("Gyökérelem: " + document.getDocumentElement().getNodeName());
            System.out.println();
            
            // Szerzők kiírása
            printSzerzok(document);
            
            // Kiadók kiírása
            printKiadok(document);
            
            // Könyvek kiírása
            printKonyvek(document);
            
            // Könyvtárosok kiírása
            printKonyvtarosok(document);
            
            // Olvasók kiírása
            printOlvasok(document);
            
            // Kölcsönzések kiírása
            printKolcsonzesek(document);
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void printSzerzok(Document doc) {
        System.out.println("=== SZERZŐK ===");
        NodeList szerzok = doc.getElementsByTagName("szerzo");
        
        for (int i = 0; i < szerzok.getLength(); i++) {
            Element szerzo = (Element) szerzok.item(i);
            System.out.println("\nSzerző ID: " + szerzo.getAttribute("Szerzo_ID"));
            System.out.println("  Név: " + getElementText(szerzo, "Nev"));
            System.out.println("  Születési dátum: " + getElementText(szerzo, "Szuletesi_datum"));
            
            NodeList emailek = szerzo.getElementsByTagName("email");
            System.out.println("  Email címek:");
            for (int j = 0; j < emailek.getLength(); j++) {
                System.out.println("    - " + emailek.item(j).getTextContent());
            }
        }
        System.out.println();
    }
    
    private static void printKiadok(Document doc) {
        System.out.println("=== KIADÓK ===");
        NodeList kiadok = doc.getElementsByTagName("kiado");
        
        for (int i = 0; i < kiadok.getLength(); i++) {
            Element kiado = (Element) kiadok.item(i);
            System.out.println("\nKiadó ID: " + kiado.getAttribute("Kiado_ID"));
            System.out.println("  Név: " + getElementText(kiado, "Nev"));
            
            Element cim = (Element) kiado.getElementsByTagName("Cim").item(0);
            System.out.println("  Cím:");
            System.out.println("    Város: " + getElementText(cim, "Varos"));
            System.out.println("    Utca: " + getElementText(cim, "Utca"));
            
            System.out.println("  Kapcsolat: " + getElementText(kiado, "Kapcsolat"));
        }
        System.out.println();
    }
    
    private static void printKonyvek(Document doc) {
        System.out.println("=== KÖNYVEK ===");
        NodeList konyvek = doc.getElementsByTagName("konyv");
        
        for (int i = 0; i < konyvek.getLength(); i++) {
            Element konyv = (Element) konyvek.item(i);
            System.out.println("\nKönyv ID: " + konyv.getAttribute("Konyv_ID"));
            System.out.println("  Cím: " + getElementText(konyv, "Cim"));
            System.out.println("  Kiadási év: " + getElementText(konyv, "Kiadasi_evi"));
            System.out.println("  Oldalszám: " + getElementText(konyv, "Oldalszam"));
            System.out.println("  Kiadó ref: " + getElementText(konyv, "Kiado_ref"));
            
            NodeList szerzok = konyv.getElementsByTagName("szerzo_ref");
            System.out.println("  Szerzők:");
            for (int j = 0; j < szerzok.getLength(); j++) {
                Element szerzo_ref = (Element) szerzok.item(j);
                System.out.println("    Szerző ID: " + szerzo_ref.getAttribute("Szerzo_ID") + 
                                 " - Szerep: " + getElementText(szerzo_ref, "Szerep"));
            }
        }
        System.out.println();
    }
    
    private static void printKonyvtarosok(Document doc) {
        System.out.println("=== KÖNYVTÁROSOK ===");
        NodeList konyvtarosok = doc.getElementsByTagName("konyvtaros");
        
        for (int i = 0; i < konyvtarosok.getLength(); i++) {
            Element konyvtaros = (Element) konyvtarosok.item(i);
            System.out.println("\nKönyvtáros ID: " + konyvtaros.getAttribute("Konyvtaros_id"));
            System.out.println("  Név: " + getElementText(konyvtaros, "Nev"));
            System.out.println("  Beosztás: " + getElementText(konyvtaros, "Befosztas"));
            System.out.println("  Elsosztás: " + getElementText(konyvtaros, "Elsosztas"));
            
            String mentorRef = getElementText(konyvtaros, "Mentor_ref");
            if (!mentorRef.isEmpty()) {
                System.out.println("  Mentor ref: " + mentorRef);
            }
        }
        System.out.println();
    }
    
    private static void printOlvasok(Document doc) {
        System.out.println("=== OLVASÓK ===");
        NodeList olvasok = doc.getElementsByTagName("olvaso");
        
        for (int i = 0; i < olvasok.getLength(); i++) {
            Element olvaso = (Element) olvasok.item(i);
            System.out.println("\nOlvasó ID: " + olvaso.getAttribute("Olvaso_ID"));
            System.out.println("  Név: " + getElementText(olvaso, "Nev"));
            System.out.println("  Születési dátum: " + getElementText(olvaso, "Szuletesi_datum"));
            
            NodeList telefonok = olvaso.getElementsByTagName("telefon");
            System.out.println("  Telefonszámok:");
            for (int j = 0; j < telefonok.getLength(); j++) {
                System.out.println("    - " + telefonok.item(j).getTextContent());
            }
            
            Element regisztracio = (Element) olvaso.getElementsByTagName("Regisztracio").item(0);
            System.out.println("  Regisztráció:");
            System.out.println("    Regisztrált könyvtáros: " + 
                             getElementText(regisztracio, "Regisztralt_konyvtaros"));
            System.out.println("    Fizetés: " + getElementText(regisztracio, "Fizetes") + " Ft");
        }
        System.out.println();
    }
    
    private static void printKolcsonzesek(Document doc) {
        System.out.println("=== KÖLCSÖNZÉSEK ===");
        NodeList kolcsonzesek = doc.getElementsByTagName("kolcsonzes");
        
        for (int i = 0; i < kolcsonzesek.getLength(); i++) {
            Element kolcsonzes = (Element) kolcsonzesek.item(i);
            System.out.println("\nKölcsönzés #" + (i + 1));
            System.out.println("  Olvasó ref: " + getElementText(kolcsonzes, "Olvaso_ref"));
            System.out.println("  Könyv ref: " + getElementText(kolcsonzes, "Konyv_ref"));
            System.out.println("  Kölcsönzés dátuma: " + getElementText(kolcsonzes, "Kolcsonzes_datuma"));
            System.out.println("  Visszahozás határidő: " + getElementText(kolcsonzes, "Visszahozas_hatarido"));
            System.out.println("  Visszahozva: " + getElementText(kolcsonzes, "Visszahozva"));
        }
        System.out.println();
    }
    
    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent();
            }
        }
        return "";
    }
}