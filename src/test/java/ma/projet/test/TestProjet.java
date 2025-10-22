package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestProjet {

    public static void main(String[] args) {
        try {
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("   TEST: Gestion des Projets               ");

            // Création des données de test
            System.out.println("\n1. Préparation des données de test:");

            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benjelloun", "Fatima", "0623456789");
            Employe emp3 = new Employe("Chahid", "Hassan", "0634567890");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);

            Projet projet1 = new Projet("Système de Gestion ERP",
                    sdf.parse("2024-01-01"),
                    sdf.parse("2024-06-30"),
                    emp1);
            Projet projet2 = new Projet("Application Mobile E-commerce",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-08-31"),
                    emp2);

            projetService.create(projet1);
            projetService.create(projet2);
            System.out.println("✓ Données de base créées");

            // Création des tâches pour le projet 1
            Tache tache1 = new Tache("Analyse des besoins",
                    sdf.parse("2024-01-01"),
                    sdf.parse("2024-01-31"),
                    5000.0, projet1);
            Tache tache2 = new Tache("Conception de l'architecture",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-02-28"),
                    7000.0, projet1);
            Tache tache3 = new Tache("Développement Backend",
                    sdf.parse("2024-03-01"),
                    sdf.parse("2024-04-30"),
                    15000.0, projet1);
            Tache tache4 = new Tache("Tests et Validation",
                    sdf.parse("2024-05-01"),
                    sdf.parse("2024-05-31"),
                    8000.0, projet1);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            System.out.println("✓ Tâches du projet 1 créées");

            // Création des tâches pour le projet 2
            Tache tache5 = new Tache("Design UI/UX",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-03-15"),
                    6000.0, projet2);
            Tache tache6 = new Tache("Développement Mobile iOS",
                    sdf.parse("2024-03-16"),
                    sdf.parse("2024-06-30"),
                    20000.0, projet2);
            Tache tache7 = new Tache("Développement Mobile Android",
                    sdf.parse("2024-04-01"),
                    sdf.parse("2024-07-15"),
                    18000.0, projet2);

            tacheService.create(tache5);
            tacheService.create(tache6);
            tacheService.create(tache7);
            System.out.println("✓ Tâches du projet 2 créées");

            // Affectation des tâches avec dates réelles
            EmployeTache et1 = new EmployeTache(emp1, tache1,
                    sdf.parse("2024-01-02"), sdf.parse("2024-01-30"));
            EmployeTache et2 = new EmployeTache(emp1, tache2,
                    sdf.parse("2024-02-01"), sdf.parse("2024-02-27"));
            EmployeTache et3 = new EmployeTache(emp2, tache3,
                    sdf.parse("2024-03-01"), sdf.parse("2024-04-28"));
            EmployeTache et4 = new EmployeTache(emp3, tache4,
                    sdf.parse("2024-05-02"), sdf.parse("2024-05-29"));
            EmployeTache et5 = new EmployeTache(emp2, tache5,
                    sdf.parse("2024-02-01"), sdf.parse("2024-03-14"));
            EmployeTache et6 = new EmployeTache(emp3, tache6,
                    sdf.parse("2024-03-16"), sdf.parse("2024-06-28"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            employeTacheService.create(et6);
            System.out.println("✓ Affectations créées");

            // Test des méthodes de ProjetService
            System.out.println("TEST: Méthodes de ProjetService                  ");
            System.out.println("\n\n║          TEST: Méthodes de ProjetService                   ║");
            System.out.println("\n2. Tâches planifiées pour le projet: " + projet1.getNom());
            List<Tache> tachesPlanifiees = projetService.getTachesPlanifiees(projet1.getId());
            if (tachesPlanifiees != null && !tachesPlanifiees.isEmpty()) {
                double totalPrix = 0;
                for (Tache t : tachesPlanifiees) {
                    System.out.println("  → " + t.getNom());
                    System.out.println("    • Date prévue: " + sdf.format(t.getDateDebutPrevue()) +
                                     " → " + sdf.format(t.getDateFinPrevue()));
                    System.out.println("    • Prix: " + t.getPrix() + " MAD");
                    totalPrix += t.getPrix();
                }
                System.out.println("\n  ➤ Budget total prévu: " + totalPrix + " MAD");
            } else {
                System.out.println("  ✗ Aucune tâche planifiée");
            }

            // Test 2: Tâches réalisées pour un projet avec dates réelles
            System.out.println("\n3. Tâches réalisées pour le projet: " + projet1.getNom());
            List<EmployeTache> tachesRealisees = projetService.getTachesRealisees(projet1.getId());
            if (tachesRealisees != null && !tachesRealisees.isEmpty()) {
                double totalRealise = 0;
                for (EmployeTache et : tachesRealisees) {
                    System.out.println("  → Tâche: " + et.getTache().getNom());
                    System.out.println("    • Employé: " + et.getEmploye().getNom() + " " +
                                     et.getEmploye().getPrenom());
                    System.out.println("    • Dates réelles: " + sdf.format(et.getDateDebutReelle()) +
                                     " → " + sdf.format(et.getDateFinReelle()));
                    System.out.println("    • Prix: " + et.getTache().getPrix() + " MAD");
                    totalRealise += et.getTache().getPrix();
                    System.out.println();
                }
                System.out.println("  ➤ Coût total réalisé: " + totalRealise + " MAD");
            } else {
                System.out.println("  ✗ Aucune tâche réalisée");
            }

            // Test pour le projet 2
            System.out.println("\n4. Tâches planifiées pour le projet: " + projet2.getNom());
            List<Tache> tachesPlanifiees2 = projetService.getTachesPlanifiees(projet2.getId());
            if (tachesPlanifiees2 != null && !tachesPlanifiees2.isEmpty()) {
                double totalPrix = 0;
                for (Tache t : tachesPlanifiees2) {
                    System.out.println("  → " + t.getNom());
                    System.out.println("    • Date prévue: " + sdf.format(t.getDateDebutPrevue()) +
                                     " → " + sdf.format(t.getDateFinPrevue()));
                    System.out.println("    • Prix: " + t.getPrix() + " MAD");
                    totalPrix += t.getPrix();
                }
                System.out.println("\n  ➤ Budget total prévu: " + totalPrix + " MAD");
            }

            System.out.println("\n5. Tâches réalisées pour le projet: " + projet2.getNom());
            List<EmployeTache> tachesRealisees2 = projetService.getTachesRealisees(projet2.getId());
            if (tachesRealisees2 != null && !tachesRealisees2.isEmpty()) {
                double totalRealise = 0;
                for (EmployeTache et : tachesRealisees2) {
                    System.out.println("  → Tâche: " + et.getTache().getNom());
                    System.out.println("    • Employé: " + et.getEmploye().getNom() + " " +
                                     et.getEmploye().getPrenom());
                    System.out.println("    • Dates réelles: " + sdf.format(et.getDateDebutReelle()) +
                                     " → " + sdf.format(et.getDateFinReelle()));
                    System.out.println("    • Prix: " + et.getTache().getPrix() + " MAD");
                    totalRealise += et.getTache().getPrix();
                    System.out.println();
                }
                System.out.println("  ➤ Coût total réalisé: " + totalRealise + " MAD");
            }

            // Liste de tous les projets
            System.out.println("\n6. Liste de tous les projets:");
            List<Projet> projets = projetService.findAll();
            if (projets != null && !projets.isEmpty()) {
                for (Projet p : projets) {
                    System.out.println("  → " + p.getNom());
                    System.out.println("    • Chef de projet: " + p.getEmploye().getNom() + " " +
                                     p.getEmploye().getPrenom());
                    System.out.println("    • Période: " + sdf.format(p.getDateDebut()) +
                                     " → " + sdf.format(p.getDateFin()));
                }
            }

            System.out.println("       TEST PROJET: TERMINÉ AVEC SUCCÈS ✓             ");

            System.out.println("\n║          TEST PROJET: TERMINÉ AVEC SUCCÈS ✓                ║\n");
        } finally {
            HibernateUtil.shutdown();
        }
    }
}

