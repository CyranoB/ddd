package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.Repository;

import java.util.List;

public interface ImplantationAnneeScolaireRepository extends Repository<ImplantationAnneeScolaire> {

    List<ImplantationAnneeScolaire> listImplantationAnneeScolaireForImplantationNumeroReference(String implantationNumeroReference);
    ImplantationAnneeScolaire getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(AnneeScolaire anneeScolaire, String implantationNumeroReference);

}