package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ma.projet.classes.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Load application.properties
                Properties properties = loadProperties();

                // Hibernate settings
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, properties.getProperty("db.driver"));
                settings.put(Environment.URL, properties.getProperty("db.url"));
                settings.put(Environment.USER, properties.getProperty("db.username"));
                settings.put(Environment.PASS, properties.getProperty("db.password"));
                settings.put(Environment.DIALECT, properties.getProperty("hibernate.dialect"));
                settings.put(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl.auto"));
                settings.put(Environment.SHOW_SQL, properties.getProperty("hibernate.show_sql"));
                settings.put(Environment.FORMAT_SQL, properties.getProperty("hibernate.format_sql"));
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                configuration.setProperties(settings);

                // Add annotated classes
                configuration.addAnnotatedClass(Employe.class);
                configuration.addAnnotatedClass(Projet.class);
                configuration.addAnnotatedClass(Tache.class);
                configuration.addAnnotatedClass(EmployeTache.class);

                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            // Load from classpath
            properties.load(HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

