package be.domaindrivendesign.ecole.beneficiaire.domain.model;

import be.domaindrivendesign.ecole.beneficiaire.domain.type.BeneficiaireCategorieType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.shared.valueobject.*;

import java.util.Arrays;
import java.util.UUID;

public class Beneficiaire extends AggregateRoot implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient la catégorie du bénéficiaire.
    /// </summary>
    /// <value>
    /// La catégorie du bénéficiaire.
    /// </value>
    public BeneficiaireCategorieType beneficiaireCategorie;
    /// <summary>
    /// Obtient le numéro d'entreprise.
    /// </summary>
    /// <value>
    /// Le numéro d'entreprise.
    /// </value>
    public NumeroEntreprise numeroEntreprise;
    /// <summary>
    /// Obtient le numéro d'identification au registre national.
    /// </summary>
    /// <value>
    /// Le numéro d'identification au registre national.
    /// </value>
    public NumeroIdentificationRegistreNational numeroIdentificationRegistreNational;
    /// <summary>
    /// Obtient les informations du compte bancaire.
    /// </summary>
    /// <value>
    /// Les informations du compte bancaire.
    /// </value>
    public CompteBancaire compteBancaire;
    /// <summary>
    /// Obtient la dénomination.
    /// </summary>
    /// <value>
    /// La dénomination.
    /// </value>
    public String denomination;
    /// <summary>
    /// Obtient le nombre de jour de crèche.
    /// </summary>
    /// <value>
    /// Le nombre de jours de crèche.
    /// </value>
    public int crecheNombreDeJours;
    /// <summary>
    /// Obtient les information de contact.
    /// </summary>
    /// <value>
    /// Les informations de contact.
    /// </value>
    public Contact contact;
    /// <summary>
    /// Obtient l'adresse.
    /// </summary>
    /// <value>
    /// L'adresse.
    /// </value>
    public Adresse adresse;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objet.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Beneficiaire(UUID id) {
        super(id);
    }

    /// <summary>
    /// Constructeur protété afin d'empêcher les créations illicites d'objet.
    /// </summary>
    /// <remarks>
    /// Cet constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected Beneficiaire() {
    }

    /// <summary>
    /// Crées un <see cref="Beneficiaire"/>.
    /// </summary>
    /// <param name="denomination">La dénomination.</param>
    /// <param name="beneficiaireCategorie">La catégorie du bénéficiaire.</param>
    /// <param name="numeroEntreprise">Le numéro d'entreprise.</param>
    /// <param name="numeroIdentificationRegistreNational">L'identification au registre national.</param>
    /// <param name="contact">Les informations de contact.</param>
    /// <param name="compteBancaire">Les informations du compte bancaire.</param>
    /// <param name="crecheNombreDeJours">Le nombre de jours de crèche.</param>
    /// <param name="adresse">L'adresse</param>
    /// <returns>La référence sur l'objet nouvellement créé.</returns>
    public static Beneficiaire creer(String denomination, BeneficiaireCategorieType beneficiaireCategorie, NumeroEntreprise numeroEntreprise,
                                     NumeroIdentificationRegistreNational numeroIdentificationRegistreNational,
                                     Contact contact, CompteBancaire compteBancaire, int crecheNombreDeJours, Adresse adresse) {
        Beneficiaire demandeur = new Beneficiaire(UUID.randomUUID());

        demandeur.adresse = adresse;
        demandeur.beneficiaireCategorie = beneficiaireCategorie;
        demandeur.numeroEntreprise = numeroEntreprise;
        demandeur.numeroIdentificationRegistreNational = numeroIdentificationRegistreNational;
        demandeur.contact = contact;
        demandeur.compteBancaire = compteBancaire;
        demandeur.crecheNombreDeJours = crecheNombreDeJours;
        demandeur.denomination = denomination;

        RuleGuard.mandatory(demandeur, demandeur::getDenomination);
        RuleGuard.mandatory(demandeur, demandeur::getAdresse);
        RuleGuard.mandatory(demandeur, demandeur::getBeneficiaireCategorie);

        // TODO numeroEntreprise XOR NumeroIdentificationRegistreNational: 1 des 2 obligatoire (mais seulement 1 à la fois)
        //RuleGuard.nbrOfElements(demandeur, new Dictionary<Expression<Func<Object>>, object>() {
        //    {() => demandeur.numeroEntreprise,numeroEntreprise },
        //    {() => demandeur.NumeroIdentificationRegistreNational, numeroIdentificationRegistreNational}}, 1, 1);

        // Compte bancaire & langue obligatoire 
        RuleGuard.mandatory(demandeur, demandeur::getCompteBancaire);

        // Creche => Specifier le nombre de jours: 0,5 ou 7
        if (beneficiaireCategorie != null && beneficiaireCategorie == BeneficiaireCategorieType.Creche) {
            RuleGuard.domain(demandeur, demandeur::getCrecheNombreDeJours, Arrays.asList(0, 5, 7));
        }

        // EMail obligatoire pour envoyer confirmation d'aggrement
        if (RuleGuard.mandatory(demandeur, demandeur::getContact)) {
            Contact c = demandeur.getContact();
            RuleGuard.mandatory(c, c::getEmail);
        }

        return demandeur;
    }

    //endregion

    //region Gestion du compte bancaire
    /// <summary>
    /// Modifie les informations du compte bancaire.
    /// </summary>
    /// <param name="compteBancaire">Les nouvelles informations.</param>
    public void modifierCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
        setState(EntityStateType.Modified);
    }
    //endregion

    //region Gestion des informations de contact
    /// <summary>
    /// Modifie les informations de contact.
    /// </summary>
    /// <param name="contact">Les nouvelles informations de contact.</param>
    public void modifierContact(Contact contact) {
        this.contact = contact;
        setState(EntityStateType.Modified);
    }
    //endregion

    //region Getters

    public BeneficiaireCategorieType getBeneficiaireCategorie() {
        return beneficiaireCategorie;
    }

    public be.domaindrivendesign.shared.valueobject.NumeroEntreprise getNumeroEntreprise() {
        return numeroEntreprise;
    }

    public NumeroIdentificationRegistreNational getNumeroIdentificationRegistreNational() {
        return numeroIdentificationRegistreNational;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public String getDenomination() {
        return denomination;
    }

    public int getCrecheNombreDeJours() {
        return crecheNombreDeJours;
    }

    public Contact getContact() {
        return contact;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    //endregion
}
