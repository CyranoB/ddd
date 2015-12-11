package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;

import java.util.List;

public interface ImplantationAnneeScolaireRepository {
    List<ImplantationAnneeScolaire> listImplantationAnneeScolaireForImplantationNumeroReference(String implantationNumeroReference);

    ImplantationAnneeScolaire getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(AnneeScolaire anneeScolaire, String implantationNumeroReference);
}