package be.domaindrivendesign.ecole.beneficiaire.getmodel;

import be.domaindrivendesign.ecole.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.ecole.beneficiaire.domain.type.BeneficiaireCategorieType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.control.DefaultDomainEventManager;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BeneficiciareTest {

    @After
    public void CleanCallContext() {
        UnitOfWorkRule.getInstance().clear();
        DefaultDomainEventManager.getInstance().destroy();
    }

    @Test
    public void TestCreer() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 0, new Adresse01());

        Assert.assertNotNull(beneficiaire.getId());
        Assert.assertEquals(beneficiaire.getBeneficiaireCategorie(), BeneficiaireCategorieType.Fournisseur);
        Assert.assertEquals(beneficiaire.getNumeroEntreprise(), new NumeroEntreprise01());
        Assert.assertNull(beneficiaire.getNumeroIdentificationRegistreNational());
        Assert.assertEquals(beneficiaire.getDenomination(), "beneficiaire 1");
        Assert.assertEquals(beneficiaire.getCompteBancaire(), new CompteBancaire01());
        Assert.assertEquals(0, beneficiaire.getCrecheNombreDeJours());
        Assert.assertEquals(beneficiaire.getContact(), new Contact01());
    }

    @Test
    public void TestCreerNullDenomination() {
        Beneficiaire beneficiaire = Beneficiaire.creer(null, BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 0, new Adresse01());

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Beneficiaire.getDenomination"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerNullEMail() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact02NullEmail(), new CompteBancaire01(), 0, new Adresse01());

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|Contact02NullEmail.getEmail"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(new Contact02NullEmail(), UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerNullCompteBancaire() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), null, 0, new Adresse01());

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Beneficiaire.getCompteBancaire"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerNullBeneficiaireCategoryType() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", null,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 0, new Adresse01());
        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Beneficiaire.getBeneficiaireCategorie"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerNumeroEnterpriseEtNumeroIdentificationRegistreNational() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), new NumeroIdentificationRegistreNational01(), new Contact01(), new CompteBancaire01(), 0, new Adresse01());
        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("BeneficiaireDomain.Domain.Model|Beneficiaire.NumeroEntreprise", "BeneficiaireDomain.Domain.Model|Beneficiaire.NumeroIdentificationRegistreNational"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.NbrOfElementsAsProperty, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("2", "1", "1"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerCreche() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Creche,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 8, new Adresse01());
        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Beneficiaire.getCrecheNombreDeJours"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.DomainNumber.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("0", "5", "7"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestCreerNonCrecheAvecCrecheNombreDeJours() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 5, new Adresse01());

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Beneficiaire.getCrecheNombreDeJours"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.EqualsNumberInvariant.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(beneficiaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("5"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }


    @Test
    public void TestModifierContact() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 0, new Adresse01());
        beneficiaire.forceState(EntityStateType.Unchanged);

        beneficiaire.modifierContact(new Contact02());
        Assert.assertEquals(EntityStateType.Modified, beneficiaire.getState());
        Assert.assertEquals(new Contact02(), beneficiaire.getContact());
    }

    @Test
    public void TestModifierCompteBancaire() {
        Beneficiaire beneficiaire = Beneficiaire.creer("beneficiaire 1", BeneficiaireCategorieType.Fournisseur,
                new NumeroEntreprise01(), null, new Contact01(), new CompteBancaire01(), 0, new Adresse01());
        beneficiaire.forceState(EntityStateType.Unchanged);

        beneficiaire.modifierCompteBancaire(new CompteBancaire02());
        Assert.assertEquals(EntityStateType.Modified, beneficiaire.getState());
        Assert.assertEquals(new CompteBancaire02(), beneficiaire.getCompteBancaire());
    }

}
