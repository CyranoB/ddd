package be.domaindrivendesign.ecole.application.dto.etablissement;


import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ImplantationAnneeScolaireDto extends Dto {

    public UUID id;
    public String implantationNumeroReference;
    public AnneeScolaireDto anneeScolaireDto;
    public List<ClasseComptageDto> classeComptages;

    public static ImplantationAnneeScolaireDto convertir(ImplantationAnneeScolaire ias) {
        if (ias == null)
            return null;

        ImplantationAnneeScolaireDto iasDto = new ImplantationAnneeScolaireDto();
        iasDto.anneeScolaireDto = ias.getAnneeScolaire() != null ? AnneeScolaireDto.convertir(ias.getAnneeScolaire()) : null;
        iasDto.classeComptages = ias.getClasseComptages() != null ? ias.getClasseComptages().stream().map(ClasseComptageDto.aggregateToDto).collect(Collectors.toList()) : null;
        iasDto.implantationNumeroReference = ias.getImplantationNumeroReference();
        iasDto.id = ias.getId();
        return iasDto;
    }

}
