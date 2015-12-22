package be.domaindrivendesign.kernel.data.mock;


import be.domaindrivendesign.kernel.common.model.Entity;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryMock<T extends Entity> {

    public List<T> mocked = new ArrayList<>();

    public List<T> getList() {
        return mocked;
    }

    public T getById(UUID id) {
        return mocked.stream().filter(e -> e.getId().equals(id)).findAny().get();
    }

    public void persistNewItem(T entity) {
        throw new NotImplementedException(getClass().getName());
    }

    public void persistUpdatedItem(T entity) {
        throw new NotImplementedException(getClass().getName());
    }

    public void persistDeletedItem(T entity) {
        throw new NotImplementedException(getClass().getName());
    }

    public void insert(T entity) {
        mocked.add(entity);
    }

    public void clear() {
        mocked.clear();
    }

    public void update(T entity) {
        T existing = getById(entity.getId());
        if (existing != null) {
            throw new RuntimeException("Repository: PersistUpdateItem appeler sur un id inexistant");
        }
        mocked.remove(existing);
        mocked.add(entity);
    }

    public void delete(T entity) {
        T existing = getById(entity.getId());
        if (existing != null) {
            throw new RuntimeException("Repository: PersisitDeleteItem appeler sur un id inexistant");
        }
        mocked.remove(existing);
    }

}
