package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Initialize services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("=== Création des Employés ===");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benjelloun", "Fatima", "0623456789");
            Employe emp3 = new Employe("Chahid", "Hassan", "0634567890");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            System.out.println("Employés créés avec succès!");

            System.out.println("\n=== Création des Projets ===");
            Projet projet1 = new Projet("Système de Gestion",
                    sdf.parse("2024-01-01"),
                    sdf.parse("2024-06-30"),
                    emp1);
            Projet projet2 = new Projet("Application Mobile",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-08-31"),
                    emp2);

            projetService.create(projet1);
            projetService.create(projet2);
            System.out.println("Projets créés avec succès!");

            System.out.println("\n=== Création des Tâches ===");
            Tache tache1 = new Tache("Analyse des besoins",
                    sdf.parse("2024-01-01"),
                    sdf.parse("2024-01-31"),
                    5000.0,
                    projet1);
            Tache tache2 = new Tache("Conception",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-02-28"),
                    7000.0,
                    projet1);
            Tache tache3 = new Tache("Développement Backend",
                    sdf.parse("2024-03-01"),
                    sdf.parse("2024-04-30"),
                    15000.0,
                    projet1);
            Tache tache4 = new Tache("Design UI/UX",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-03-15"),
                    6000.0,
                    projet2);
            Tache tache5 = new Tache("Développement Mobile",
                    sdf.parse("2024-03-16"),
                    sdf.parse("2024-06-30"),
                    20000.0,
                    projet2);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            tacheService.create(tache5);
            System.out.println("Tâches créées avec succès!");

            System.out.println("\n=== Affectation des Tâches aux Employés ===");
            EmployeTache et1 = new EmployeTache(emp1, tache1,
                    sdf.parse("2024-01-02"),
                    sdf.parse("2024-01-30"));
            EmployeTache et2 = new EmployeTache(emp1, tache2,
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-02-27"));
            EmployeTache et3 = new EmployeTache(emp2, tache3,
                    sdf.parse("2024-03-01"),
                    sdf.parse("2024-04-28"));
            EmployeTache et4 = new EmployeTache(emp2, tache4,
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-03-14"));
            EmployeTache et5 = new EmployeTache(emp3, tache5,
                    sdf.parse("2024-03-16"),
                    sdf.parse("2024-06-25"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            System.out.println("Tâches affectées aux employés avec succès!");

            // Tests des méthodes spécifiques
            System.out.println("TESTS DES MÉTHODES DEMANDÉES ");

            // 1. Afficher la liste des tâches réalisées par un employé
            System.out.println("\n1. Liste des tâches réalisées par " + emp1.getNom() + " " + emp1.getPrenom() + ":");
            List<Tache> tachesEmp1 = employeService.getTachesRealisees(emp1.getId());
            if (tachesEmp1 != null && !tachesEmp1.isEmpty()) {
                for (Tache t : tachesEmp1) {
                    System.out.println("   - " + t.getNom() + " (Prix: " + t.getPrix() + " MAD)");
                }
            } else {
                System.out.println("   Aucune tâche réalisée.");
            }

            // 2. Afficher la liste des projets gérés par un employé
            System.out.println("\n2. Liste des projets gérés par " + emp1.getNom() + " " + emp1.getPrenom() + ":");
            List<Projet> projetsEmp1 = employeService.getProjetsGeres(emp1.getId());
            if (projetsEmp1 != null && !projetsEmp1.isEmpty()) {
                for (Projet p : projetsEmp1) {
                    System.out.println("   - " + p.getNom() + " (Début: " + sdf.format(p.getDateDebut()) +
                            ", Fin: " + sdf.format(p.getDateFin()) + ")");
                }
            } else {
                System.out.println("   Aucun projet géré.");
            }

            // 3. Afficher la liste des tâches planifiées pour un projet
            System.out.println("\n3. Liste des tâches planifiées pour le projet '" + projet1.getNom() + "':");
            List<Tache> tachesPlanifiees = projetService.getTachesPlanifiees(projet1.getId());
            if (tachesPlanifiees != null && !tachesPlanifiees.isEmpty()) {
                for (Tache t : tachesPlanifiees) {
                    System.out.println("   - " + t.getNom());
                    System.out.println("     Date début prévue: " + sdf.format(t.getDateDebutPrevue()));
                    System.out.println("     Date fin prévue: " + sdf.format(t.getDateFinPrevue()));
                    System.out.println("     Prix: " + t.getPrix() + " MAD");
                }
            } else {
                System.out.println("   Aucune tâche planifiée.");
            }

            // 4. Afficher la liste des tâches réalisées avec les dates réelles
            System.out.println("\n4. Liste des tâches réalisées pour le projet '" + projet1.getNom() + "' (avec dates réelles):");
            List<EmployeTache> tachesRealisees = projetService.getTachesRealisees(projet1.getId());
            if (tachesRealisees != null && !tachesRealisees.isEmpty()) {
                for (EmployeTache et : tachesRealisees) {
                    System.out.println("   - Tâche: " + et.getTache().getNom());
                    System.out.println("     Employé: " + et.getEmploye().getNom() + " " + et.getEmploye().getPrenom());
                    System.out.println("     Date début réelle: " + sdf.format(et.getDateDebutReelle()));
                    System.out.println("     Date fin réelle: " + sdf.format(et.getDateFinReelle()));
                    System.out.println("     Prix: " + et.getTache().getPrix() + " MAD");
                    System.out.println();
                }
            } else {
                System.out.println("   Aucune tâche réalisée.");
            }

            System.out.println("\n5. Liste des tâches réalisées par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            List<Tache> tachesEmp2 = employeService.getTachesRealisees(emp2.getId());
            if (tachesEmp2 != null && !tachesEmp2.isEmpty()) {
                for (Tache t : tachesEmp2) {
                    System.out.println("   - " + t.getNom() + " (Prix: " + t.getPrix() + " MAD)");
                }
            }

            System.out.println("\n6. Liste des projets gérés par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            List<Projet> projetsEmp2 = employeService.getProjetsGeres(emp2.getId());
            if (projetsEmp2 != null && !projetsEmp2.isEmpty()) {
                for (Projet p : projetsEmp2) {
                    System.out.println("   - " + p.getNom() + " (Début: " + sdf.format(p.getDateDebut()) +
                            ", Fin: " + sdf.format(p.getDateFin()) + ")");
                }
            }

            System.out.println("\n========================================");
            System.out.println("Application terminée avec succès!");
            System.out.println("========================================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown Hibernate
            HibernateUtil.shutdown();
        }
    }
}

