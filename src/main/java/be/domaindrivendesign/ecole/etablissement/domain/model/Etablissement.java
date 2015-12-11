package be.domaindrivendesign.ecole.etablissement.domain.model;

import be.domaindrivendesign.ecole.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.shared.valueobject.Adresse;
import be.domaindrivendesign.shared.valueobject.Contact;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
public class Etablissement extends AggregateRoot implements RuleObject {

    @Column(name = "NUMERO_REFERENCE")
    private String numeroReference;
    @Column(name = "DENOMINATION")
    private String denomination;

    @Embedded
    private Adresse adresse;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ETABLISSEMENT_ID")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Implantation> implantations;

    @Column
    private EnseignementReseauType enseignementReseau;

    @Column
    private EcoleType ecole;

    //region Getters
    /// <summary>
    /// Obtient les informations de contact.
    /// </summary>
    /// <value>
    /// Les informations de contact.
    /// </value>
    private Contact contact;

    //region Contructors
    /// <summary>
    /// Constructeur privé afin d'empêcher la création illicite d'objet.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected Etablissement(UUID id) {
        super(id);
    }

    /// <summary>
    /// Constructeur privé afin d'empêcher la création illicite d'objet.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected Etablissement() {
    }

    /// <summary>
    /// Crée un <see cref="Etablissement"/>.
    /// </summary>
    /// <param name="numeroReference">Le numéro de référence.</param>
    /// <param name="denomination">La dénomination.</param>
    /// <param name="enseignementReseau">Le réseau d'enseignement.</param>
    /// <param name="adresse">L'adresse.</param>
    /// <param name="ecole">Le type d'école.</param>
    /// <param name="contact">Les informations de contact.</param>
    /// <param name="implantations">Les implantations.</param>
    /// <returns>La référene sur l'objet nouvellement créé.</returns>
    public static Etablissement creer(String numeroReference, String denomination, EnseignementReseauType enseignementReseau, Adresse adresse, EcoleType ecole, Contact contact, Set<Implantation> implantations) {
        Etablissement etablissement = new Etablissement(UUID.randomUUID());

        etablissement.denomination = denomination;
        etablissement.numeroReference = numeroReference;
        etablissement.adresse = adresse;
        etablissement.ecole = ecole;
        etablissement.contact = contact;
        etablissement.implantations = implantations;
        etablissement.enseignementReseau = enseignementReseau;
        etablissement.state = EntityStateType.Added;

        RuleGuard.mandatory(etablissement, etablissement::getNumeroReference);
        RuleGuard.mandatory(etablissement, etablissement::getDenomination);
        RuleGuard.mandatory(etablissement, etablissement::getAdresse);
        RuleGuard.mandatory(etablissement, etablissement::getEnseignementReseau);
        RuleGuard.mandatory(etablissement, etablissement::getEcole);
        RuleGuard.nbrOfElements(etablissement, etablissement::getImplantations, 1, 10);

        return etablissement;
    }

    // region Gestion des implantations
    /// <summary>
    /// Modifie les implantations.
    /// </summary>
    /// <param name="implantations">Les implantations.</param>
    /// <param name="dateApplication">La date d'application.</param>
    public void modifierImplantations(List<Implantation> implantations, LocalDateTime dateApplication) {
        // a modifier vs existant
        for (Implantation aModifier : implantations) {
            Optional<Implantation> existant = getImplantations().stream().filter(x -> x.getNumeroReference().equals(aModifier.getNumeroReference())).findFirst();
            if (!existant.isPresent()) {
                getImplantations().add(aModifier);
                aModifier.getValidite().modifierDebut(dateApplication);
            } else
                existant.get().modifier(aModifier);
        }

        // existant vs a modifier
        for (Implantation existant: getImplantations()) {
            Optional<Implantation> aModifier = implantations.stream().filter(x -> x.getNumeroReference().equals(existant.getNumeroReference())).findFirst();
            if (!aModifier.isPresent()) {
                existant.fermer(dateApplication);
            }
        }
    }

    // region Opération établissement
    /// <summary>
    /// Modifie les informations de contact.
    /// </summary>
    /// <param name="contact">The contact.</param>
    public void modifierContact(Contact contact) {
        this.contact = contact;
        setState(EntityStateType.Modified);
    }

    /// <summary>
    /// Supprime l'établissement.
    /// </summary>
    /// <param name="dateDeFermeture">La date de fermeture.</param>
    public void supprimer(LocalDateTime dateDeFermeture) {
        this.logicalDeleteOn = dateDeFermeture;
        setState(EntityStateType.Modified);
        for (Implantation i : getImplantations())
            i.supprimer(dateDeFermeture);
    }

    //endregion

    //region Properties

    public String getNumeroReference() {
        return numeroReference;
    }

    public String getDenomination() {
        return denomination;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Set<Implantation> getImplantations() {
        return implantations;
    }

    public EnseignementReseauType getEnseignementReseau() {
        return enseignementReseau;
    }

    public EcoleType getEcole() {
        return ecole;
    }

    public Contact getContact() {
        return contact;
    }
    // endregion

}