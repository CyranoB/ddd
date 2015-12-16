package be.domaindrivendesign.ecole.module.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.Repository;

import java.util.List;

public interface ImplantationAnneeScolaireRepository extends Repository<ImplantationAnneeScolaire> {

    List<ImplantationAnneeScolaire> listImplantationAnneeScolaireForImplantationNumeroReference(String implantationNumeroReference);
    ImplantationAnneeScolaire getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(AnneeScolaire anneeScolaire, String implantationNumeroReference);

}