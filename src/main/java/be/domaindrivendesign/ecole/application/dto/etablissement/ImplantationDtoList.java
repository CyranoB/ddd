package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.ecole.module.etablissement.domain.model.Implantation;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImplantationDtoList extends Dto {
    public static Function<Implantation, ImplantationDtoList> aggregateToDto = new Function<Implantation, ImplantationDtoList>() {
        @Override
        public ImplantationDtoList apply(Implantation implantation) {
            return ImplantationDtoList.convertir(implantation);
        }
    };
    public UUID id;
    public String numeroReference;
    public String denomination;
    public int adresseCodePostal;

    public static List<ImplantationDtoList> convertir(List<Implantation> implantations) {
        if (implantations == null)
            return null;
        return implantations.stream().map(ImplantationDtoList.aggregateToDto).collect(Collectors.toList());
    }

    public static ImplantationDtoList convertir(Implantation implantation) {
        if (implantation == null)
            return null;

        ImplantationDtoList idl = new ImplantationDtoList();

        idl.adresseCodePostal = implantation.getAdresse() == null ? null : implantation.getAdresse().getCodePostal();
        idl.denomination = implantation.getDenomination();
        idl.id = implantation.getId();
        idl.numeroReference = implantation.getNumeroReference();
        return idl;
    }
}
