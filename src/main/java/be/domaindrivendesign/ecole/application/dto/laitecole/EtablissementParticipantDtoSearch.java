package be.domaindrivendesign.ecole.application.dto.laitecole;

import be.domaindrivendesign.kernel.module.dto.DtoSearch;

public class EtablissementParticipantDtoSearch extends DtoSearch {

    public String numeroDgarne;

    public String getNumeroDgarne() {
        return numeroDgarne;
    }

    public void setNumeroDgarne(String numeroDgarne) {
        this.numeroDgarne = numeroDgarne;
    }
}
