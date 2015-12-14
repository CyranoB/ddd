package be.domaindrivendesign.ecole.beneficiaire.data.jpa;


import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;

import java.util.UUID;

public class JpaAgrementRepository extends RepositoryJpa<Agrement, UUID> implements AgrementRepository {
}
