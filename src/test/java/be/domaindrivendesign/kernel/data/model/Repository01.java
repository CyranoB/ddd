package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository01 extends AbstractRepository<Entity01> {

    public List<Entity> persistedInsertedItems = new ArrayList<>();
    public List<Entity> persistedUpdatedItems = new ArrayList<>();
    public List<Entity> persistedDeletedItems = new ArrayList<>();

    public Repository01(UnitOfWork unitOfWork) {
        super(unitOfWork);
    }

    @Override
    public List<Entity01> list() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity01 getById(UUID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void persistNewItem(Entity01 entity) {
        persistedInsertedItems.add(entity);
    }

    @Override
    public void persistUpdatedItem(Entity01 entity) {
        persistedUpdatedItems.add(entity);
    }

    @Override
    public void persistDeletedItem(Entity01 entity) {
        persistedDeletedItems.add(entity);
    }
}
