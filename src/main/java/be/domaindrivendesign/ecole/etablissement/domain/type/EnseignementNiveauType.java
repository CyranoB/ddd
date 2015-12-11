package be.domaindrivendesign.ecole.etablissement.domain.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum EnseignementNiveauType {

    Inconnu(0),
    /// <summary>
    /// Enseignement ordinaire.
    /// </summary>
    Ordinaire(1),
    /// <summary>
    /// Enseignement spécial.
    /// </summary>
    Special(2);


    public final int typeValue;

    EnseignementNiveauType(int value) {
        this.typeValue = value;
    }
}
