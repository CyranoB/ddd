package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by eddie on 03/12/15.
 */
@Repository
public class JpaRepository01 extends RepositoryJpa<Entity01, UUID> {
}
