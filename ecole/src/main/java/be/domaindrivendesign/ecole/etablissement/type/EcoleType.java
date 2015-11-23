package be.domaindrivendesign.ecole.etablissement.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum EcoleType {
    /// <summary>
    /// Un établissement scolaire.
    /// </summary>
    EtablissementScolaire(1),
    /// <summary>
    /// Une crèche.
    /// </summary>
    Creche(2),
    /// <summary>
    /// Une accueillante.
    /// </summary>
    Accueillante(3);


    public final int typeValue;

    EcoleType(int value) {
        this.typeValue = value;
    }
}
