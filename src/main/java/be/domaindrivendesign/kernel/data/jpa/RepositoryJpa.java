package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.Repository;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;
import net.jodah.typetools.TypeResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@NoRepositoryBean
@Transactional(readOnly = true)
public class RepositoryJpa<T extends Entity> implements Repository<T>, UnitOfWorkRepository<T> {

    @Autowired
    protected UnitOfWorkJpa unitOfWork;

    private Class<?>[] typeArgs = null;

    public UnitOfWorkJpa getUnitOfWork() {
        return unitOfWork;
    }

    public void setUnitOfWork(UnitOfWorkJpa unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<T> list() {
        List<T> entities = getUnitOfWork().getEntityManager().createQuery(
                "SELECT p FROM "+ getEntityType().getName() +" p").getResultList();
        return entities;
    }

    @Override
    public T getById(UUID id) {
        T entity = (T) getUnitOfWork().getEntityManager().find(getEntityType(), id);
        return entity;
    }

    @Override
    public void insert(T entity) {
        unitOfWork.registerInserted(entity, this);
    }

    @Override
    public void update(T entity) {
        unitOfWork.registerUpdated(entity, this);
    }

    public void delete(UUID id) {
        Entity entity = getById(id);

        if (entity != null) {
            delete(entity);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void delete(Entity entity) {
        unitOfWork.registerRemoved(entity, this);
    }

    @Override
    public void persistNewItem(T entity) {
        unitOfWork.getEntityManager().persist(entity);
    }

    @Override
    public void persistUpdatedItem(T entity) {
        unitOfWork.getEntityManager().merge(entity);
    }

    @Override
    public void persistDeletedItem(T entity) {
        entity.logicalDelete();
        unitOfWork.getEntityManager().unwrap(Session.class).merge(entity);
    }


    protected Class<?> getEntityType() {
        if (this.typeArgs == null) {
            this.typeArgs = TypeResolver.resolveRawArguments(RepositoryJpa.class, this.getClass());
        }
        return this.typeArgs[0];
    }

    protected Class<?> getIdType() {
        if (this.typeArgs == null) {
            this.typeArgs = TypeResolver.resolveRawArguments(RepositoryJpa.class, this.getClass());
        }
        return this.typeArgs[1];
    }

}
