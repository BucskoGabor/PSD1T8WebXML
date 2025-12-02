package psd1t8.domparse.hu;

import java.util.Scanner;

/**
 * PSD1T8 DOM Parse - Főprogram
 * 
 * Készítette: PSD1T8
 * A program XML fájlokat dolgoz fel DOM parser használatával.
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n╔═══════════════════════════════════════════════╗");
            System.out.println("║   PSD1T8 - DOM XML Feldolgozó Program         ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("1. XML Beolvasás és Megjelenítés (DomRead)");
            System.out.println("2. XML Lekérdezések (DomQuery)");
            System.out.println("3. XML Módosítás (DomModify)");
            System.out.println("4. Összes művelet futtatása");
            System.out.println("0. Kilépés");
            System.out.println();
            System.out.print("Válasszon: ");

            int choice = scanner.nextInt();
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        System.out.println("═══ DOM READ ═══\n");
                        DomRead.main(null);
                        break;
                    case 2:
                        System.out.println("═══ DOM QUERY ═══\n");
                        DomQuery.main(null);
                        break;
                    case 3:
                        System.out.println("═══ DOM MODIFY ═══\n");
                        DomModify.main(null);
                        break;
                    case 4:
                        System.out.println("═══ ÖSSZES MŰVELET ═══\n");
                        System.out.println("\n--- 1. DOM READ ---\n");
                        DomRead.main(null);
                        System.out.println("\n--- 2. DOM QUERY ---\n");
                        DomQuery.main(null);
                        System.out.println("\n--- 3. DOM MODIFY ---\n");
                        DomModify.main(null);
                        break;
                    case 0:
                        System.out.println("Kilépés...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Érvénytelen választás!");
                }
            } catch (Exception e) {
                System.err.println("Hiba történt: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nNyomjon Enter-t a folytatáshoz...");
            scanner.nextLine(); // Consume newline
            scanner.nextLine(); // Wait for Enter
        }
    }
}
