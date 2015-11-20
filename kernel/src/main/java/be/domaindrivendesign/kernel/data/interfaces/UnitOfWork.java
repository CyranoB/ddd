package be.domaindrivendesign.kernel.data.interfaces;

import be.domaindrivendesign.kernel.common.model.Entity;

import java.util.UUID;

/**
 * Maintains a list of objects affected by a business transaction
 * and coordinates the writing out of changes and the resolution of concurrency problems.
 *
 * Created by eddie on 19/11/15.
 */
public interface UnitOfWork {

    void registerInserted(Entity entity, UnitOfWorkRepository repository);

    void registerUpdated(Entity entity, UnitOfWorkRepository repository);

    void registerRemoved(Entity entity, UnitOfWorkRepository repository);

    void commit();

    /// <summary>
    /// Allow to know if the unitofwork has some entities to store
    /// </summary>
    /// <returns>true, if no entities are registered</returns>
    boolean isEmpty();

    UUID getId();
}
