package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

/**
 * Created by eddie on 13/11/15.
 */


public class AnneeScolaire extends ValueObject {
    private int anneeDebut;
    private int anneeFin;

    public AnneeScolaire() {
    }

    public AnneeScolaire(int debut, int fin) {
        AnneeScolaire anneeScolaire = this;

        anneeScolaire.setAnneeDebut(debut);
        anneeScolaire.setAnneeFin(fin);

        RuleGuard.between(anneeScolaire, () -> anneeScolaire.getAnneeDebut(), 2000, 2100, RuleSeverityType.Error);
        RuleGuard.between(anneeScolaire, () -> anneeScolaire.getAnneeFin(), 2000, 2100, RuleSeverityType.Error);
    }

    public static AnneeScolaire Create(int debut, int fin) {
        AnneeScolaire anneeScolaire = new AnneeScolaire();

        anneeScolaire.setAnneeDebut(debut);
        anneeScolaire.setAnneeFin(fin);

        RuleGuard.between(anneeScolaire, () -> anneeScolaire.getAnneeDebut(), 2000, 2100, RuleSeverityType.Error);
        RuleGuard.between(anneeScolaire, () -> anneeScolaire.getAnneeFin(), 2000, 2100, RuleSeverityType.Error);

        return anneeScolaire;
    }

    public int getAnneeDebut() {
        return anneeDebut;
    }

    protected void setAnneeDebut(int anneeDebut) {
        this.anneeDebut = anneeDebut;
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    protected void setAnneeFin(int anneeFin) {
        this.anneeFin = anneeFin;
    }
}
