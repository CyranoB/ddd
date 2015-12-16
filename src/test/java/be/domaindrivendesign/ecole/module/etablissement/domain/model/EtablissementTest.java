package be.domaindrivendesign.ecole.module.etablissement.domain.model;

import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.NiveauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

import static be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule.getInstance;
import static org.junit.Assert.assertEquals;

/**
 * Created by eddie on 25/11/15.
 */
public class EtablissementTest {

    @After
    public void tearDown() {
        getInstance().clear();
    }

    @Test
    public void testCreer() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Implantation implantation02 = Implantation.creer("reference I02", "denomination I02", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Arrays.asList(implantation01, implantation02)));

        Assert.assertNotNull(etablissement.getId());
        Assert.assertEquals("reference", etablissement.getNumeroReference());
        Assert.assertEquals("denomination", etablissement.getDenomination());
        Assert.assertEquals(new Adresse01(), etablissement.getAdresse());
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(implantation01, implantation02)), etablissement.getImplantations());
        assertEquals(EnseignementReseauType.OfficielProvincial, etablissement.getEnseignementReseau());
        assertEquals(EcoleType.EtablissementScolaire, etablissement.getEcole());
        assertEquals(new Contact01(), etablissement.getContact());
    }


    @Test
    public void testCreerNullNumeroReference() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer(null, "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getNumeroReference"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullDenomination() {
        Implantation implantation01 = Implantation.creer("reference 01", "denomination 01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("ref", null, EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));

        // Violation
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getDenomination"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAdresse() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, null,
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getAdresse"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullEnseignementReseauType() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", null, new Adresse02(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getEnseignementReseau"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullEcoleType() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);

        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse02(),
                null, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));

        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getEcole"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNoImplantation() {
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse02(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.emptyList()));
        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.module.etablissement.domain.model|Etablissement.getImplantations"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.NbrOfElementsInList.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("0", "1", "10"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testModifierContact() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Collections.singletonList(implantation01)));
        etablissement.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        etablissement.modifierContact(new Contact02());

        assertEquals(new Contact02(), etablissement.getContact());
        Assert.assertEquals(EntityStateType.Modified, etablissement.getState());
    }


    @Test
    public void testSupprimer() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        Implantation implantation02 = Implantation.creer("reference I02", "denomination I02", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        implantation01.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.
        implantation02.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Arrays.asList(implantation01, implantation02)));
        etablissement.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // Event Register
        DummyEventRegister eventRegister = new DummyEventRegister();

        // Fermeture
        LocalDateTime dateDeFermeture = LocalDateTime.now();
        etablissement.supprimer(dateDeFermeture);

        // Suppression logique
        Assert.assertEquals(dateDeFermeture, etablissement.getLogicalDeleteOn());
        Assert.assertEquals(EntityStateType.Modified, etablissement.getState());
        Assert.assertEquals(EntityStateType.Modified, implantation01.getState());
        Assert.assertEquals(EntityStateType.Modified, implantation02.getState());

        // Evenement levé
        Assert.assertEquals(implantation01, eventRegister.getImplantations().get(0));
        Assert.assertEquals(implantation02, eventRegister.getImplantations().get(1));
    }


    @Test
    public void testModifierImplantations() throws NoSuchMethodException {
        // Etablissement existant
        Implantation implantation01A = Implantation.creer("reference a", "01 a", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        Implantation implantation01B = Implantation.creer("reference b", "01 b", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        implantation01A.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.
        implantation01B.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Etablissement etablissement01 = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new LinkedHashSet<>(Arrays.asList(implantation01A, implantation01B)));
        etablissement01.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // Modifier etablissement02 vers: a est modifie, b disparait, c est ajouté
        Implantation implantation02A = Implantation.creer("reference a", "02 a", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        Implantation implantation02C = Implantation.creer("reference c", "02 b", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);

        // Event register
        DummyEventRegister eventRegister = new DummyEventRegister();

        // Modification Etablissement
        LocalDateTime dateApplication = LocalDateTime.now();
        etablissement01.modifierImplantations(Arrays.asList(implantation02A, implantation02C), dateApplication);

        // Vérifications
        Assert.assertEquals(1, eventRegister.getImplantations().size());
        Assert.assertEquals(implantation01B, eventRegister.getImplantations().get(0));
        Assert.assertEquals(EntityStateType.Modified, implantation01B.getState());
        Assert.assertEquals(EntityStateType.Modified, implantation01A.getState());
        Assert.assertEquals(EntityStateType.Added, implantation02C.getState());
    }
}
