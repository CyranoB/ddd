package be.domaindrivendesign.kernel.data.interfaces;

import be.domaindrivendesign.kernel.common.model.Entity;

/**
 * Created by eddie on 19/11/15.
 */
public interface UnitOfWorkRepository {
    void persistNewItem(Entity entity);

    void persistUpdatedItem(Entity entity);

    void persistDeletedItem(Entity entity);
}
