package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface ImplantationAnneeScolaireRepository {
    List<ImplantationAnneeScolaire> getByImplantationNumeroReference(String numeroDeReference);
}
