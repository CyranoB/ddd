package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.type.EnseignementReseauType;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.shared.valueobject.Adresse;
import be.domaindrivendesign.shared.valueobject.Contact;

import java.util.List;

/**
 * Created by eddie on 23/11/15.
 */
public class Etablissement extends AggregateRoot implements RuleObject {

    //region Propriétés

    /// <summary>
    /// Obtient le numéro de référence.
    /// </summary>
    /// <value>
    /// Le numéro référence.
    /// </value>
    private String numeroReference;
    /// <summary>
    /// Obtient la dénomination.
    /// </summary>
    /// <value>
    /// La dénomination.
    /// </value>
    private String denomination;
    /// <summary>
    /// Obtient l'adresse.
    /// </summary>
    /// <value>
    /// L'adresse.
    /// </value>
    private Adresse adresse;
    /// <summary>
    /// Obtient la liste des implantations.
    /// </summary>
    /// <value>
    /// Les implantations.
    /// </value>
    private List<Implantation> implantations;
    /// <summary>
    /// Obtient le réseau d'enseignement.
    /// </summary>
    /// <value>
    /// Le réseau d'enseignement.
    /// </value>
    private EnseignementReseauType enseignementReseau;
    /// <summary>
    /// Obtient le type d'école.
    /// </summary>
    /// <value>
    /// Le type d'école.
    /// </value>
    private EcoleType ecole;
    /// <summary>
    /// Obtient les informations de contact.
    /// </summary>
    /// <value>
    /// Les informations de contact.
    /// </value>
    private Contact contact;

    //endregion


}
