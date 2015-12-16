package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.data.interfaces.Repository;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWorkRepository;


public interface Repository01Jpa extends Repository<Entity01>, UnitOfWorkRepository<Entity01> {
}
