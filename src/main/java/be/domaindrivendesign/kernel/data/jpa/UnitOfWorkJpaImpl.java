package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.data.model.UnitOfWorkImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class UnitOfWorkJpaImpl extends UnitOfWorkImpl implements UnitOfWorkJpa {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /*@Override
    @Transactional
    public void commit() {
        try {
            super.commit();
            getEntityManager().flush();
        } catch (Exception e) {
            throw new KernelException("UnitOfWork Commit Exception", e);
        }
    }*/
}
