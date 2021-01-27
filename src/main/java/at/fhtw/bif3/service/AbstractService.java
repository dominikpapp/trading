package at.fhtw.bif3.service;

import at.fhtw.bif3.db.dao.AbstractDAO;
import at.fhtw.bif3.exception.EntityNotFoundException;

import java.util.List;

public abstract class AbstractService<P, K> {

    private final AbstractDAO<P, K> abstractDAO;

    protected AbstractService(AbstractDAO<P, K> abstractDAO) {
        this.abstractDAO = abstractDAO;
    }

    public void create(P object) {
        abstractDAO.create(object);
    }

    public void update(P object) {
        abstractDAO.update(object);
    }

    public void delete(K id) {
        abstractDAO.delete(id);
    }

    public P findById(K id) {
        return abstractDAO.read(id);
    }

    public int countEntities() {
        return abstractDAO.countEntities();
    }

    public boolean exists(K id) {
        try {
            abstractDAO.read(id);
        } catch (EntityNotFoundException e) {
            return false;
        }
        return true;
    }

    public P findByField(String fieldName, String fieldValue) {
        return abstractDAO.findByField(fieldName, fieldValue);
    }

    public P findRandom() {
        return abstractDAO.findRandom();
    }

    public List<P> findAll() {
        return abstractDAO.findAll();
    }
}
