package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.data.interfaces.Repository;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;


public interface JpaRepository01 extends Repository<Entity01>, UnitOfWorkRepository<Entity01> {
}
