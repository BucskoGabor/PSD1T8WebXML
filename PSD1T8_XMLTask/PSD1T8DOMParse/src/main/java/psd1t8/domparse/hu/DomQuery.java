package psd1t8.domparse.hu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DomQuery osztály - XML lekérdezések DOM parser használatával
 * Készítette: PSD1T8
 */
public class DomQuery {

    private Document document;
    private Scanner scanner;

    public DomQuery(String filePath) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        try {
            DomQuery query = new DomQuery("src/main/resources/PSD1T8_XML.xml");

            System.out.println("=== XML Lekérdezések ===\n");

            boolean running = true;
            while (running) {
                System.out.println("\nVálasszon lekérdezést:");
                System.out.println("1. Könyvek szerző szerint");
                System.out.println("2. Kölcsönzések olvasó szerint");
                System.out.println("3. Könyvtárosok és mentoraik");
                System.out.println("4. Kiadók és könyveik");
                System.out.println("5. Aktív kölcsönzések");
                System.out.println("6. Összes lekérdezés futtatása");
                System.out.println("0. Vissza");
                System.out.print("\nVálasztás: ");

                int choice = query.scanner.nextInt();
                query.scanner.nextLine(); // Consume newline
                System.out.println();

                switch (choice) {
                    case 1:
                        System.out.print("Adja meg a szerző ID-t (pl. SZ001, SZ002, SZ003): ");
                        String szerzoId = query.scanner.nextLine();
                        query.konyvekSzerzoSzerint(szerzoId);
                        break;
                    case 2:
                        System.out.print("Adja meg az olvasó ID-t (pl. O001, O002, O003): ");
                        String olvasoId = query.scanner.nextLine();
                        query.kolcsonzesekOlvasoSzerint(olvasoId);
                        break;
                    case 3:
                        query.konyvtarosokEsMentoraik();
                        break;
                    case 4:
                        query.kiadokEsKonyveik();
                        break;
                    case 5:
                        query.aktivKolcsonzesek();
                        break;
                    case 6:
                        System.out.println("--- Összes lekérdezés futtatása ---\n");
                        query.konyvekSzerzoSzerint("SZ002");
                        query.kolcsonzesekOlvasoSzerint("O001");
                        query.konyvtarosokEsMentoraik();
                        query.kiadokEsKonyveik();
                        query.aktivKolcsonzesek();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Kilépés a lekérdezésekből...");
                        break;
                    default:
                        System.out.println("Érvénytelen választás!");
                }

                if (running && choice != 0) {
                    System.out.println("\nNyomjon Enter-t a folytatáshoz...");
                    query.scanner.nextLine();
                }
            }



        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. Lekérdezés: Könyvek listázása adott szerző szerint
     */
    public void konyvekSzerzoSzerint(String szerzoId) {
        System.out.println("=== 1. Könyvek szerző szerint (Szerző ID: " + szerzoId + ") ===");

        // Először megkeressük a szerző nevét
        NodeList szerzok = document.getElementsByTagName("szerzo");
        String szerzoNev = "";
        for (int i = 0; i < szerzok.getLength(); i++) {
            Element szerzo = (Element) szerzok.item(i);
            if (szerzo.getAttribute("Szerzo_ID").equals(szerzoId)) {
                szerzoNev = getElementText(szerzo, "Nev");
                break;
            }
        }
        System.out.println("Szerző neve: " + szerzoNev + "\n");

        // Könyvek keresése
        NodeList konyvek = document.getElementsByTagName("konyv");
        boolean found = false;

        for (int i = 0; i < konyvek.getLength(); i++) {
            Element konyv = (Element) konyvek.item(i);
            NodeList szerzoRefs = konyv.getElementsByTagName("szerzo_ref");

            for (int j = 0; j < szerzoRefs.getLength(); j++) {
                Element szerzoRef = (Element) szerzoRefs.item(j);
                if (szerzoRef.getAttribute("Szerzo_ID").equals(szerzoId)) {
                    found = true;
                    System.out.println("Könyv ID: " + konyv.getAttribute("Konyv_ID"));
                    System.out.println("  Cím: " + getElementText(konyv, "Cim"));
                    System.out.println("  Kiadási év: " + getElementText(konyv, "Kiadasi_evi"));
                    System.out.println("  Szerep: " + getElementText(szerzoRef, "Szerep"));
                    System.out.println();
                }
            }
        }

        if (!found) {
            System.out.println("Nincs könyv ezzel a szerzővel.\n");
        }
    }

    /**
     * 2. Lekérdezés: Kölcsönzések olvasó szerint
     */
    public void kolcsonzesekOlvasoSzerint(String olvasoId) {
        System.out.println("=== 2. Kölcsönzések olvasó szerint (Olvasó ID: " + olvasoId + ") ===");

        // Olvasó neve
        NodeList olvasok = document.getElementsByTagName("olvaso");
        String olvasoNev = "";
        for (int i = 0; i < olvasok.getLength(); i++) {
            Element olvaso = (Element) olvasok.item(i);
            if (olvaso.getAttribute("Olvaso_ID").equals(olvasoId)) {
                olvasoNev = getElementText(olvaso, "Nev");
                break;
            }
        }
        System.out.println("Olvasó neve: " + olvasoNev + "\n");

        // Kölcsönzések keresése
        NodeList kolcsonzesek = document.getElementsByTagName("kolcsonzes");
        boolean found = false;

        for (int i = 0; i < kolcsonzesek.getLength(); i++) {
            Element kolcsonzes = (Element) kolcsonzesek.item(i);
            if (getElementText(kolcsonzes, "Olvaso_ref").equals(olvasoId)) {
                found = true;
                String konyvId = getElementText(kolcsonzes, "Konyv_ref");
                String konyvCim = getKonyvCim(konyvId);

                System.out.println("Kölcsönzés #" + (i + 1));
                System.out.println("  Könyv: " + konyvCim + " (" + konyvId + ")");
                System.out.println("  Kölcsönzés dátuma: " + getElementText(kolcsonzes, "Kolcsonzes_datuma"));
                System.out.println("  Határidő: " + getElementText(kolcsonzes, "Visszahozas_hatarido"));
                System.out.println("  Visszahozva: " + getElementText(kolcsonzes, "Visszahozva"));
                System.out.println();
            }
        }

        if (!found) {
            System.out.println("Nincs kölcsönzés ennél az olvasónál.\n");
        }
    }

    /**
     * 3. Lekérdezés: Könyvtárosok és mentoraik
     */
    public void konyvtarosokEsMentoraik() {
        System.out.println("=== 3. Könyvtárosok és mentoraik ===\n");

        NodeList konyvtarosok = document.getElementsByTagName("konyvtaros");

        for (int i = 0; i < konyvtarosok.getLength(); i++) {
            Element konyvtaros = (Element) konyvtarosok.item(i);
            String id = konyvtaros.getAttribute("Konyvtaros_id");
            String nev = getElementText(konyvtaros, "Nev");
            String mentorRef = getElementText(konyvtaros, "Mentor_ref");

            System.out.println(nev + " (" + id + ")");
            if (!mentorRef.isEmpty()) {
                String mentorNev = getKonyvtarosNev(mentorRef);
                System.out.println("  Mentor: " + mentorNev + " (" + mentorRef + ")");
            } else {
                System.out.println("  Nincs mentor (vezető beosztás)");
            }
            System.out.println();
        }
    }

    /**
     * 4. Lekérdezés: Kiadók és könyveik
     */
    public void kiadokEsKonyveik() {
        System.out.println("=== 4. Kiadók és könyveik ===\n");

        NodeList kiadok = document.getElementsByTagName("kiado");

        for (int i = 0; i < kiadok.getLength(); i++) {
            Element kiado = (Element) kiadok.item(i);
            String kiadoId = kiado.getAttribute("Kiado_ID");
            String kiadoNev = getElementText(kiado, "Nev");

            System.out.println(kiadoNev + " (" + kiadoId + ")");
            System.out.println("  Könyvei:");

            NodeList konyvek = document.getElementsByTagName("konyv");
            int count = 0;
            for (int j = 0; j < konyvek.getLength(); j++) {
                Element konyv = (Element) konyvek.item(j);
                if (getElementText(konyv, "Kiado_ref").equals(kiadoId)) {
                    count++;
                    System.out.println("    - " + getElementText(konyv, "Cim") +
                            " (" + getElementText(konyv, "Kiadasi_evi") + ")");
                }
            }
            if (count == 0) {
                System.out.println("    Nincs könyv");
            }
            System.out.println();
        }
    }

    /**
     * 5. Lekérdezés: Aktív (még vissza nem hozott) kölcsönzések
     */
    public void aktivKolcsonzesek() {
        System.out.println("=== 5. Aktív kölcsönzések (vissza nem hozott) ===\n");

        NodeList kolcsonzesek = document.getElementsByTagName("kolcsonzes");
        int count = 0;

        for (int i = 0; i < kolcsonzesek.getLength(); i++) {
            Element kolcsonzes = (Element) kolcsonzesek.item(i);
            String visszahozva = getElementText(kolcsonzes, "Visszahozva");

            if (visszahozva.equals("false")) {
                count++;
                String olvasoId = getElementText(kolcsonzes, "Olvaso_ref");
                String konyvId = getElementText(kolcsonzes, "Konyv_ref");

                System.out.println("Kölcsönzés #" + count);
                System.out.println("  Olvasó: " + getOlvasoNev(olvasoId) + " (" + olvasoId + ")");
                System.out.println("  Könyv: " + getKonyvCim(konyvId) + " (" + konyvId + ")");
                System.out.println("  Kölcsönzés dátuma: " + getElementText(kolcsonzes, "Kolcsonzes_datuma"));
                System.out.println("  Határidő: " + getElementText(kolcsonzes, "Visszahozas_hatarido"));
                System.out.println();
            }
        }

        if (count == 0) {
            System.out.println("Nincs aktív kölcsönzés.\n");
        }
    }

    // Segédfüggvények
    private String getKonyvCim(String konyvId) {
        NodeList konyvek = document.getElementsByTagName("konyv");
        for (int i = 0; i < konyvek.getLength(); i++) {
            Element konyv = (Element) konyvek.item(i);
            if (konyv.getAttribute("Konyv_ID").equals(konyvId)) {
                return getElementText(konyv, "Cim");
            }
        }
        return "";
    }

    private String getOlvasoNev(String olvasoId) {
        NodeList olvasok = document.getElementsByTagName("olvaso");
        for (int i = 0; i < olvasok.getLength(); i++) {
            Element olvaso = (Element) olvasok.item(i);
            if (olvaso.getAttribute("Olvaso_ID").equals(olvasoId)) {
                return getElementText(olvaso, "Nev");
            }
        }
        return "";
    }

    private String getKonyvtarosNev(String konyvtarosId) {
        NodeList konyvtarosok = document.getElementsByTagName("konyvtaros");
        for (int i = 0; i < konyvtarosok.getLength(); i++) {
            Element konyvtaros = (Element) konyvtarosok.item(i);
            if (konyvtaros.getAttribute("Konyvtaros_id").equals(konyvtarosId)) {
                return getElementText(konyvtaros, "Nev");
            }
        }
        return "";
    }

    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0 && nodeList.item(0) != null) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
