package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import org.springframework.stereotype.Repository;

/**
 * Created by asmol on 16-12-15.
 */
@Repository
public class JpaRepository01Impl extends RepositoryJpa<Entity01> implements JpaRepository01 {
}