package database.dao;

import database.utils.HibernateSessionFactory;
import org.hibernate.Session;

/**
 * Общий класс слоев отправки запросов к базе данных
 */
public abstract class DAO {

    /**
     * Сокращение для открытия сессии
     * @return            Сессия взаимодействия с базой данных
     */
    protected Session openSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }
}