package be.domaindrivendesign.ecole.etablissement.service;

import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;

import java.time.LocalDateTime;

public interface EcoleDomainService {

    void importerEtablissement(Etablissement etablissement, LocalDateTime dateApplication);

    void importerImplantationAnneeScolaire(ImplantationAnneeScolaire implantationAnneeScolaire, LocalDateTime dateApplication);
}
