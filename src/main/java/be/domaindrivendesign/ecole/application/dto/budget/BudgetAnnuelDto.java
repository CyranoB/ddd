package be.domaindrivendesign.ecole.application.dto.budget;

import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.module.budget.domain.model.BudgetAnnuel;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetAnnuelDto extends Dto {

    public UUID id;
    public AnneeScolaireDto anneeScolaire;
    public BigDecimal montantAideFruitEtLegumeParEleve;
    public BigDecimal montantAideLaitParEleve;
    public long fruitEtLegumeNbrEleve;
    public long laitNbrEleve;

    public static BudgetAnnuelDto convertir(BudgetAnnuel budgetAnnuel) {
        if (budgetAnnuel == null)
            return null;

        BudgetAnnuelDto budgetAnnuelDto = new BudgetAnnuelDto();
        budgetAnnuelDto.setId(budgetAnnuel.getId());
        budgetAnnuelDto.setLaitNbrEleve(budgetAnnuel.getLaitNbrEleve());
        budgetAnnuelDto.setAnneeScolaire(AnneeScolaireDto.convertir(budgetAnnuel.getAnneeScolaire()));
        budgetAnnuelDto.setFruitEtLegumeNbrEleve(budgetAnnuel.getFruitEtLegumeNbrEleve());
        budgetAnnuelDto.setMontantAideFruitEtLegumeParEleve(budgetAnnuel.getMontantAideFruitEtLegumeParEleve());
        budgetAnnuelDto.setMontantAideLaitParEleve(budgetAnnuel.getMontantAideLaitParEleve());
        return budgetAnnuelDto;
    }

    public static BudgetAnnuel convertir(BudgetAnnuelDto budgetAnnuelDto) {
        return BudgetAnnuel.creer(
                AnneeScolaireDto.convertir(budgetAnnuelDto.getAnneeScolaire()),
                budgetAnnuelDto.getMontantAideFruitEtLegumeParEleve(),
                budgetAnnuelDto.getMontantAideLaitParEleve(),
                budgetAnnuelDto.getFruitEtLegumeNbrEleve(),
                budgetAnnuelDto.getLaitNbrEleve());
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AnneeScolaireDto getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(AnneeScolaireDto anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public BigDecimal getMontantAideFruitEtLegumeParEleve() {
        return montantAideFruitEtLegumeParEleve;
    }

    public void setMontantAideFruitEtLegumeParEleve(BigDecimal montantAideFruitEtLegumeParEleve) {
        this.montantAideFruitEtLegumeParEleve = montantAideFruitEtLegumeParEleve;
    }

    public BigDecimal getMontantAideLaitParEleve() {
        return montantAideLaitParEleve;
    }

    public void setMontantAideLaitParEleve(BigDecimal montantAideLaitParEleve) {
        this.montantAideLaitParEleve = montantAideLaitParEleve;
    }

    public long getFruitEtLegumeNbrEleve() {
        return fruitEtLegumeNbrEleve;
    }

    public void setFruitEtLegumeNbrEleve(long fruitEtLegumeNbrEleve) {
        this.fruitEtLegumeNbrEleve = fruitEtLegumeNbrEleve;
    }

    public long getLaitNbrEleve() {
        return laitNbrEleve;
    }

    public void setLaitNbrEleve(long laitNbrEleve) {
        this.laitNbrEleve = laitNbrEleve;
    }
}
