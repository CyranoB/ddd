package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.kernel.data.interfaces.Repository;

public interface EtablissementRepository extends Repository<Etablissement> {
    Etablissement getEtablissementForNumeroDeReference(String numeroDeReference);
}
