package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.AggregateRoot;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO mappings
@Entity
public class EtablissementParticipant extends AggregateRoot implements RuleObject {
    //region Propriétés
    public String etablissementNumeroReference;
    @OneToMany
    public List<ImplantationParticipante> implantationParticipantes;
    public AnneeScolaire anneeScolaire;
    @OneToOne
    public CalendrierProgramme calendrierProgramme;
    public String numeroDgarne;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected EtablissementParticipant(UUID id) {
        super(id);
    }

    /// <summary>
    /// Constructeur protégé afin d'empêcher les créations illicites d'objets.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer des objets.
    /// </remarks>
    protected EtablissementParticipant() {
    }

    /// <summary>
    /// Crée une nouvelle instance de la classe <see cref="EtablissementParticipant"/>.
    /// </summary>
    /// <param name="numeroDgarne">Le numéro DGARNE.</param>
    /// <param name="anneeScolaire">L'année scolaire.</param>
    /// <param name="etablissementNumeroReference">Le numéro de référence de l'établissement.</param>
    /// <returns>Une référence sur l'objet <see cref="EtablissementParticipant"/> nouvellement créé.</returns>
    public static EtablissementParticipant Creer(String numeroDgarne, AnneeScolaire anneeScolaire, String etablissementNumeroReference) {
        EtablissementParticipant ecole = new EtablissementParticipant(UUID.randomUUID());

        RuleGuard.mandatory(ecole, ecole::getNumeroDgarne);
        RuleGuard.mandatory(ecole, ecole::getAnneeScolaire);
        RuleGuard.mandatory(ecole, ecole::getEtablissementNumeroReference);

        ecole.numeroDgarne = numeroDgarne;
        ecole.anneeScolaire = anneeScolaire;
        ecole.etablissementNumeroReference = etablissementNumeroReference;

        return ecole;
    }
    //endregion

    /// <summary>
    /// Définit le programme de distribution.
    /// </summary>
    /// <param name="calendrierProgramme">Le calendrier.</param>
    public void DefinirCalendrierProgramme(CalendrierProgramme calendrierProgramme) {
        this.calendrierProgramme = calendrierProgramme;
        this.calendrierProgramme.setState(EntityStateType.Modified);
    }

    //region Gestion des implantations
    /// <summary>
    /// Ajoute une implantation à la liste des implantations participantes.
    /// </summary>
    /// <param name="implantationParticipante">L'implantation à ajouter</param>
    public void AjouterImplantationParticipante(ImplantationParticipante implantationParticipante) {
        if (implantationParticipantes == null)
            implantationParticipantes = new ArrayList<ImplantationParticipante>();
        implantationParticipantes.add(implantationParticipante);
    }

    /// <summary>
    /// Supprime une implantation de la liste des implantations participantes.
    /// </summary>
    /// <param name="implantationParticipanteId">L'identificateur de l'implantation à supprimer.</param>
    public void SupprimerImplantationParticipante(UUID implantationParticipanteId) {
        Optional<ImplantationParticipante> implantation = implantationParticipantes.stream().filter(i -> i.getId().equals(implantationParticipanteId)).findFirst();
        if (!RuleGuard.mandatoryClass(implantation)) {
        if (implantation.isPresent())
            implantationParticipantes.remove(implantation.get());
        }
    }
    //endregion


    public String getEtablissementNumeroReference() {
        return etablissementNumeroReference;
    }

    protected void setEtablissementNumeroReference(String etablissementNumeroReference) {
        this.etablissementNumeroReference = etablissementNumeroReference;
    }

    public List<ImplantationParticipante> getImplantationParticipantes() {
        return implantationParticipantes;
    }

    protected void setImplantationParticipantes(List<ImplantationParticipante> implantationParticipantes) {
        this.implantationParticipantes = implantationParticipantes;
    }

    public AnneeScolaire getAnneeScolaire() {
        return this.anneeScolaire;
    }

    protected void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public CalendrierProgramme getCalendrierProgramme() {
        return this.calendrierProgramme;
    }

    protected void setCalendrierProgramme(CalendrierProgramme calendrierProgramme) {
        this.calendrierProgramme = calendrierProgramme;
    }

    public String getNumeroDgarne() {
        return numeroDgarne;
    }

    protected void setNumeroDgarne(String numeroDgarne) {
        this.numeroDgarne = numeroDgarne;
    }
}
