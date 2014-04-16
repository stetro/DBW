package de.stetro.matin.dbw.prak2.daos;


public interface Dao<T> {
    public T getAll() throws Exception;

    public void persist(T object) throws Exception;

    public String getInsertSqlStatement(T t);
}
