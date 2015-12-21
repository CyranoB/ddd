package be.domaindrivendesign.ecole.module.beneficiaire.domain.model;


import be.domaindrivendesign.ecole.module.beneficiaire.domain.event.DemandeAgrementValidee;
import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.common.valueobject.ValidationDemande;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.control.DefaultDomainEventManager;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Agrement extends AggregateRoot implements RuleObject {

    @Column
    protected LocalDateTime dateDepot;

    @Column
    protected LocalDateTime dateDemande;

    @Column
    protected int numeroDossier;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "anneeDebut", column = @Column(name = "PREMIEREANNEESCOLAIRE_ANNEEDEBUT")),
            @AttributeOverride(name = "anneeFin", column = @Column(name = "PREMIEREANNEESCOLAIRE_ANNEEFIN"))
    })
    protected AnneeScolaire premiereAnneeScolaire;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "anneeDebut", column = @Column(name = "DERNIEREANNEESCOLAIRE_ANNEEDEBUT")),
            @AttributeOverride(name = "anneeFin", column = @Column(name = "DERNIEREANNEESCOLAIRE_ANNEEFIN"))
    })
    protected AnneeScolaire derniereAnneeScolaire;

    @Column
    @Type(type = "uuid-char")
    protected UUID beneficiaireId;

    @Embedded
    protected ValidationDemande validationDemande;

    @Column
    protected String numeroDgarne;


    //region Constructeurs
    /// <summary>
    /// Constructeur privé afin d'empêcher les créations illicites d'objet.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer les objets.
    /// </remarks>
    protected Agrement() {
    }

    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Agrement(UUID id) {
        super(id);
        this.validationDemande = new ValidationDemande();
    }

    /// <summary>
    /// Crées un nouvel <see cref="Agrement"/>.
    /// </summary>
    /// <param name="numeroDgarne">Le numéro DGARNE de l'agrément.</param>
    /// <param name="dateDepot">La date de dépot.</param>
    /// <param name="dateDemande">La date de demande.</param>
    /// <param name="numeroDossier">Le numéro de dossier.</param>
    /// <param name="premiereAnneeScolaire">La première année scolaire.</param>
    /// <param name="beneficiaireId">L'identificateur du <see cref="Beneficiaire"/>.</param>
    /// <returns>Une référence sur le nouvel <see cref="Agrement"/>.</returns>
    public static Agrement creer(String numeroDgarne, LocalDateTime dateDepot, LocalDateTime dateDemande, int numeroDossier,
                                 AnneeScolaire premiereAnneeScolaire, UUID beneficiaireId) {
        // Instanciation d'un agrément avec génération du GUID
        Agrement demandeAgrement = new Agrement(UUID.randomUUID());

        // Affectation des données
        demandeAgrement.numeroDgarne = numeroDgarne;
        demandeAgrement.dateDepot = dateDepot;
        demandeAgrement.dateDemande = dateDemande;
        demandeAgrement.numeroDossier = numeroDossier;
        demandeAgrement.premiereAnneeScolaire = premiereAnneeScolaire;
        demandeAgrement.beneficiaireId = beneficiaireId;


        // Vérification des données obligatoires
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getNumeroDgarne);
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getDateDepot);
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getDateDemande);
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getNumeroDossier);
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getPremiereAnneeScolaire);
        RuleGuard.mandatory(demandeAgrement, demandeAgrement::getBeneficiaireId);

        // Renvoyer l'instance créée
        return demandeAgrement;
    }
    //endregion

    //region Gestion de la validation de la demande
    /// <summary>
    /// Fixes la date de validation de l'agrément.
    /// </summary>
    /// <param name="dateDeDemande">La date de demande.</param>
    public void demanderValidation(LocalDateTime dateDeDemande) {
        // Fixer la date de validation
        this.validationDemande = validationDemande.demanderLaValidation(dateDeDemande != null ? dateDeDemande : LocalDateTime.now());

        // L'entité a été modifiée
        setState(EntityStateType.Modified);
    }

    public void demanderValidation() {
        demanderValidation(null);
    }

    /// <summary>
    /// Fixes la date d'acceptation de l'agrément.
    /// </summary>
    /// <param name="dateAcceptation">La date d'acceptation.</param>
    public void accepter(LocalDateTime dateAcceptation) {
        // Fixer la date d'acceptation
        this.validationDemande = validationDemande.accepter(dateAcceptation != null ? dateAcceptation : LocalDateTime.now());

        // L'entité a été modifiée
        setState(EntityStateType.Modified);

        // Lever un événement pour envoyer notament le document de confirmation avec acceptation
        DefaultDomainEventManager.getInstance().fire(new DemandeAgrementValidee(this));
    }

    public void accepter() {
        accepter(null);
    }

    /// <summary>
    /// Fixes la date de refus de l'agrément
    /// </summary>
    /// <param name="raisonRefus">La raison du refus.</param>
    /// <param name="dateRefus">La date du refus.</param>
    public void refuser(String raisonRefus, LocalDateTime dateRefus) {
        // Fixer la date de refus
        this.validationDemande = validationDemande.rejeter(dateRefus != null ? dateRefus : LocalDateTime.now(), raisonRefus);

        // L'entité a été modifiée
        setState(EntityStateType.Modified);

        // Lever un événement pour envoyer notamment le document de confirmation avec rejet
        DefaultDomainEventManager.getInstance().fire(new DemandeAgrementValidee(this));
    }
    //endregion

    //region Gestion de la dernière année scolaire
    /// <summary>
    /// Fixes la date de clôture de l'agrément (c'est-à-dire fixer la dernière année scolaire)
    /// </summary>
    /// <param name="derniereAnneeScolaire">La dernière année scolaire.</param>
    public void cloturerAgrement(AnneeScolaire derniereAnneeScolaire) {

        // Fixer la derniereAnneeScolaire
        this.derniereAnneeScolaire = derniereAnneeScolaire;

        // Il faut une derniereAnneeScolaire
        if (RuleGuard.mandatory(this, this::getDerniereAnneeScolaire)) {
            // Il faut que la derniereAnneeScolaire soit égale ou après la PremiereAnneeScolaire               
            RuleGuard.afterOrEqual(this, () -> LocalDateTime.of(this.getDerniereAnneeScolaire().getAnneeDebut(), 1, 1, 0, 0, 0), () -> LocalDateTime.of(this.getPremiereAnneeScolaire().getAnneeFin(), 12, 31, 0, 0, 0));
        }

        // L'entité a été modifiée
        setState(EntityStateType.Modified);
    }
    //endregion

    //region Getters & setters


    public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    protected void setDateDepot(LocalDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    protected void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public int getNumeroDossier() {
        return numeroDossier;
    }

    protected void setNumeroDossier(int numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public AnneeScolaire getPremiereAnneeScolaire() {
        return premiereAnneeScolaire;
    }

    protected void setPremiereAnneeScolaire(AnneeScolaire premiereAnneeScolaire) {
        this.premiereAnneeScolaire = premiereAnneeScolaire;
    }

    public AnneeScolaire getDerniereAnneeScolaire() {
        return derniereAnneeScolaire;
    }

    protected void setDerniereAnneeScolaire(AnneeScolaire derniereAnneeScolaire) {
        this.derniereAnneeScolaire = derniereAnneeScolaire;
    }

    public UUID getBeneficiaireId() {
        return beneficiaireId;
    }

    protected void setBeneficiaireId(UUID beneficiaireId) {
        this.beneficiaireId = beneficiaireId;
    }

    public ValidationDemande getValidationDemande() {
        return validationDemande;
    }

    protected void setValidationDemande(ValidationDemande validationDemande) {
        this.validationDemande = validationDemande;
    }

    public String getNumeroDgarne() {
        return numeroDgarne;
    }

    protected void setNumeroDgarne(String numeroDgarne) {
        this.numeroDgarne = numeroDgarne;
    }

    //endregion

}
