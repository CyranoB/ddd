package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;

import javax.persistence.EntityManager;

/**
 * Created by asmol on 16-12-15.
 */
public interface UnitOfWorkJpa extends UnitOfWork {

    EntityManager getEntityManager();
}
