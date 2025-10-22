package ma.projet.test;

import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CleanDatabase {

    public static void main(String[] args) {
        cleanDatabase();
    }

    public static void cleanDatabase() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            System.out.println("Nettoyage de la base de données...");

            // Suppression dans l'ordre pour respecter les contraintes de clés étrangères
            session.createQuery("DELETE FROM EmployeTache").executeUpdate();
            System.out.println("✓ Table employe_tache nettoyée");

            session.createQuery("DELETE FROM Tache").executeUpdate();
            System.out.println("✓ Table tache nettoyée");

            session.createQuery("DELETE FROM Projet").executeUpdate();
            System.out.println("✓ Table projet nettoyée");

            session.createQuery("DELETE FROM Employe").executeUpdate();
            System.out.println("✓ Table employe nettoyée");

            transaction.commit();
            System.out.println("\n✓ Base de données nettoyée avec succès!\n");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Erreur lors du nettoyage: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}

