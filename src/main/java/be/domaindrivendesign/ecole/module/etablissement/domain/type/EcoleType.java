package be.domaindrivendesign.ecole.module.etablissement.domain.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum EcoleType {
    Inconnu(0),
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
