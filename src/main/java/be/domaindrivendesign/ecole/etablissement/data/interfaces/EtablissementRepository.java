package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;

public interface EtablissementRepository {
    Etablissement getEtablissementForNumeroDeReference(String numeroDeReference);
}
