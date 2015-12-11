package be.domaindrivendesign.ecole.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.etablissement.model.Etablissement;

import java.util.UUID;

public interface EtablissementRepository {
    Etablissement getEtablissementForNumeroDeReference(String numeroDeReference);
}
