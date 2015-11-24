package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

/**
 * Created by eddie on 23/11/15.
 */
public class NumeroTelephone extends ValueObject {

    private String numero;

    private NumeroTelephone() {
    }

    public NumeroTelephone(String numero) {
        // TODO RuleGuard PhoneNumber
        //RuleGuard.PhoneNumber(this, () => this.Numero, numero);
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
