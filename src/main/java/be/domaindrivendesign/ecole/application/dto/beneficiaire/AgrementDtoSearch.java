package be.domaindrivendesign.ecole.application.dto.beneficiaire;

import be.domaindrivendesign.kernel.module.dto.DtoSearch;

import java.util.List;
import java.util.UUID;

public class AgrementDtoSearch extends DtoSearch {
    public List<String> numeroDgarnes;
    public UUID beneficiaireId;
    public boolean aValider;
    public boolean enConstitution;

    public List<String> getNumeroDgarnes() {
        return numeroDgarnes;
    }

    public void setNumeroDgarnes(List<String> numeroDgarnes) {
        this.numeroDgarnes = numeroDgarnes;
    }

    public UUID getBeneficiaireId() {
        return beneficiaireId;
    }

    public void setBeneficiaireId(UUID beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }

    public boolean isaValider() {
        return aValider;
    }

    public void setaValider(boolean aValider) {
        this.aValider = aValider;
    }

    public boolean isEnConstitution() {
        return enConstitution;
    }

    public void setEnConstitution(boolean enConstitution) {
        this.enConstitution = enConstitution;
    }
}
