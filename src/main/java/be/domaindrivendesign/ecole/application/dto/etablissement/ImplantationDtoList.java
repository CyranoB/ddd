package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.ecole.module.etablissement.domain.model.Implantation;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImplantationDtoList extends Dto {
    public static Function<Implantation, ImplantationDtoList> aggregateToDto = ImplantationDtoList::convertir;

    public static Function<List<Implantation>, List<ImplantationDtoList>> aggregatesToDtos =
            ImplantationDtoList::convertir;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumeroReference() {
        return numeroReference;
    }

    public void setNumeroReference(String numeroReference) {
        this.numeroReference = numeroReference;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getAdresseCodePostal() {
        return adresseCodePostal;
    }

    public void setAdresseCodePostal(int adresseCodePostal) {
        this.adresseCodePostal = adresseCodePostal;
    }
}
