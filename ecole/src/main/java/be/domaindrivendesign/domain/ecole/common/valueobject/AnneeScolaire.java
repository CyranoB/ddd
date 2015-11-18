package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

/**
 * Created by eddie on 13/11/15.
 */


public class AnneeScolaire extends ValueObject {
    private int anneeDebut;
    private int anneeFin;

    public AnneeScolaire( int debut, int fin ) {
        this.anneeDebut = debut;
        this.anneeFin = fin;

        RuleGuard.between(this, () -> this.anneeDebut, 2000, 2100);
        RuleGuard.between(this, () -> this.anneeFin, 2000, 2100);
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
