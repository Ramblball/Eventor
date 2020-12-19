package database.dao;

import database.utils.HibernateSessionFactory;
import org.hibernate.Session;

/**
 * Класс, описывающий общую логику отправки запросов к базе данных
 */
public abstract class DAO {

    /**
     * Метод создания сессии работы с пользователем
     * @return            Сессия взаимодействия с базой данных
     */
    protected Session openSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }
}