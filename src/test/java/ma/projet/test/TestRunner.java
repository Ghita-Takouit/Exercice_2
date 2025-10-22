package ma.projet.test;

import java.util.Scanner;

public class TestRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n  MENU DE TEST - Gestion de Projets          ");
            System.out.println();
            System.out.println("  1. Nettoyer la base de données");
            System.out.println("  2. Test: Gestion des Employés");
            System.out.println("  3. Test: Gestion des Projets");
            System.out.println("  4. Test: Affichage complet");
            System.out.println("  5. Exécuter tous les tests");
            System.out.println("  0. Quitter");
            System.out.println();
            System.out.print("Choisissez une option: ");

            try {
                int choix = scanner.nextInt();
                System.out.println();

                switch (choix) {
                    case 1:
                        System.out.println("Exécution du nettoyage de la base de données...");
                        CleanDatabase.cleanDatabase();
                        break;

                    case 2:
                        System.out.println("Exécution du test des employés...");
                        CleanDatabase.cleanDatabase();
                        TestEmploye.main(new String[]{});
                        break;

                    case 3:
                        System.out.println("Exécution du test des projets...");
                        CleanDatabase.cleanDatabase();
                        TestProjet.main(new String[]{});
                        break;

                    case 4:
                        System.out.println("Exécution du test d'affichage complet...");
                        CleanDatabase.cleanDatabase();
                        TestAffichage.main(new String[]{});
                        break;

                    case 5:
                        System.out.println("Exécution de tous les tests...");
                        System.out.println("\n>>> TEST 1: Gestion des Employés <<<");
                        CleanDatabase.cleanDatabase();
                        TestEmploye.main(new String[]{});

                        System.out.println("\n\n>>> TEST 2: Gestion des Projets <<<");
                        CleanDatabase.cleanDatabase();
                        TestProjet.main(new String[]{});

                        System.out.println("\n\n>>> TEST 3: Affichage Complet <<<");
                        CleanDatabase.cleanDatabase();
                        TestAffichage.main(new String[]{});
                        break;

                    case 0:
                        continuer = false;
                        System.out.println("Au revoir!");
                        break;

                    default:
                        System.out.println("Option invalide. Veuillez choisir entre 0 et 5.");
                }

                if (continuer && choix != 0) {
                    System.out.println("\nAppuyez sur Entrée pour continuer...");
                    scanner.nextLine(); // Consommer le retour à la ligne
                    scanner.nextLine(); // Attendre l'entrée de l'utilisateur
                }

            } catch (Exception e) {
                System.err.println("Erreur: " + e.getMessage());
                scanner.nextLine(); // Nettoyer le buffer
            }
        }

        scanner.close();
    }
}

