package at.fhtw.bif3.db.dao;

import at.fhtw.bif3.exception.DBException;

public interface GenericDAO<P, K> {
    boolean create(P object) throws DBException;

    P read(K id) throws DBException;

    void update(P object) throws DBException;

    void delete(K id) throws DBException;
}
