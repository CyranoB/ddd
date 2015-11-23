package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by eddie on 19/11/15.
 */
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

    public void registerInserted(Entity entity, UnitOfWorkRepository repository) {
        insertedEntities.put(entity, repository);
    }

    public void registerUpdated(Entity entity, UnitOfWorkRepository repository) {
        updatedEntities.put(entity, repository);
    }

    public void registerRemoved(Entity entity, UnitOfWorkRepository repository) {
        deletedEntities.put(entity, repository);
    }

    @Transactional
    public void commit() {
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

    public boolean isEmpty() {
        return (deletedEntities.size() + insertedEntities.size() + updatedEntities.size()) == 0;
    }

    public UUID getId() {
        return id;
    }
    //endregion

    public boolean equals(Object other) {
        return other instanceof UnitOfWork && id.equals(((UnitOfWork) other).getId());
    }

    /// <summary>
    /// Returns a hash code for this instance.
    /// </summary>
    /// <returns>
    /// A hash code for this instance, suitable for use in hashing algorithms and data structures like a hash table.
    /// </returns>
    public int hashCode() {
        // 397 is a sufficient size prime number to cause the result variable to overflow and mix the bits of the hash
        return 397 ^ super.hashCode() ^ this.id.hashCode();
    }
}