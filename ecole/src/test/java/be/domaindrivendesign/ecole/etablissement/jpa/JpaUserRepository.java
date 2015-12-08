package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaUserRepository extends RepositoryJpa<User, UUID> {
}
