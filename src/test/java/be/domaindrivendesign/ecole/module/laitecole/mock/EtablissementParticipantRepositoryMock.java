package be.domaindrivendesign.ecole.module.laitecole.mock;

import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoList;
import be.domaindrivendesign.ecole.application.dto.laitecole.EtablissementParticipantDtoSearch;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.data.mock.EtablissementRepositoryMock;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepository;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.ecole.module.laitecole.domain.model.EtablissementParticipant;
import be.domaindrivendesign.kernel.data.mock.RepositoryMock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EtablissementParticipantRepositoryMock
        extends RepositoryMock<EtablissementParticipant>
        implements EtablissementParticipantRepository, EtablissementParticipantRepositoryDto {


    public static EtablissementParticipant getEtablissementParticipant01(String etablissementNumeroReference, String numeroDgarne) {
        return EtablissementParticipant.creer(
                numeroDgarne,
                new AnneeScolaire(2014, 2015),
                EtablissementRepositoryMock.EtablissementNumeroRefence01);
    }

    public List<String> listNumeroDGARNEFor(UUID etablissementId) {
        return mocked.stream().filter(e -> e.getId().equals(etablissementId))
                .map(EtablissementParticipant::getEtablissementNumeroReference).collect(Collectors.toList());
    }

    public List<String> listNumeroDGARNEFor(UUID etablissementId, UUID implantationId) {
        List<EtablissementParticipant> etablissementParticipants =
                mocked.stream().filter(e -> e.getId().equals(etablissementId)).collect(Collectors.toList());
        List<String> numeroDgarnEs = new ArrayList<String>();
        for (EtablissementParticipant etablissementParticipant : etablissementParticipants) {
            String n = etablissementParticipant.getImplantationParticipantes().stream()
                    .filter(i -> i.getId().equals(implantationId))
                    .map(e -> etablissementParticipant.getNumeroDgarne())
                    .distinct().findFirst().get();
            numeroDgarnEs.add(n);
        }
        return numeroDgarnEs;
    }

    public List<EtablissementParticipantDtoList> listEtablissementParticipant(EtablissementParticipantDtoSearch search) {
        return mocked.stream().map(EtablissementParticipantDtoList.aggregateToDto).collect(Collectors.toList());
    }

    @Override
    public List<EtablissementParticipant> list() {
        return null;
    }
}
