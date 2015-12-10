package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.data.model.UnitOfWorkImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UnitOfWorkJpa extends UnitOfWorkImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void commit() {
        try {
            getEntityManager().getTransaction().begin();
            super.commit();
            getEntityManager().flush();
            getEntityManager().getTransaction().commit();
        }catch (Exception e){
            getEntityManager().getTransaction().rollback();
            // TODO Ajouter gestion des exceptions;
            throw new KernelException("UnitOfWork Commit Exception", e);
        }
    }
}
