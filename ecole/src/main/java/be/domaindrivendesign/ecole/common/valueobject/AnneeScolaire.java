package be.domaindrivendesign.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

/**
 * Value object représentant une année scolaire
 * <p>
 * Created by eddie on 13/11/15.
 */

public class AnneeScolaire extends ValueObject {
    private int anneeDebut;
    private int anneeFin;

    public AnneeScolaire(int debut, int fin) {
        //noinspection UnusedAssignment
        @SuppressWarnings("UnusedAssignment")
        AnneeScolaire anneeScolaire = this;

        this.setAnneeDebut(debut);
        this.setAnneeFin(fin);

        RuleGuard.between(this, this::getAnneeDebut, 2000, 2100, RuleSeverityType.Error);
        RuleGuard.between(this, this::getAnneeFin, 2000, 2100, RuleSeverityType.Error);
        RuleGuard.smallerOrEqualThan(this, this::getAnneeDebut, this::getAnneeFin, RuleSeverityType.Error);
    }


    //region Getters & Setters
    public int getAnneeDebut() {
        return anneeDebut;
    }

    private void setAnneeDebut(int anneeDebut) {
        this.anneeDebut = anneeDebut;
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    private void setAnneeFin(int anneeFin) {
        this.anneeFin = anneeFin;
    }
    //endregion

    @Override
    public String toString() {
        return String.format("%d / %d", this.anneeDebut, this.anneeFin);
    }
}
