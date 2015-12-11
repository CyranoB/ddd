package be.domaindrivendesign.kernel.data.jpa;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;
import net.jodah.typetools.TypeResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@Transactional(readOnly = true)
public class RepositoryJpa<T extends Entity, ID extends Serializable> implements UnitOfWorkRepository {

    @Autowired
    protected UnitOfWork unitOfWork;

    private Class<?>[] typeArgs = null;

    public UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public UnitOfWorkJpa getJpaUnitOfWork() {
        return (UnitOfWorkJpa) unitOfWork;
    }

    // TODO Il faudrait peut être faire un aspect qui fait cela pour toutes les méthodes find
    public List<T> findAll() {
        @SuppressWarnings("unchecked")
        List<T> entities = getJpaUnitOfWork().getEntityManager().createQuery(
                "SELECT p FROM "+ getEntityType().getName() +" p").getResultList();
        entities.stream().forEach(e -> e.forceState(EntityStateType.Unchanged));
        return entities;
    }

    // TODO Il faudrait peut être faire un aspect qui fait cela pour toutes les méthodes find
    public T findById(ID id) {
        //noinspection unchecked
        T entity = (T) getJpaUnitOfWork().getEntityManager().find(getEntityType(), id);
        if (entity != null)
            entity.forceState(EntityStateType.Unchanged);
        return entity;
    }

    public void insert(T entity) {
        unitOfWork.registerInserted(entity, this);
    }

    public void update(T entity) {
        unitOfWork.registerUpdated(entity, this);
    }

    public void delete(ID id) {
        Entity entity = findById(id);

        if (entity != null) {
            delete(entity);
        }else{
            throw new EntityNotFoundException();
        }
    }
    public void delete(Entity entity) {
        unitOfWork.registerRemoved(entity, this);
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

    @Override
    public void persistNewItem(Entity entity) {
        getJpaUnitOfWork().getEntityManager().persist(entity);
    }

    @Override
    public void persistUpdatedItem(Entity entity) {
        getJpaUnitOfWork().getEntityManager().unwrap(Session.class).persist(entity);
    }

    @Override
    public void persistDeletedItem(Entity entity) {
        entity.logicalDelete();
        getJpaUnitOfWork().getEntityManager().unwrap(Session.class).merge(entity);
    }
}
