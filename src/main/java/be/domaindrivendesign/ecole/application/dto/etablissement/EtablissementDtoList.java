package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EtablissementDtoList extends Dto {

    public static Function<Etablissement, EtablissementDtoList> aggregateToDto = new Function<Etablissement, EtablissementDtoList>() {
        @Override
        public EtablissementDtoList apply(Etablissement etablissement) {
            return EtablissementDtoList.convertir(etablissement);
        }
    };
    public UUID id;
    public String numeroReference;
    public String denomination;
    public EnseignementReseauType enseignementReseau;
    public EcoleType ecole;
    public int codePostal;
    public String localite;
    public String adr;

    public static List<EtablissementDtoList> convertir(List<Etablissement> etablissements) {
        if (etablissements == null)
            return null;
        return etablissements.stream().map(EtablissementDtoList.aggregateToDto).collect(Collectors.toList());
    }

    public static EtablissementDtoList convertir(Etablissement etablissement) {
        EtablissementDtoList etablissementDtoList = new EtablissementDtoList();

        if (etablissement.getAdresse() != null) {
            etablissementDtoList.codePostal = etablissement.getAdresse().getCodePostal();
            etablissementDtoList.adr = etablissement.getAdresse().getAdr();
            etablissementDtoList.localite = etablissement.getAdresse().getLocalite();
        }
        etablissementDtoList.denomination = etablissement.getDenomination();
        etablissementDtoList.ecole = etablissement.getEcole();
        etablissementDtoList.enseignementReseau = etablissement.getEnseignementReseau();
        etablissementDtoList.id = etablissement.getId();
        etablissementDtoList.numeroReference = etablissement.getNumeroReference();
        return etablissementDtoList;
    }
}
