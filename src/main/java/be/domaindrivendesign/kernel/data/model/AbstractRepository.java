package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.Repository;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by eddie on 20/11/15.
 */
public abstract class AbstractRepository<T extends Entity> implements Repository, UnitOfWorkRepository {


    protected final UnitOfWork unitOfWork;

    protected AbstractRepository() {
        this(null);
    }

    public AbstractRepository(UnitOfWork unitOfWork) {
        super();
        this.unitOfWork = unitOfWork;
    }

    @Override
    public abstract Collection list();

    @Override
    public abstract Entity getById(UUID id);

    @Override
    public abstract void persistNewItem(Entity entity);

    @Override
    public abstract void persistUpdatedItem(Entity entity);

    @Override
    public abstract void persistDeletedItem(Entity entity);

    @Override
    public void insert(Entity entity) {
        raiseUnitOfWorkOrEntityNull(entity);
        unitOfWork.registerInserted(entity, this);
    }

    @Override
    public void update(Entity entity) {
        raiseUnitOfWorkOrEntityNull(entity);
        unitOfWork.registerUpdated(entity, this);
    }

    @Override
    public void delete(Entity entity) {
        raiseUnitOfWorkOrEntityNull(entity);
        unitOfWork.registerRemoved(entity, this);
    }

    protected void raiseUnitOfWorkOrEntityNull(Entity entity) {
        if (unitOfWork == null) {
            throw new KernelException("Unit of work is null, no insert is allowed.");
        }
        if (entity == null) {
            throw new KernelException("Insert null entity is not allowed.");
        }
    }
}
