package be.domaindrivendesign.ecole.laitecole.domain.type;

/**
 * Created by eddie on 11/12/15.
 */
public enum CodeProduitType {

    //TODO: Où trouver la description des valeurs de cette énumération ?
    L(1),
    La(2),
    F9(3),
    Y9(4),
    F8(5),
    Y8(6),
    M(7),
    P(8),
    F(10);

    public final int typeValue;

    CodeProduitType(int value) {
        this.typeValue = value;
    }
}
