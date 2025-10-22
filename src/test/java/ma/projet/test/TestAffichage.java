package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class TestAffichage {

    public static void main(String[] args) {
        try {
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("TEST COMPLET: Affichage de toutes les données   ");

            // Création des données
            System.out.println("\n>>> PHASE 1: Création des données");

            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benjelloun", "Fatima", "0623456789");
            Employe emp3 = new Employe("Chahid", "Hassan", "0634567890");
            Employe emp4 = new Employe("Derraz", "Karim", "0645678901");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            employeService.create(emp4);
            System.out.println("✓ 4 employés créés");

            Projet projet1 = new Projet("Système de Gestion ERP",
                    sdf.parse("2024-01-01"), sdf.parse("2024-06-30"), emp1);
            Projet projet2 = new Projet("Application Mobile E-commerce",
                    sdf.parse("2024-02-01"), sdf.parse("2024-08-31"), emp2);
            Projet projet3 = new Projet("Site Web Institutionnel",
                    sdf.parse("2024-03-01"), sdf.parse("2024-05-31"), emp1);

            projetService.create(projet1);
            projetService.create(projet2);
            projetService.create(projet3);
            System.out.println("✓ 3 projets créés");

            // Tâches pour Projet 1
            Tache t1 = new Tache("Analyse des besoins",
                    sdf.parse("2024-01-01"), sdf.parse("2024-01-31"), 5000.0, projet1);
            Tache t2 = new Tache("Conception",
                    sdf.parse("2024-02-01"), sdf.parse("2024-02-28"), 7000.0, projet1);
            Tache t3 = new Tache("Développement Backend",
                    sdf.parse("2024-03-01"), sdf.parse("2024-04-30"), 15000.0, projet1);
            Tache t4 = new Tache("Tests",
                    sdf.parse("2024-05-01"), sdf.parse("2024-05-31"), 8000.0, projet1);

            // Tâches pour Projet 2
            Tache t5 = new Tache("Design UI/UX",
                    sdf.parse("2024-02-01"), sdf.parse("2024-03-15"), 6000.0, projet2);
            Tache t6 = new Tache("Développement iOS",
                    sdf.parse("2024-03-16"), sdf.parse("2024-06-30"), 20000.0, projet2);
            Tache t7 = new Tache("Développement Android",
                    sdf.parse("2024-04-01"), sdf.parse("2024-07-15"), 18000.0, projet2);

            // Tâches pour Projet 3
            Tache t8 = new Tache("Maquettage",
                    sdf.parse("2024-03-01"), sdf.parse("2024-03-15"), 3000.0, projet3);
            Tache t9 = new Tache("Développement Frontend",
                    sdf.parse("2024-03-16"), sdf.parse("2024-04-30"), 8000.0, projet3);
            Tache t10 = new Tache("Intégration CMS",
                    sdf.parse("2024-05-01"), sdf.parse("2024-05-31"), 5000.0, projet3);

            tacheService.create(t1); tacheService.create(t2); tacheService.create(t3);
            tacheService.create(t4); tacheService.create(t5); tacheService.create(t6);
            tacheService.create(t7); tacheService.create(t8); tacheService.create(t9);
            tacheService.create(t10);
            System.out.println("✓ 10 tâches créées");

            // Affectations
            employeTacheService.create(new EmployeTache(emp1, t1,
                    sdf.parse("2024-01-02"), sdf.parse("2024-01-30")));
            employeTacheService.create(new EmployeTache(emp1, t2,
                    sdf.parse("2024-02-01"), sdf.parse("2024-02-27")));
            employeTacheService.create(new EmployeTache(emp2, t3,
                    sdf.parse("2024-03-01"), sdf.parse("2024-04-28")));
            employeTacheService.create(new EmployeTache(emp3, t4,
                    sdf.parse("2024-05-02"), sdf.parse("2024-05-29")));
            employeTacheService.create(new EmployeTache(emp2, t5,
                    sdf.parse("2024-02-01"), sdf.parse("2024-03-14")));
            employeTacheService.create(new EmployeTache(emp3, t6,
                    sdf.parse("2024-03-16"), sdf.parse("2024-06-28")));
            employeTacheService.create(new EmployeTache(emp4, t7,
                    sdf.parse("2024-04-01"), sdf.parse("2024-07-14")));
            employeTacheService.create(new EmployeTache(emp1, t8,
                    sdf.parse("2024-03-01"), sdf.parse("2024-03-14")));
            employeTacheService.create(new EmployeTache(emp4, t9,
                    sdf.parse("2024-03-16"), sdf.parse("2024-04-29")));
            employeTacheService.create(new EmployeTache(emp2, t10,
                    sdf.parse("2024-05-01"), sdf.parse("2024-05-30")));
            System.out.println("✓ 10 affectations créées");

            // AFFICHAGES
            System.out.println("\n\n>>> PHASE 2: Affichage des Employés");
            List<Employe> employes = employeService.findAll();
            for (Employe e : employes) {
                System.out.println("\n Employé: " + e.getNom() + " " + e.getPrenom());
                System.out.println("  Tel: " + e.getTelephone());

                // Projets gérés
                List<Projet> projetsGeres = employeService.getProjetsGeres(e.getId());
                if (projetsGeres != null && !projetsGeres.isEmpty()) {
                    System.out.println("│  Projets gérés:");
                    for (Projet p : projetsGeres) {
                        System.out.println("│    • " + p.getNom());
                    }
                }

                // Tâches réalisées
                List<Tache> tachesRealisees = employeService.getTachesRealisees(e.getId());
                if (tachesRealisees != null && !tachesRealisees.isEmpty()) {
                    System.out.println("│  Tâches réalisées: " + tachesRealisees.size());
                    double total = 0;
                    for (Tache t : tachesRealisees) {
                        System.out.println("│    • " + t.getNom() + " (" + t.getPrix() + " MAD)");
                        total += t.getPrix();
                    }
                    System.out.println("│  Total tâches: " + total + " MAD");
                }
            }

            System.out.println("\n\n>>> PHASE 3: Affichage des Projets");
            List<Projet> projets = projetService.findAll();
            for (Projet p : projets) {
                System.out.println("PROJET: " + p.getNom());
                System.out.println(" Chef: " + p.getEmploye().getNom() + " " + p.getEmploye().getPrenom());
                System.out.println("Période: " + sdf.format(p.getDateDebut()) + " → " + sdf.format(p.getDateFin()));

                // Tâches planifiées
                List<Tache> tachesPlan = projetService.getTachesPlanifiees(p.getId());
                if (tachesPlan != null && !tachesPlan.isEmpty()) {
                    System.out.println("┃ TÂCHES PLANIFIÉES:");
                    double budgetTotal = 0;
                    for (Tache t : tachesPlan) {
                        System.out.println("  → " + t.getNom());
                        System.out.println("   Prévu: " + sdf.format(t.getDateDebutPrevue()) +
                                         " → " + sdf.format(t.getDateFinPrevue()));
                        System.out.println("  Prix: " + t.getPrix() + " MAD");
                        budgetTotal += t.getPrix();
                    }
                    System.out.println("Budget total: " + budgetTotal + " MAD");
                }


                // Tâches réalisées
                List<EmployeTache> tachesReal = projetService.getTachesRealisees(p.getId());
                if (tachesReal != null && !tachesReal.isEmpty()) {
                    System.out.println("TÂCHES RÉALISÉES:");
                    double coutTotal = 0;
                    for (EmployeTache et : tachesReal) {
                        System.out.println("  → " + et.getTache().getNom());
                        System.out.println("   Employé: " + et.getEmploye().getNom() + " " +
                                         et.getEmploye().getPrenom());
                        System.out.println("┃     Réalisé: " + sdf.format(et.getDateDebutReelle()) +
                                         " → " + sdf.format(et.getDateFinReelle()));
                        System.out.println("┃     Prix: " + et.getTache().getPrix() + " MAD");
                        coutTotal += et.getTache().getPrix();
                    }
                    System.out.println("┃   Coût total: " + coutTotal + " MAD");
                }
            }

            System.out.println("\n\n>>> PHASE 4: Affichage des Tâches");
            List<Tache> taches = tacheService.findAll();
            System.out.println("Total des tâches: " + taches.size());
            for (Tache t : taches) {
                System.out.println("\n  ► " + t.getNom());
                System.out.println("    Projet: " + t.getProjet().getNom());
                System.out.println("    Dates prévues: " + sdf.format(t.getDateDebutPrevue()) +
                                 " → " + sdf.format(t.getDateFinPrevue()));
                System.out.println("    Prix: " + t.getPrix() + " MAD");
            }
            System.out.println("\n\n>>> PHASE 5: Affichage des Affectations");
            List<EmployeTache> affectations = employeTacheService.findAll();
            System.out.println("\n\n PHASE 5: Affichage des Affectations");
            for (EmployeTache et : affectations) {
                System.out.println("\n  ► Employé: " + et.getEmploye().getNom() + " " +
                                 et.getEmploye().getPrenom());
                System.out.println("    Tâche: " + et.getTache().getNom());
                System.out.println("    Projet: " + et.getTache().getProjet().getNom());
                System.out.println("    Dates réelles: " + sdf.format(et.getDateDebutReelle()) +
                                 " → " + sdf.format(et.getDateFinReelle()));
            System.out.println("\n\n║       TEST AFFICHAGE COMPLET: TERMINÉ AVEC SUCCÈS ✓        ║\n");

            System.out.println("   TEST AFFICHAGE COMPLET: TERMINÉ AVEC SUCCÈS ");

        } catch (Exception e) {
            System.err.println("✗ ERREUR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}

