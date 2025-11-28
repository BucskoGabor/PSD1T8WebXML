import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTTransform {

    public static void main(String[] args) {
        // Ensure output directory exists
        new File("output").mkdirs();

        // Define transformations: {xmlFile, xslFile, outputFile}
        String[][] transformations = {
                // Task 1 - Student data
                { "hallgatoPSD1T8.xml", "hallgatoPSD1T8.xsl", "output/hallgatoPSD1T8.html" },
                { "hallgatoPSD1T8.xml", "hallgatoPSD1T8.xsl", "output/hallgatoPSD1T8.out.xml" }, // Also generate
                                                                                                 // .out.xml as
                                                                                                 // requested

                // Task 2 - Timetable
                { "orarendPSD1T8.xml", "orarendPSD1T8.xsl", "output/orarendPSD1T8.html" },
                { "orarendPSD1T8.xml", "orarendPSD1T8.xsl", "output/orarendPSD1T8.out.xml" }, // Also generate .out.xml
                                                                                              // as requested

                // Task 3 - Cars
                { "autokPSD1T8.xml", "autokPSD1T8.xsl", "output/autokPSD1T8.html" },
                { "autokPSD1T8.xml", "autok1PSD1T8.xsl", "output/autok1PSD1T8.html" },
                { "autokPSD1T8.xml", "autok2PSD1T8.xsl", "output/autok2PSD1T8.html" },
                { "autokPSD1T8.xml", "autok3PSD1T8.xsl", "output/autok3PSD1T8.html" },
                { "autokPSD1T8.xml", "autok4PSD1T8.xsl", "output/autok4PSD1T8.html" },
                { "autokPSD1T8.xml", "autok5PSD1T8.xsl", "output/autok5PSD1T8.html" },
                { "autokPSD1T8.xml", "autok6PSD1T8.xsl", "output/autok6PSD1T8.html" },
                { "autokPSD1T8.xml", "autok7PSD1T8.xsl", "output/autok7PSD1T8.xml" }
        };

        System.out.println("Starting XSLT transformations...\n");

        int successCount = 0;
        for (String[] task : transformations) {
            String xmlFile = task[0];
            String xslFile = task[1];
            String outputFile = task[2];

            if (transform(xmlFile, xslFile, outputFile)) {
                successCount++;
            }
        }

        System.out.println(
                "\n" + successCount + "/" + transformations.length + " transformations completed successfully!");
    }

    private static boolean transform(String xmlPath, String xslPath, String outputPath) {
        try {
            File xmlFile = new File(xmlPath);
            File xslFile = new File(xslPath);
            File outputFile = new File(outputPath);

            if (!xmlFile.exists()) {
                System.out.println("X Error: XML file not found: " + xmlPath);
                return false;
            }
            if (!xslFile.exists()) {
                System.out.println("X Error: XSL file not found: " + xslPath);
                return false;
            }

            Source xmlSource = new StreamSource(xmlFile);
            Source xslSource = new StreamSource(xslFile);
            Result result = new StreamResult(outputFile);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xslSource);

            // Set output properties if needed, though usually defined in XSLT
            // transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            transformer.transform(xmlSource, result);

            System.out.println("V Successfully created: " + outputPath);
            return true;

        } catch (TransformerException e) {
            System.out.println("X Error transforming " + xmlPath + " with " + xslPath + ": " + e.getMessage());
            // e.printStackTrace(); // Uncomment for debug
            return false;
        } catch (Exception e) {
            System.out.println("X Unexpected error: " + e.getMessage());
            return false;
        }
    }
}
