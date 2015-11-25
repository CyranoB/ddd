package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

/**
 * Created by eddie on 23/11/15.
 */
public class NumeroTelephone extends ValueObject {

    private String numero;

    private NumeroTelephone() {
    }

    public NumeroTelephone(String numero) {
        RuleGuard.phone(this, () -> numero);
        this.numero = numero;
    }

    @Override
    public String toString() {
        return this.numero;
    }

    public String getNumero() {
        return numero;
    }
}
