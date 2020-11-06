package database.dao;

import database.utils.HibernateSessionFactory;
import org.hibernate.Session;

public abstract class DAO {

    protected Session openSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }

}
