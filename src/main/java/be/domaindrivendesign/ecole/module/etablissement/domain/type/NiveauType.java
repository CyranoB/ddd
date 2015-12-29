package be.domaindrivendesign.ecole.module.etablissement.domain.type;

public enum NiveauType {
    Inconnu(0),
    Maternelle(1),
    Primaire(2),
    Secondaire(3),
    Specialisé(4),
    Prescolaire5Jour(5),
    Prescolaire7Jour(7);

    public final int typeValue;

    NiveauType(int value) {
        this.typeValue = value;
    }
}
