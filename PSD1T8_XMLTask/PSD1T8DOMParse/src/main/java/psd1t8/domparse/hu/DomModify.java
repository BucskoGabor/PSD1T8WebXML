package psd1t8.domparse.hu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * DomModify osztály - XML módosítása DOM parser használatával
 * Készítette: PSD1T8
 */
public class DomModify {

    private Document document;
    private Scanner scanner;

    public DomModify(String filePath) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        try {
            DomModify modify = new DomModify("src/main/resources/PSD1T8_XML.xml");

            System.out.println("=== XML Módosítások ===\n");

            boolean running = true;
            while (running) {
                System.out.println("\nVálasszon módosítási műveletet:");
                System.out.println("1. Új szerző hozzáadása");
                System.out.println("2. Kölcsönzés visszahozása");
                System.out.println("3. Olvasó telefonszámának módosítása");
                System.out.println("4. Új könyv hozzáadása");
                System.out.println("5. Szerző törlése");
                System.out.println("6. Összes művelet futtatása (példa adatokkal)");
                System.out.println("0. Mentés és kilépés");
                System.out.print("\nVálasztás: ");

                int choice = modify.scanner.nextInt();
                modify.scanner.nextLine(); // Consume newline
                System.out.println();

                switch (choice) {
                    case 1:
                        System.out.print("Szerző ID (pl. SZ004): ");
                        String id = modify.scanner.nextLine();
                        System.out.print("Név: ");
                        String nev = modify.scanner.nextLine();
                        System.out.print("Születési dátum (YYYY-MM-DD): ");
                        String szulDatum = modify.scanner.nextLine();
                        System.out.print("Email cím: ");
                        String email = modify.scanner.nextLine();
                        modify.ujSzerzoHozzaadasa(id, nev, szulDatum, new String[] { email });
                        break;

                    case 2:
                        System.out.print("Olvasó ID (pl. O001): ");
                        String olvasoId = modify.scanner.nextLine();
                        System.out.print("Könyv ID (pl. KV001): ");
                        String konyvId = modify.scanner.nextLine();
                        modify.kolcsonzesVisszahozasa(olvasoId, konyvId);
                        break;

                    case 3:
                        System.out.print("Olvasó ID (pl. O003): ");
                        String olvasoIdTel = modify.scanner.nextLine();
                        System.out.print("Új telefonszám (pl. +36301112222): ");
                        String ujTelefon = modify.scanner.nextLine();
                        modify.olvasoTelefonModositasa(olvasoIdTel, ujTelefon);
                        break;

                    case 4:
                        System.out.print("Könyv ID (pl. KV005): ");
                        String konyvIdUj = modify.scanner.nextLine();
                        System.out.print("Cím: ");
                        String cim = modify.scanner.nextLine();
                        System.out.print("Kiadási év (YYYY): ");
                        String ev = modify.scanner.nextLine();
                        System.out.print("Oldalszám: ");
                        int oldal = modify.scanner.nextInt();
                        modify.scanner.nextLine();
                        System.out.print("Kiadó ID (pl. K001): ");
                        String kiadoRef = modify.scanner.nextLine();
                        System.out.print("Szerző ID (pl. SZ001): ");
                        String szerzoRef = modify.scanner.nextLine();
                        System.out.print("Szerep (pl. Főszerző): ");
                        String szerep = modify.scanner.nextLine();
                        modify.ujKonyvHozzaadasa(konyvIdUj, cim, ev, oldal, kiadoRef, szerzoRef, szerep);
                        break;

                    case 5:
                        System.out.print("Törlendő szerző ID (pl. SZ004): ");
                        String szerzoTorol = modify.scanner.nextLine();
                        modify.szerzoTorlese(szerzoTorol);
                        break;

                    case 6:
                        System.out.println("--- Példa módosítások futtatása ---\n");
                        modify.ujSzerzoHozzaadasa("SZ004", "Szabó Katalin", "1992-04-18",
                                new String[] { "szabo.katalin@email.hu" });
                        modify.kolcsonzesVisszahozasa("O001", "KV001");
                        modify.olvasoTelefonModositasa("O003", "+36301112222");
                        break;

                    case 0:
                        // Módosított XML mentése
                        modify.mentes("src/main/resources/PSD1T8_XML_modified.xml");
                        System.out.println("\n=== Módosítások sikeresen mentve: PSD1T8_XML_modified.xml ===");
                        running = false;
                        break;

                    default:
                        System.out.println("Érvénytelen választás!");
                }

                if (running && choice != 0) {
                    System.out.println("\nNyomjon Enter-t a folytatáshoz...");
                    modify.scanner.nextLine();
                }
            }



        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. Módosítás: Új szerző hozzáadása
     */
    public void ujSzerzoHozzaadasa(String id, String nev, String szuletesiDatum, String[] emailek) {
        System.out.println("1. Új szerző hozzáadása: " + nev);

        // Szerzők konténer megkeresése
        NodeList szerzokList = document.getElementsByTagName("szerzok");
        Element szerzokElem = (Element) szerzokList.item(0);

        // Új szerző elem létrehozása
        Element ujSzerzo = document.createElement("szerzo");
        ujSzerzo.setAttribute("Szerzo_ID", id);

        // Név hozzáadása
        Element nevElem = document.createElement("Nev");
        nevElem.setTextContent(nev);
        ujSzerzo.appendChild(nevElem);

        // Születési dátum hozzáadása
        Element szulDatumElem = document.createElement("Szuletesi_datum");
        szulDatumElem.setTextContent(szuletesiDatum);
        ujSzerzo.appendChild(szulDatumElem);

        // Email címek hozzáadása
        Element emailCimekElem = document.createElement("Email_cimek");
        for (String email : emailek) {
            Element emailElem = document.createElement("email");
            emailElem.setTextContent(email);
            emailCimekElem.appendChild(emailElem);
        }
        ujSzerzo.appendChild(emailCimekElem);

        // Hozzáadás a szerzők listához
        szerzokElem.appendChild(ujSzerzo);

        System.out.println("  ✓ Szerző hozzáadva: " + id + " - " + nev + "\n");
    }

    /**
     * 2. Módosítás: Kölcsönzés visszahozásának beállítása
     */
    public void kolcsonzesVisszahozasa(String olvasoId, String konyvId) {
        System.out.println("2. Kölcsönzés visszahozása beállítása");
        System.out.println("   Olvasó: " + olvasoId + ", Könyv: " + konyvId);

        NodeList kolcsonzesek = document.getElementsByTagName("kolcsonzes");
        boolean found = false;

        for (int i = 0; i < kolcsonzesek.getLength(); i++) {
            Element kolcsonzes = (Element) kolcsonzesek.item(i);
            String olvaso = getElementText(kolcsonzes, "Olvaso_ref");
            String konyv = getElementText(kolcsonzes, "Konyv_ref");

            if (olvaso.equals(olvasoId) && konyv.equals(konyvId)) {
                NodeList visszahozvaList = kolcsonzes.getElementsByTagName("Visszahozva");
                if (visszahozvaList.getLength() > 0) {
                    visszahozvaList.item(0).setTextContent("true");
                    found = true;
                    System.out.println("  ✓ Kölcsönzés visszahozva státuszra állítva\n");
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("  ✗ Nem található ilyen kölcsönzés\n");
        }
    }

    /**
     * 3. Módosítás: Olvasó első telefonszámának módosítása
     */
    public void olvasoTelefonModositasa(String olvasoId, String ujTelefon) {
        System.out.println("3. Olvasó telefonszám módosítása");
        System.out.println("   Olvasó ID: " + olvasoId + ", Új telefon: " + ujTelefon);

        NodeList olvasok = document.getElementsByTagName("olvaso");
        boolean found = false;

        for (int i = 0; i < olvasok.getLength(); i++) {
            Element olvaso = (Element) olvasok.item(i);
            if (olvaso.getAttribute("Olvaso_ID").equals(olvasoId)) {
                NodeList telefonok = olvaso.getElementsByTagName("telefon");
                if (telefonok.getLength() > 0) {
                    telefonok.item(0).setTextContent(ujTelefon);
                    found = true;
                    System.out.println("  ✓ Telefonszám módosítva\n");
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("  ✗ Nem található ilyen olvasó\n");
        }
    }

    /**
     * 4. Új könyv hozzáadása
     */
    public void ujKonyvHozzaadasa(String id, String cim, String kiadasiEv,
            int oldalszam, String kiadoRef, String szerzoRef, String szerep) {
        System.out.println("4. Új könyv hozzáadása: " + cim);

        NodeList konyvekList = document.getElementsByTagName("konyvek");
        Element konyvekElem = (Element) konyvekList.item(0);

        // Új könyv elem létrehozása
        Element ujKonyv = document.createElement("konyv");
        ujKonyv.setAttribute("Konyv_ID", id);

        // Cím
        Element cimElem = document.createElement("Cim");
        cimElem.setTextContent(cim);
        ujKonyv.appendChild(cimElem);

        // Kiadási év
        Element evElem = document.createElement("Kiadasi_evi");
        evElem.setTextContent(kiadasiEv);
        ujKonyv.appendChild(evElem);

        // Oldalszám
        Element oldalElem = document.createElement("Oldalszam");
        oldalElem.setTextContent(String.valueOf(oldalszam));
        ujKonyv.appendChild(oldalElem);

        // Kiadó referencia
        Element kiadoRefElem = document.createElement("Kiado_ref");
        kiadoRefElem.setTextContent(kiadoRef);
        ujKonyv.appendChild(kiadoRefElem);

        // Szerzők referencia
        Element szerzokRefElem = document.createElement("Szerzok_ref");
        Element szerzoRefElem = document.createElement("szerzo_ref");
        szerzoRefElem.setAttribute("Szerzo_ID", szerzoRef);

        Element szerepElem = document.createElement("Szerep");
        szerepElem.setTextContent(szerep);
        szerzoRefElem.appendChild(szerepElem);

        szerzokRefElem.appendChild(szerzoRefElem);
        ujKonyv.appendChild(szerzokRefElem);

        // Hozzáadás a könyvek listához
        konyvekElem.appendChild(ujKonyv);

        System.out.println("  ✓ Könyv hozzáadva: " + id + " - " + cim + "\n");
    }

    /**
     * 5. Elem törlése - Szerző törlése ID alapján
     */
    public void szerzoTorlese(String szerzoId) {
        System.out.println("5. Szerző törlése: " + szerzoId);

        NodeList szerzok = document.getElementsByTagName("szerzo");
        boolean found = false;

        for (int i = 0; i < szerzok.getLength(); i++) {
            Element szerzo = (Element) szerzok.item(i);
            if (szerzo.getAttribute("Szerzo_ID").equals(szerzoId)) {
                Node parent = szerzo.getParentNode();
                parent.removeChild(szerzo);
                found = true;
                System.out.println("  ✓ Szerző törölve\n");
                break;
            }
        }

        if (!found) {
            System.out.println("  ✗ Nem található ilyen szerző\n");
        }
    }

    /**
     * Módosított XML mentése fájlba
     */
    public void mentes(String outputPath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Formázás beállítása
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(outputPath));

        transformer.transform(source, result);
    }

    // Segédfüggvény
    private static String getElementText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0 && nodeList.item(0) != null) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
