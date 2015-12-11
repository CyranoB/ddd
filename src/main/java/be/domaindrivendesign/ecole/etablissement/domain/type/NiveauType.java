package be.domaindrivendesign.ecole.etablissement.domain.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum NiveauType {

    /// <summary>
    /// Préscolaire à 5 jours.
    /// </summary>
    Prescolaire5Jour(5),
    /// <summary>
    /// Préscolaire à 7 jours.
    /// </summary>
    Prescolaire7Jour(7),
    /// <summary>
    /// Maternelle.
    /// </summary>
    Maternelle(1),
    /// <summary>
    /// Primaire.
    /// </summary>
    Primaire(2),
    /// <summary>
    /// Secondaire.
    /// </summary>
    Secondaire(3),
    /// <summary>
    /// Spécialisé.
    /// </summary>
    Specialisé(4);

    public final int typeValue;

    NiveauType(int value) {
        this.typeValue = value;
    }

}
