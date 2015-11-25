package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule.getInstance;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

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
        assertEquals(1, getInstance().getViolations().size());
        assertNull(getInstance().getViolations().get(0).getMessage());
        assertEquals(getInstance().getViolations().get(0).getPropertyPaths(),
                singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNumeroReference"));
        assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullDenomination() {
        Implantation implantation = Implantation.creer("reference", null, new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, getInstance().getViolations().size());
        assertNull(getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getDenomination"), getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAdresse() {
        Implantation implantation = Implantation.creer("reference", "denomination", null, Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, getInstance().getViolations().size());
        assertNull(getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getAdresse"), getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullNiveauType() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), null, new Contact01(), PeriodDateHeure.EMPTY);
        // Violation
        assertEquals(1, getInstance().getViolations().size());
        assertNull(getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getNiveaux"), getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.NbrOfElementsInList.typeValue, getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("0", "1", "7"), getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullValidte() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), null);
        // Violation
        assertEquals(1, getInstance().getViolations().size());
        assertNull(getInstance().getViolations().get(0).getMessage());
        assertEquals(singletonList("be.domaindrivendesign.ecole.etablissement.model|Implantation.getValidite"), getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantation, getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), getInstance().getViolations().get(0).getValues());
    }


    @Test
    public void testModifier() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        UUID implantationId = implantation.getId();
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Implantation implantation02 = Implantation.creer("reference 2", "denomination 2", new Adresse02(), Collections.singletonList(NiveauType.Secondaire), new Contact02(), PeriodDateHeure.EMPTY);
        implantation.modifier(implantation02);

        assertEquals(implantationId, implantation.getId()); // Id est immutable
        assertEquals(EntityStateType.Modified, implantation.getState());

        assertEquals("reference", implantation.getNumeroReference()); // NumeroReference est immutable
        assertEquals("denomination 2", implantation.getDenomination());
        assertEquals(new Adresse02(), implantation.getAdresse());
        assertEquals(Collections.singletonList(NiveauType.Secondaire), implantation.getNiveaux());
        assertEquals(new Contact02(), implantation.getContact());
    }

    @Test
    public void testFermer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        UUID implantationId = implantation.getId();
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // TODO Event Register
        //EventRegister eventRegister = new EventRegister();

        // Fermeture
        LocalDateTime dateDeFermeture = LocalDateTime.now();
        implantation.fermer(dateDeFermeture);

        // Period de validite fermée
        assertEquals(EntityStateType.Modified, implantation.getState());

        // TODO Evenement levé
        // assertEquals(implantation, eventRegister.Implantation);
    }

    @Test
    public void testSupprimer() {
        Implantation implantation = Implantation.creer("reference", "denomination", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        UUID implantationId = implantation.getId();
        implantation.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // TODO Event Register
        //EventRegister eventRegister = new EventRegister();

        // Fermeture
        LocalDateTime supprimeLe = LocalDateTime.now();
        implantation.supprimer(supprimeLe);

        // Suppression logique
        assertEquals(supprimeLe, implantation.getLogicalDeleteOn());
        assertEquals(EntityStateType.Modified, implantation.getState());

        // TODO Evenement levé
        // assertEquals(implantation, eventRegister.Implantation);
    }
}