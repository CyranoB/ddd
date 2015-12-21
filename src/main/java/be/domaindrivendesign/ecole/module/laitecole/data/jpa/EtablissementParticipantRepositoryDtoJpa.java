package be.domaindrivendesign.ecole.module.laitecole.data.jpa;

import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoList;
import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoSearch;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.ecole.module.laitecole.domain.model.EtablissementParticipant;
import be.domaindrivendesign.ecole.module.laitecole.domain.model.QEtablissementParticipant;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EtablissementParticipantRepositoryDtoJpa extends RepositoryJpa<EtablissementParticipant> implements EtablissementParticipantRepositoryDto {
    @Override
    public List<String> listNumeroDGARNEFor(UUID etablissementId) {
        JPAQuery<EtablissementParticipant> query = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QEtablissementParticipant etablissementParticipant = QEtablissementParticipant.etablissementParticipant;
        return query.select(etablissementParticipant.etablissementNumeroReference).from(etablissementParticipant).where(etablissementParticipant.id.eq(etablissementId)).fetch();
    }

    @Override
    public List<String> listNumeroDGARNEFor(UUID etablissementId, UUID implantationId) {
        //TODO implementer
        return null;
    }

    @Override
    public List<EtablissementParticipantDtoList> listEtablissementParticipant(EtablissementParticipantDtoSearch search) {
        //TODO implementer
        return null;
    }
}
