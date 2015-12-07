package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;

import java.util.List;

public interface ImplantationAnneeScolaireRepository {
    List<ImplantationAnneeScolaire> getByImplantationNumeroReference(String numeroDeReference);
}
