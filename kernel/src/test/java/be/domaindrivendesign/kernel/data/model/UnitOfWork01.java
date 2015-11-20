package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;

import java.util.Map;

/**
 * Created by eddie on 20/11/15.
 */
public class UnitOfWork01 extends UnitOfWorkImpl {
    public Map<Entity, UnitOfWorkRepository> getAddedEntities() {
        return insertedEntities;
    }

    public Map<Entity, UnitOfWorkRepository> getUpdatedEntities() {
        return updatedEntities;
    }

    public Map<Entity, UnitOfWorkRepository> getDeletedEntities() {
        return deletedEntities;
    }
}
