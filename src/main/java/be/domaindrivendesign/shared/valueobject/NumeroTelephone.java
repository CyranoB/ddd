package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumeroTelephone extends ValueObject {

    @Column
    private String numero;

    protected NumeroTelephone() {
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
