package be.domaindrivendesign.ecole.budget.model;


import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;

import java.math.BigDecimal;
import java.util.UUID;

public class BudgetAnnuel extends Aggregate implements RuleObject {

    //region Propriétés
    /// <summary>
    /// Obtient l'année scolaire.
    /// </summary>
    /// <value>
    /// L'année scolaire.
    /// </value>
    public AnneeScolaire anneeScolaire;
    /// <summary>
    /// Obtient le montant de l'aide "Fruits et Légume" par élève.
    /// </summary>
    /// <value>
    /// Le montant de l'aide "Fruits et Légume" par élève.
    /// </value>
    public BigDecimal montantAideFruitEtLegumeParEleve;
    /// <summary>
    /// Obtient le montant de l'aide "Lait" par élève.
    /// </summary>
    /// <value>
    /// Le montant de l'aide "Lait" par élève.
    /// </value>
    public BigDecimal montantAideLaitParEleve;
    /// <summary>
    /// Obtient le nombre d'élève éligibles pour l'aide "Fruits et Légumes".
    /// </summary>
    /// <value>
    /// Le nombre d'élève éligibles pour l'aide "Fruits et Légumes".
    /// </value>
    public long fruitEtLegumeNbrEleve;
    /// <summary>
    /// Obtient le nombre d'élève éligibles pour l'aide "Lait".
    /// </summary>
    /// <value>
    /// Le nombre d'élève éligibles pour l'aide "Lait".
    /// </value>
    public long laitNbrEleve;
    //endregion

    //region Constructeurs
    /// <summary>
    /// Constructeur protégé afin d'éviter les créations illicites d'objet.
    /// </summary>
    /// <remarks>
    /// Ce constructeur est exclusivement utilisé par l'ORM pour créer les objets.
    /// </remarks>
    protected BudgetAnnuel() {
    }

    /// <summary>
    /// Constructeur protégé afin d'éviter les créations illicites d'objet.
    /// </summary>
    /// <param name="id">L'identificateur unique de l'objet.</param>
    protected BudgetAnnuel(UUID id) {
        super(id);
    }

    /// <summary>
    /// Crée un budget annuel.
    /// </summary>
    /// <param name="anneeScolaire">L'année scolaire.</param>
    /// <param name="montantAideFruitEtLegumeparEleve">Le montant de l'aide "Fruits et Légumes" par élève.</param>
    /// <param name="montantAideLaitParEleve">Le montant de l'aide "Lait" par élève.</param>
    /// <param name="fruitEtLegumeNbrEleve">Le nombre d'élève concernés par l'aide "Fruits et Légumes".</param>
    /// <param name="laitNbrEleve">Le nombre d'élève concernés par l'aide "Lait".</param>
    /// <returns>La référence au <see cref="BudgetAnnuel"/> nouvellement créé.</returns>
    public static BudgetAnnuel creer(AnneeScolaire anneeScolaire, BigDecimal montantAideFruitEtLegumeparEleve, BigDecimal montantAideLaitParEleve,
                                     long fruitEtLegumeNbrEleve, long laitNbrEleve) {
        BudgetAnnuel budgetAnnuel = new BudgetAnnuel(UUID.randomUUID());

        budgetAnnuel.anneeScolaire = anneeScolaire;
        budgetAnnuel.montantAideFruitEtLegumeParEleve = montantAideFruitEtLegumeparEleve;
        budgetAnnuel.montantAideLaitParEleve = montantAideLaitParEleve;
        budgetAnnuel.fruitEtLegumeNbrEleve = fruitEtLegumeNbrEleve;
        budgetAnnuel.laitNbrEleve = laitNbrEleve;

        RuleGuard.mandatory(budgetAnnuel, budgetAnnuel::getAnneeScolaire);
        RuleGuard.greaterOrEqualThanInvariant(budgetAnnuel, budgetAnnuel::getMontantAideFruitEtLegumeParEleve, new BigDecimal(0.1));
        RuleGuard.greaterOrEqualThanInvariant(budgetAnnuel, budgetAnnuel::getMontantAideLaitParEleve, new BigDecimal(0.1));
        RuleGuard.greaterOrEqualThanInvariant(budgetAnnuel, budgetAnnuel::getFruitEtLegumeNbrEleve, 0l);
        RuleGuard.greaterOrEqualThanInvariant(budgetAnnuel, budgetAnnuel::getLaitNbrEleve, 0l);

        return budgetAnnuel;
    }

    //endregion

    //region Gestion des nombres d'élève.
    /// <summary>
    /// Modifie le nombre d'élève concernés par l'aide "Fruits et Légumes".
    /// </summary>
    /// <param name="fruitEtLegumeNbrEleve">Le nombre d'élève.</param>
    public void ModifierFruitEtLegumeNbrEleve(long fruitEtLegumeNbrEleve) {
        this.fruitEtLegumeNbrEleve = fruitEtLegumeNbrEleve;
        RuleGuard.greaterOrEqualThanInvariant(this, this::getFruitEtLegumeNbrEleve, 0l);
        setState(EntityStateType.Modified);
    }

    /// <summary>
    /// Modifie le nombre d'élève concernés par l'aide "Lait".
    /// </summary>
    /// <param name="aideLaitNbrEleve">Le nombre d'élève.</param>
    public void ModifierAideLaitNbrEleve(long aideLaitNbrEleve) {
        this.laitNbrEleve = aideLaitNbrEleve;
        RuleGuard.greaterOrEqualThanInvariant(this, this::getLaitNbrEleve, 0l);
        setState(EntityStateType.Modified);
    }
    //endregion


    public AnneeScolaire getAnneeScolaire() {
        return anneeScolaire;
    }

    protected void setAnneeScolaire(AnneeScolaire anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public BigDecimal getMontantAideFruitEtLegumeParEleve() {
        return montantAideFruitEtLegumeParEleve;
    }

    protected void setMontantAideFruitEtLegumeParEleve(BigDecimal montantAideFruitEtLegumeParEleve) {
        this.montantAideFruitEtLegumeParEleve = montantAideFruitEtLegumeParEleve;
    }

    public BigDecimal getMontantAideLaitParEleve() {
        return montantAideLaitParEleve;
    }

    protected void setMontantAideLaitParEleve(BigDecimal montantAideLaitParEleve) {
        this.montantAideLaitParEleve = montantAideLaitParEleve;
    }

    public long getFruitEtLegumeNbrEleve() {
        return fruitEtLegumeNbrEleve;
    }

    protected void setFruitEtLegumeNbrEleve(long fruitEtLegumeNbrEleve) {
        this.fruitEtLegumeNbrEleve = fruitEtLegumeNbrEleve;
    }

    public long getLaitNbrEleve() {
        return laitNbrEleve;
    }

    protected void setLaitNbrEleve(long laitNbrEleve) {
        this.laitNbrEleve = laitNbrEleve;
    }
}
