package be.domaindrivendesign.ecole.application.dto.laitecole;


import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDto;
import be.domaindrivendesign.ecole.module.laitecole.domain.model.EtablissementParticipant;
import be.domaindrivendesign.kernel.module.dto.DtoList;

import java.util.UUID;
import java.util.function.Function;

public class EtablissementParticipantDtoList extends DtoList {

    public static Function<EtablissementParticipant, EtablissementParticipantDtoList> aggregateToDto = new Function<EtablissementParticipant, EtablissementParticipantDtoList>() {
        @Override
        public EtablissementParticipantDtoList apply(EtablissementParticipant etablissementParticipant) {
            return convertir(etablissementParticipant);
        }
    };

    public UUID id;
    public String etablissementNumeroReference;
    public AnneeScolaireDto anneeScolaire;
    public EtablissementDto etablissement;

    public static EtablissementParticipantDtoList convertir(EtablissementParticipant etablissementParticipant, EtablissementDto etablissement) {
        if (etablissementParticipant == null)
            return null;
        EtablissementParticipantDtoList e = new EtablissementParticipantDtoList();
        e.id = etablissementParticipant.getId();
        e.etablissement = etablissement;
        e.etablissementNumeroReference = etablissementParticipant.getEtablissementNumeroReference();
        e.anneeScolaire = AnneeScolaireDto.convertir(etablissementParticipant.getAnneeScolaire());
        return e;
    }

    public static EtablissementParticipantDtoList convertir(EtablissementParticipant etablissementParticipant) {
        return EtablissementParticipantDtoList.convertir(etablissementParticipant, null);
    }
}