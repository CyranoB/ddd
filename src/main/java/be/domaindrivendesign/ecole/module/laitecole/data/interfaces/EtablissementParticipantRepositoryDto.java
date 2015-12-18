package be.domaindrivendesign.ecole.module.laitecole.data.interfaces;

import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoList;
import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoSearch;

import java.util.List;
import java.util.UUID;


public interface EtablissementParticipantRepositoryDto {
    List<String> ListNumeroDGARNEFor(UUID etablissementId);

    List<String> ListNumeroDGARNEFor(UUID etablissementId, UUID implantationId);

    List<EtablissementParticipantDtoList> ListEtablissementParticipant(EtablissementParticipantDtoSearch search);
}
