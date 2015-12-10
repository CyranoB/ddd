package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule.getInstance;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

/**
 * Created by eddie on 24/11/15.
 */
public class ImplantationTest {
    @After
    public void tearDown() {
        getInstance().clear();
    }

    @Test
    public void testCreer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(),
                Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Assert.assertNotNull(implantation.getId());
        Assert.assertEquals("reference", implantation.getNumeroReference());
        Assert.assertEquals("denomination", implantation.getDenomination());
        Assert.assertEquals(new Adresse01(), implantation.getAdresse());
        Assert.assertEquals(Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), implantation.getNiveaux());
        assertEquals(new Contact01(), implantation.getContact());
    }

    @Test
    public void testCreerNullNumeroReference() {
        Implantation implantation = Implantation.creer(
                null, "denomination",
                new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire),
                new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        Assert.assertEquals(1, getInstance().getViolations().size());
        Assert.assertNull(getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(getInstance().getViolations().get(0).getPropertyPaths(),
                singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNumeroReference"));
        Assert.assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullDenomination() {
        Implantation implantation = Implantation.creer("reference", null, new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        Assert.assertEquals(1, getInstance().getViolations().size());
        Assert.assertNull(getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getDenomination"), getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAdresse() {
        Implantation implantation = Implantation.creer("reference", "denomination", null, Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        Assert.assertEquals(1, getInstance().getViolations().size());
        Assert.assertNull(getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getAdresse"), getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullNiveauType() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), null, new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        Assert.assertEquals(1, getInstance().getViolations().size());
        Assert.assertNull(getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNiveaux"), getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.NbrOfElementsInList.typeValue, getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("0", "1", "7"), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullValidte() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), null);
        // Violation
        Assert.assertEquals(1, getInstance().getViolations().size());
        Assert.assertNull(getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getValidite"), getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }


    @Test
    public void testModifier() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        UUID implantationId = implantation.getId();
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Implantation implantation02 = Implantation.creer("reference 2", "denomination 2", new Adresse02(), Collections.singletonList(NiveauType.Secondaire), new Contact02(), PeriodDateHeure.EMPTY);
        implantation.modifier(implantation02);

        Assert.assertEquals(implantationId, implantation.getId()); // Id est immutable
        Assert.assertEquals(EntityStateType.Modified, implantation.getState());

        Assert.assertEquals("reference", implantation.getNumeroReference()); // NumeroReference est immutable
        Assert.assertEquals("denomination 2", implantation.getDenomination());
        Assert.assertEquals(new Adresse02(), implantation.getAdresse());
        Assert.assertEquals(Collections.singletonList(NiveauType.Secondaire), implantation.getNiveaux());
        assertEquals(new Contact02(), implantation.getContact());
    }

    @Test
    public void testModifierContact() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        implantation.modifierContact(new Contact02());

        assertEquals(new Contact02(), implantation.getContact());
    }

    @Test
    public void testFermer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // Event Register
        DummyEventRegister eventRegister = new DummyEventRegister();

        // Fermeture
        LocalDateTime dateDeFermeture = LocalDateTime.now();
        implantation.fermer(dateDeFermeture);

        // Period de validite fermée
        Assert.assertEquals(EntityStateType.Modified, implantation.getState());

        // Evenement levé
        Assert.assertEquals(implantation, eventRegister.getImplantations().get(0));
    }

    @Test
    public void testSupprimer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // Event Register
        DummyEventRegister eventRegister = new DummyEventRegister();

        // Fermeture
        LocalDateTime supprimeLe = LocalDateTime.now();
        implantation.supprimer(supprimeLe);

        // Suppression logique
        Assert.assertEquals(supprimeLe, implantation.getLogicalDeleteOn());
        Assert.assertEquals(EntityStateType.Modified, implantation.getState());

        // Evenement levé
        Assert.assertEquals(implantation, eventRegister.getImplantations().get(0));
    }
}