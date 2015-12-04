package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.model.UnitOfWorkImpl;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.SynchronizationType;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * Created by eddie on 03/12/15.
 */
public class UnitOfWorkJpa extends UnitOfWorkImpl {

    @PersistenceContext(synchronization= SynchronizationType.UNSYNCHRONIZED, type = PersistenceContextType.EXTENDED)
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
        }
    }
}