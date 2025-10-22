package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestEmploye {

    public static void main(String[] args) {
        try {
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("  TEST: Gestion des Employés    ");

            // Création des employés
            System.out.println("\n1. Création des Employés:");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benjelloun", "Fatima", "0623456789");
            Employe emp3 = new Employe("Chahid", "Hassan", "0634567890");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);

            System.out.println("✓ Employé 1: " + emp1.getNom() + " " + emp1.getPrenom() + " - Tel: " + emp1.getTelephone());
            System.out.println("✓ Employé 2: " + emp2.getNom() + " " + emp2.getPrenom() + " - Tel: " + emp2.getTelephone());
            System.out.println("✓ Employé 3: " + emp3.getNom() + " " + emp3.getPrenom() + " - Tel: " + emp3.getTelephone());

            // Affichage de tous les employés
            System.out.println("\n2. Liste de tous les Employés:");
            List<Employe> employes = employeService.findAll();
            if (employes != null && !employes.isEmpty()) {
                for (Employe e : employes) {
                    System.out.println("  → ID: " + e.getId() + " | " + e.getNom() + " " + e.getPrenom() +
                                     " | Tel: " + e.getTelephone());
                }
            }

            // Création des projets
            System.out.println("\n3. Création des Projets:");
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

            System.out.println("✓ Projet 1: " + projet1.getNom() + " (Chef: " + emp1.getNom() + ")");
            System.out.println("✓ Projet 2: " + projet2.getNom() + " (Chef: " + emp2.getNom() + ")");

            // Création des tâches
            System.out.println("\n4. Création des Tâches:");
            Tache tache1 = new Tache("Analyse des besoins",
                    sdf.parse("2024-01-01"),
                    sdf.parse("2024-01-31"),
                    5000.0, projet1);
            Tache tache2 = new Tache("Conception",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-02-28"),
                    7000.0, projet1);
            Tache tache3 = new Tache("Développement Backend",
                    sdf.parse("2024-03-01"),
                    sdf.parse("2024-04-30"),
                    15000.0, projet1);
            Tache tache4 = new Tache("Design UI/UX",
                    sdf.parse("2024-02-01"),
                    sdf.parse("2024-03-15"),
                    6000.0, projet2);
            Tache tache5 = new Tache("Développement Mobile",
                    sdf.parse("2024-03-16"),
                    sdf.parse("2024-06-30"),
                    20000.0, projet2);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            tacheService.create(tache5);

            System.out.println("✓ 5 tâches créées avec succès");

            // Affectation des tâches
            System.out.println("\n5. Affectation des Tâches aux Employés:");
            EmployeTache et1 = new EmployeTache(emp1, tache1,
                    sdf.parse("2024-01-02"), sdf.parse("2024-01-30"));
            EmployeTache et2 = new EmployeTache(emp1, tache2,
                    sdf.parse("2024-02-01"), sdf.parse("2024-02-27"));
            EmployeTache et3 = new EmployeTache(emp2, tache3,
                    sdf.parse("2024-03-01"), sdf.parse("2024-04-28"));
            EmployeTache et4 = new EmployeTache(emp2, tache4,
                    sdf.parse("2024-02-01"), sdf.parse("2024-03-14"));
            EmployeTache et5 = new EmployeTache(emp3, tache5,
                    sdf.parse("2024-03-16"), sdf.parse("2024-06-25"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);

            System.out.println("✓ 5 affectations créées avec succès");

            // Test des méthodes spécifiques
            System.out.println("\n\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║          TEST: Méthodes de Consultation                    ║");
            System.out.println("\n\n TEST: Méthodes de Consultation                 ");
            System.out.println("\n6. Tâches réalisées par " + emp1.getNom() + " " + emp1.getPrenom() + ":");
            List<Tache> tachesEmp1 = employeService.getTachesRealisees(emp1.getId());
            if (tachesEmp1 != null && !tachesEmp1.isEmpty()) {
                for (Tache t : tachesEmp1) {
                    System.out.println("  → " + t.getNom() + " - Prix: " + t.getPrix() + " MAD");
                }
            } else {
                System.out.println("  ✗ Aucune tâche réalisée");
            }

            // Test 2: Projets gérés par un employé
            System.out.println("\n7. Projets gérés par " + emp1.getNom() + " " + emp1.getPrenom() + ":");
            List<Projet> projetsEmp1 = employeService.getProjetsGeres(emp1.getId());
            if (projetsEmp1 != null && !projetsEmp1.isEmpty()) {
                for (Projet p : projetsEmp1) {
                    System.out.println("  → " + p.getNom());
                    System.out.println("    Du " + sdf.format(p.getDateDebut()) + " au " + sdf.format(p.getDateFin()));
                }
            } else {
                System.out.println("  ✗ Aucun projet géré");
            }

            System.out.println("\n8. Tâches réalisées par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            List<Tache> tachesEmp2 = employeService.getTachesRealisees(emp2.getId());
            if (tachesEmp2 != null && !tachesEmp2.isEmpty()) {
                for (Tache t : tachesEmp2) {
                    System.out.println("  → " + t.getNom() + " - Prix: " + t.getPrix() + " MAD");
                }
            }

            System.out.println("\n9. Projets gérés par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            List<Projet> projetsEmp2 = employeService.getProjetsGeres(emp2.getId());
            if (projetsEmp2 != null && !projetsEmp2.isEmpty()) {
                for (Projet p : projetsEmp2) {
                    System.out.println("  → " + p.getNom());
                    System.out.println("    Du " + sdf.format(p.getDateDebut()) + " au " + sdf.format(p.getDateFin()));
                }
            }

            System.out.println("\n        TEST EMPLOYÉ: TERMINÉ AVEC SUCCÈS \n");

        } catch (Exception e) {
        }
    }
}

