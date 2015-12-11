package be.domaindrivendesign.ecole.etablissement.domain.type;

/**
 * Created by eddie on 23/11/15.
 */
public enum EnseignementReseauType {
    Inconnu(0),
    /// <summary>
    /// Organisé par la Communauté Française.
    /// </summary>
    OrganiseParLaCommunauteFrancaise(1),
    /// <summary>
    /// Officiel provincial.
    /// </summary>
    OfficielProvincial(2),
    /// <summary>
    /// Officiel communal.
    /// </summary>
    OfficielCommunal(3),
    /// <summary>
    /// Organisé par la Communauté Germanophone.
    /// </summary>
    OrganiseParLaCommunauteGermanophone(4),
    /// <summary>
    /// Libre subventionné Confessionnel.
    /// </summary>
    LibreSubventionneCf(5),
    /// <summary>
    /// Libre subventionné non confessionnel.
    /// </summary>
    LibreSubventionneCg(6),
    /// <summary>
    /// Autre réseau.
    /// </summary>
    Autre(7);

    public final int typeValue;

    EnseignementReseauType(int value) {
        this.typeValue = value;
    }
}
