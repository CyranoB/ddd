package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import org.springframework.stereotype.Repository;

/**
 * Created by asmol on 16-12-15.
 */
@Repository
public class Repository01JpaImpl extends RepositoryJpa<Entity01> implements Repository01Jpa {
}