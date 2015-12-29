package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.application.error.KernelApplicationException;
import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UnitOfWorkImpl implements UnitOfWork {

    protected final UUID id;
    protected final Map<Entity, UnitOfWorkRepository> insertedEntities;
    protected final Map<Entity, UnitOfWorkRepository> updatedEntities;
    protected final Map<Entity, UnitOfWorkRepository> deletedEntities;

    public UnitOfWorkImpl() {
        id = UUID.randomUUID();
        insertedEntities = new HashMap<>();
        updatedEntities = new HashMap<>();
        deletedEntities = new HashMap<>();
    }

    //region UnitOfWork members
    @Override
    public void registerInserted(Entity entity, UnitOfWorkRepository repository){
        insertedEntities.put(entity, repository);
    }

    @Override
    public void registerUpdated(Entity entity, UnitOfWorkRepository repository) {
        updatedEntities.put(entity, repository);
    }

    @Override
    public void registerRemoved(Entity entity, UnitOfWorkRepository repository) {
        deletedEntities.put(entity, repository);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = false)
    public  void commit() {
        if (UnitOfWorkRule.getInstance().hasBlockingError() || UnitOfWorkRule.getInstance().hasError())
            throw new KernelApplicationException(UnitOfWorkRule.getInstance().getViolations());

        try {
            deletedEntities.forEach((e, r) -> r.persistDeletedItem(e));
            insertedEntities.forEach((e, r) -> r.persistNewItem(e));
            updatedEntities.forEach((e, r) -> r.persistUpdatedItem(e));
        } finally {
            deletedEntities.clear();
            insertedEntities.clear();
            updatedEntities.clear();
        }
    }

    @Override
    public boolean isEmpty() {
        return (deletedEntities.size() + insertedEntities.size() + updatedEntities.size()) == 0;
    }

    @Override
    public UUID getId() {
        return id;
    }
    //endregion

    @Override
    public boolean equals(Object other) {
        return other instanceof UnitOfWork && id.equals(((UnitOfWork) other).getId());
    }

    @Override
    public int hashCode() {
        // 397 is a sufficient size prime number to cause the result variable to overflow and mix the bits of the hash
        return 397 ^ super.hashCode() ^ this.id.hashCode();
    }
}
