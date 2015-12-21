package be.domaindrivendesign.ecole.module.laitecole.domain.type;

//TODO convertisseur
public enum UniteProduitType {

    Litre(1),
    Kilos(2);

    public final int typeValue;

    UniteProduitType(int value) {
        this.typeValue = value;
    }
}
