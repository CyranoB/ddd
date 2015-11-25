package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

/**
 * Created by eddie on 24/11/15.
 */
public class ImplantationTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testCreer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(),
                Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        assertNotNull(implantation.getId());
        assertEquals("reference", implantation.getNumeroReference());
        assertEquals("denomination", implantation.getDenomination());
        assertEquals(new Adresse01(), implantation.getAdresse());
        assertEquals(Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), implantation.getNiveaux());
        assertEquals(new Contact01(), implantation.getContact());
    }

    @Test
    public void testCreerNullNumeroReference() {
        Implantation implantation = Implantation.creer(
                null, "denomination",
                new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire),
                new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths(),
                singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNumeroReference"));
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullDenomination() {
        Implantation implantation = Implantation.creer("reference", null, new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getDenomination"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAdresse() {
        Implantation implantation = Implantation.creer("reference", "denomination", null, Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getAdresse"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullNiveauType() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), null, new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNiveaux"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.NbrOfElementsInList.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("0", "1", "7"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullValidte() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), null);
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getValidite"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }
}