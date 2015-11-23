package be.domaindrivendesign.ecole.etablissement.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum EnseignementNiveauType {

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
