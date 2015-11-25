package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.shared.valueobject.Adresse;
import be.domaindrivendesign.shared.valueobject.Contact;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by eddie on 23/11/15.
 */
public class Implantation extends Aggregate implements RuleObject {



    //region Propriétés
    /// <summary>
    /// Obtient le numéro de référence.
    /// </summary>
    /// <value>
    /// Le numéro de référence.
    /// </value>
    private String numeroReference;
    // La dénomination est immutable
    /// <summary>
    /// Obtient la dénomination.
    /// </summary>
    /// <value>
    /// La dénomination.
    /// </value>
    private String denomination;
    // L'adresse est immutable
    /// <summary>
    /// Obtient l'adresse.
    /// </summary>
    /// <value>
    /// L'adresse.
    /// </value>
    private Adresse adresse;

    /// <summary>
    /// Obient la liste des niveaux.
    /// </summary>
    /// <value>
    /// Les niveaux.
    /// </value>
    private List<NiveauType> niveaux;
    /// <summary>
    /// Obtient les informations de contact.
    /// </summary>
    /// <value>
    /// Le contact.
    /// </value>
    private Contact contact;
    /// <summary>
    /// Obtient la période de validité.
    /// </summary>
    /// <value>
    /// La période de validité.
    /// </value>
    private PeriodDateHeure validite;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Implantation(UUID id) {
        super(id);
    }

    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected Implantation() {
    }
    //endregion

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="Implantation"/>.
    /// </summary>
    /// <param name="numeroReference">Le numéro de référence.</param>
    /// <param name="denomination">La dénomination.</param>
    /// <param name="adresse">L'adresse.</param>
    /// <param name="niveaux">Les niveaux.</param>
    /// <param name="contact">Les informations de contact.</param>
    /// <param name="validite">La période de validité.</param>
    /// <returns>Une référence sur l'objet <see cref="Implantation"/> nouvellement créé.</returns>
    public static Implantation creer(String numeroReference, String denomination, Adresse adresse, List<NiveauType> niveaux, Contact contact, PeriodDateHeure validite) {
        Implantation implantation = new Implantation(UUID.randomUUID());
        implantation.denomination = denomination;
        implantation.numeroReference = numeroReference;
        implantation.adresse = adresse;
        implantation.niveaux = niveaux;
        implantation.contact = contact;
        implantation.validite = validite;

        RuleGuard.mandatory(implantation, implantation::getNumeroReference);
        RuleGuard.mandatory(implantation, implantation::getDenomination);
        RuleGuard.mandatory(implantation, implantation::getAdresse);
        RuleGuard.nbrOfElements(implantation, implantation::getNiveaux, niveaux, 1, 7);
        RuleGuard.mandatory(implantation, implantation::getValidite);

        return implantation;
    }

    /// <summary>
    /// Modifie les informations d'une implantation.
    /// </summary>
    /// <param name="implantation">Les modifications à appliquer.</param>
    /// <remarks>
    /// Le numéro de référence, la période de validité et l'identificateur ne peuvent être modifiés.
    /// </remarks>
    public void modifier(Implantation implantation) {
        this.denomination = implantation.denomination;
        this.adresse = implantation.adresse;
        this.niveaux = implantation.niveaux;
        this.contact = implantation.contact;
        this.logicalDeleteOn = implantation.logicalDeleteOn;
        setState(EntityStateType.Modified);
    }

    /// <summary>
    /// Modifie les informations de contact.
    /// </summary>
    /// <param name="contact">Les informations de contact.</param>
    public void modifierContact(Contact contact) {
        this.contact = contact;
        setState(EntityStateType.Modified);
    }

    /// <summary>
    /// Ferme une implantation.
    /// </summary>
    /// <param name="fermeLe">La date de fermeture.</param>
    public void fermer(LocalDateTime fermeLe) {
        if (RuleGuard.after(this, () -> fermeLe, validite::getDebut, RuleSeverityType.BlockingError))
            this.validite = new PeriodDateHeure(validite.getDebut(), fermeLe);
        setState(EntityStateType.Modified);
        // TODO DomainEventManager
        // DomainEventManager.Default.Raise(new ImplantationFermee(this, fermeLe));
    }

    /// <summary>
    /// Supprime (logiquement) une implantation.
    /// </summary>
    /// <param name="supprimeLe">La date de suppression.</param>
    public void supprimer(LocalDateTime supprimeLe) {
        deleteLogically(supprimeLe);
        // TODO DomainEventManager
        //DomainEventManager.Default.Raise(new ImplantationFermee(this, supprimeLe));
    }

    //region Getters
    public String getNumeroReference() {
        return numeroReference;
    }

    public String getDenomination() {
        return denomination;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public List<NiveauType> getNiveaux() {
        return niveaux;
    }

    public Contact getContact() {
        return contact;
    }

    public PeriodDateHeure getValidite() {
        return validite;
    }
    //endregion

}
