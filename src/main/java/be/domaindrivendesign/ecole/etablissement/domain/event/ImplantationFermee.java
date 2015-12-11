package be.domaindrivendesign.ecole.etablissement.domain.event;

import be.domaindrivendesign.ecole.etablissement.domain.model.Implantation;
import be.domaindrivendesign.kernel.domain.control.DomainEventImpl;

import java.time.LocalDateTime;

/**
 * Created by eddie on 26/11/15.
 */
public class ImplantationFermee extends DomainEventImpl<ImplantationFermee> {


    /// <summary>
    /// Obtient une référence sur l'implantation.
    /// </summary>
    public final Implantation implantation;
    //endregion
    /// <summary>
    /// Obtient la date de fermeture de l'implantation
    /// </summary>
    public final LocalDateTime fermeLe;

    //region Constructeurs
    /// <summary>
    /// Initialise une nouvelle instance de la classe <see cref="ImplantationFermee"/>.
    /// </summary>
    /// <param name="implantation">L'implantation.</param>
    /// <param name="fermeLe">La date de fermeture.</param>
    public ImplantationFermee(Implantation implantation, LocalDateTime fermeLe) {
        super();
        this.implantation = implantation;
        this.fermeLe = fermeLe;
    }
    //endregion


    //region Propriétés

    //region Getters
    public Implantation getImplantation() {
        return implantation;
    }

    public LocalDateTime getFermeLe() {
        return fermeLe;
    }
    //endregion

}
