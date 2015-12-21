package be.domaindrivendesign.ecole.module.laitecole.data.interfaces;

import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoList;
import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoSearch;

import java.util.List;
import java.util.UUID;


public interface EtablissementParticipantRepositoryDto {
    List<String> listNumeroDGARNEFor(UUID etablissementId);

    List<String> listNumeroDGARNEFor(UUID etablissementId, UUID implantationId);

    List<EtablissementParticipantDtoList> listEtablissementParticipant(EtablissementParticipantDtoSearch search);
}
