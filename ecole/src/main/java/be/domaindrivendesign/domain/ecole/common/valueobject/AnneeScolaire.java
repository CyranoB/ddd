package be.domaindrivendesign.domain.ecole.common.valueobject;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import lombok.Value;

/**
 * Created by eddie on 13/11/15.
 */

@Value
public class AnneeScolaire implements RuleObject {
    int anneeDebut;
    int anneeFin;

    public AnneeScolaire( int debut, int fin ) {
        this.anneeDebut = debut;
        this.anneeFin = fin;

        RuleGuard.between(this, () -> this.anneeDebut, debut, 2000, 2100);
        RuleGuard.between(this, () -> this.anneeFin, fin, 2000, 2100);
    }
}
