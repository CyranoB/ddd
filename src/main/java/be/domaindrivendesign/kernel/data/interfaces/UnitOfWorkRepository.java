package be.domaindrivendesign.kernel.data.interfaces;

import be.domaindrivendesign.kernel.common.model.Entity;

public interface UnitOfWorkRepository<T extends Entity> {
    void persistNewItem(T entity);

    void persistUpdatedItem(T entity);

    void persistDeletedItem(T entity);
}
