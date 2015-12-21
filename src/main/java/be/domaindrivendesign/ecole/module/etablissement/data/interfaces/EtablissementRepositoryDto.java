package be.domaindrivendesign.ecole.module.etablissement.data.interfaces;

import be.domaindrivendesign.ecole.application.dto.etablissement.*;

import java.util.List;

public interface EtablissementRepositoryDto {
    EtablissementDto getEtablissementByNumeroReference(EtablissementDtoSearch search);

    List<EtablissementDtoList> listEtablissement(EtablissementDtoSearch search);

    List<ImplantationDtoList> listImplantation(ImplantationDtoSearch search);
}
