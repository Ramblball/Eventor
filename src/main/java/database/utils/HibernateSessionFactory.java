package database.utils;

import database.model.Event;
import database.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Класс фабрики создания классов сессий взаимодействия с базой данных
 */
public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    /**
     * Метод для получения сессии работы с базой данных
     * @return              Объект сессии работы с базой данных
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Event.class);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
                configuration.setProperty("show_sql", "true");
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}