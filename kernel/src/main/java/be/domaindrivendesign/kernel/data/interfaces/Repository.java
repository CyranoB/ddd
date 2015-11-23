package be.domaindrivendesign.kernel.data.interfaces;

import be.domaindrivendesign.kernel.common.model.Entity;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by eddie on 20/11/15.
 */
public interface Repository<T extends Entity> {
    Collection<T> list();

    T getById(UUID id);

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}