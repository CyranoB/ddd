package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule.getInstance;
import static org.junit.Assert.*;

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
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Arrays.asList(implantation01, implantation02)));

        assertNotNull(etablissement.getId());
        assertEquals("reference", etablissement.getNumeroReference());
        assertEquals("denomination", etablissement.getDenomination());
        assertEquals(new Adresse01(), etablissement.getAdresse());
        assertEquals(Arrays.asList(implantation01, implantation02), etablissement.getImplantations());
        assertEquals(EnseignementReseauType.OfficielProvincial, etablissement.getEnseignementReseau());
        assertEquals(EcoleType.EtablissementScolaire, etablissement.getEcole());
        assertEquals(new Contact01(), etablissement.getContact());
    }


    @Test
    public void testCreerNullNumeroReference() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer(null, "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getNumeroReference"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullDenomination() {
        Implantation implantation01 = Implantation.creer("reference 01", "denomination 01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("ref", null, EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));

        // Violation
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getDenomination"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAdresse() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, null,
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getAdresse"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullEnseignementReseauType() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", null, new Adresse02(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getEnseignementReseau"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullEcoleType() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);

        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse02(),
                null, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getEcole"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNoImplantation() {
        Etablissement etablissement = Etablissement.creer("reference 1", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse02(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.emptyList()));
        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|Etablissement.getImplantations"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.NbrOfElementsInList.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(etablissement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("0", "1", "10"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testModifierContact() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Arrays.asList(NiveauType.Maternelle, NiveauType.Primaire), new Contact01(), PeriodDateHeure.EMPTY);
        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Collections.singletonList(implantation01)));
        etablissement.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        etablissement.modifierContact(new Contact02());

        assertEquals(new Contact02(), etablissement.getContact());
        assertEquals(EntityStateType.Modified, etablissement.getState());
    }


    @Test
    public void testSupprimer() {
        Implantation implantation01 = Implantation.creer("reference I01", "denomination I01", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        Implantation implantation02 = Implantation.creer("reference I02", "denomination I02", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        implantation01.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.
        implantation02.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Etablissement etablissement = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Arrays.asList(implantation01, implantation02)));
        etablissement.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.


        // TODO Event Register
        //EventRegister eventRegister = new EventRegister();

        // Fermeture
        LocalDateTime dateDeFermeture = LocalDateTime.now();
        etablissement.supprimer(dateDeFermeture);

        // Suppression logique
        assertEquals(dateDeFermeture, etablissement.getLogicalDeleteOn());
        assertEquals(EntityStateType.Modified, etablissement.getState());
        assertEquals(EntityStateType.Modified, implantation01.getState());
        assertEquals(EntityStateType.Modified, implantation02.getState());

        // TODO Evenement levé
        //assertEquals(implantation01, eventRegister.Implantations[0]);
        //assertEquals(implantation02, eventRegister.Implantations[1]);
    }


    @Test
    public void testModifierImplantations() {
        // Etablissement existant
        Implantation implantation01A = Implantation.creer("reference a", "01 a", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        Implantation implantation01B = Implantation.creer("reference b", "01 b", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        implantation01A.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.
        implantation01B.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        Etablissement etablissement01 = Etablissement.creer("reference", "denomination", EnseignementReseauType.OfficielProvincial, new Adresse01(),
                EcoleType.EtablissementScolaire, new Contact01(), new ArrayList<>(Arrays.asList(implantation01A, implantation01B)));
        etablissement01.forceState(EntityStateType.Unchanged); // Tromper l'état pour éviter que le test Modified reste Added.

        // Modifier etablissement02 vers: a est modifie, b disparait, c est ajouté
        Implantation implantation02A = Implantation.creer("reference a", "02 a", new Adresse02(), Collections.singletonList(NiveauType.Prescolaire5Jour), new Contact02(), PeriodDateHeure.EMPTY);
        Implantation implantation02C = Implantation.creer("reference c", "02 b", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);

        // TODO Event Register
        // EventRegister eventRegister = new EventRegister();

        // Modification Etablissement
        LocalDateTime dateApplication = LocalDateTime.now();
        etablissement01.modifierImplantations(Arrays.asList(implantation02A, implantation02C), dateApplication);

        //TODO Event register
        //assertEquals(1, eventRegister.Implantations.Count);
        //assertEquals(implantation01B, eventRegister.Implantations[0]);
        //assertEquals(EntityStateType.Modified, implantation01B.getState());

        assertEquals(EntityStateType.Modified, implantation01A.getState());
        assertEquals(EntityStateType.Added, implantation02C.getState());
    }
}
