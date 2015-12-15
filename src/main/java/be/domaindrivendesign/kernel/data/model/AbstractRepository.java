package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.Repository;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractRepository<T extends Entity> implements Repository<T>, UnitOfWorkRepository<T> {


    protected final UnitOfWork unitOfWork;

    protected AbstractRepository() {
        this(null);
    }

    public AbstractRepository(UnitOfWork unitOfWork) {
        super();
        this.unitOfWork = unitOfWork;
    }

    @Override
    public abstract List<T> list();

    @Override
    public abstract T getById(UUID id);

    @Override
    public abstract void persistNewItem(T entity);

    @Override
    public abstract void persistUpdatedItem(T entity);

    @Override
    public abstract void persistDeletedItem(T entity);

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
