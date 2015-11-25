package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by eddie on 20/11/15.
 */
public class Repository01 extends AbstractRepository<Entity01> {

    public List<Entity> persistedInsertedItems = new ArrayList<>();
    public List<Entity> persistedUpdatedItems = new ArrayList<>();
    public List<Entity> persistedDeletedItems = new ArrayList<>();

    public Repository01(UnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    @Override
    public Collection<Entity01> list() {
        throw new NotImplementedException();
    }

    @Override
    public Entity01 getById(UUID id) {
        throw new NotImplementedException();
    }

    @Override
    public void persistNewItem(Entity entity) {
        persistedInsertedItems.add(entity);
    }

    @Override
    public void persistUpdatedItem(Entity entity) {
        persistedUpdatedItems.add(entity);
    }

    @Override
    public void persistDeletedItem(Entity entity) {
        persistedDeletedItems.add(entity);
    }
}
