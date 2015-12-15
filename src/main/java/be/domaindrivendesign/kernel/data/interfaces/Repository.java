package be.domaindrivendesign.kernel.data.interfaces;

import be.domaindrivendesign.kernel.common.model.Entity;

import java.util.List;
import java.util.UUID;

public interface Repository<T extends Entity> {
    List<T> list();

    T getById(UUID id);

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}
