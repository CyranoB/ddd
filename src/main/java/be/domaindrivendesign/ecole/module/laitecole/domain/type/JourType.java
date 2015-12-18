package be.domaindrivendesign.ecole.module.laitecole.domain.type;

public enum JourType {
    Lundi(1),
    Mardi(2),
    Mercredi(3),
    Jeudi(4),
    Vendredi(5),
    Samedi(6),
    Dimanche(7);

    public final int typeValue;

    JourType(int value) {
        this.typeValue = value;
    }
}
