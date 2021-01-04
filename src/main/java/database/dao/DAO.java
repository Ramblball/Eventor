package database.dao;

/**
 * Интерфейс, описывающий основные методы отправки запросов к базе данных
 */
public interface DAO<T> {

    T findById(int id);

    void create(T entity);

    void update(T entity);

    void remove(T entity);
}