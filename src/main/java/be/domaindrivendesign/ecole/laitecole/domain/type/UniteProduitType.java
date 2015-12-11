package be.domaindrivendesign.ecole.laitecole.domain.type;

/**
 * Created by eddie on 11/12/15.
 */
public enum UniteProduitType {

    Litre(1),
    Kilos(2);

    public final int typeValue;

    UniteProduitType(int value) {
        this.typeValue = value;
    }
}
