package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by eddie on 27/11/15.
 */
public class ImplantationAnneeScolaireTest {

    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testCreer() {
        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.PremierePrimaire, 14);
        Arrays.asList(classeComptage01, classeComptage02);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        assertNotNull(implantationAnneeScolaire.getId());
        assertEquals("1", implantationAnneeScolaire.getImplantationNumeroReference());
        assertEquals(new AnneeScolaire(2014, 2015), implantationAnneeScolaire.getAnneeScolaire());
        assertEquals(classeComptage01, implantationAnneeScolaire.getClasseComptages().get(0));
        assertEquals(classeComptage02, implantationAnneeScolaire.getClasseComptages().get(1));
    }

    @Test
    public void testCreerNullImplantationNumeroReference() {
        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.PremierePrimaire, 14);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer(null, new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|ImplantationAnneeScolaire.getImplantationNumeroReference"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantationAnneeScolaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullAnneeScolaire() {
        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.PremierePrimaire, 14);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", null,
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(new ArrayList<>(Collections.singleton("be.domaindrivendesign.ecole.etablissement.model|ImplantationAnneeScolaire.getAnneeScolaire")), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantationAnneeScolaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Collections.emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerNullClasseComptage() {
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015), null);

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Collections.singletonList("be.domaindrivendesign.ecole.etablissement.model|ImplantationAnneeScolaire.getClasseComptages"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.NbrOfElementsInList.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(implantationAnneeScolaire, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("0", "1", Integer.toString(Integer.MAX_VALUE)), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCreerDoubleClasseComptage() {
        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.Maternelle, 14);
        //noinspection UnusedAssignment
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(null, UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        //assertEquals(Collections.singleton("java.util|ArrayList.get.getClasse"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Unique.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(classeComptage01, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals("Maternelle", UnitOfWorkRule.getInstance().getViolations().get(0).getValues().get(0));
    }


    @Test
    public void testModifier() {
        //TODO
    }

    @Test
    public void testComptagePrimaire() {
        ClasseComptage classeComptageM = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptageP1 = ClasseComptage.creer(ClasseType.PremierePrimaire, 1);
        ClasseComptage classeComptageP2 = ClasseComptage.creer(ClasseType.DeuxiemePrimaire, 2);
        ClasseComptage classeComptageP3 = ClasseComptage.creer(ClasseType.TroisièmePrimaire, 3);
        ClasseComptage classeComptageP4 = ClasseComptage.creer(ClasseType.QuatriemePrimaire, 4);
        ClasseComptage classeComptageP5 = ClasseComptage.creer(ClasseType.CinquiemePrimaire, 5);
        ClasseComptage classeComptageP6 = ClasseComptage.creer(ClasseType.SixiemePrimaire, 6);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptageM, classeComptageP1, classeComptageP2, classeComptageP3, classeComptageP4, classeComptageP5, classeComptageP6)));

        // Violation
        assertEquals(10, implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().equals(ClasseType.Maternelle)).findFirst().get().getNombreEleves());
        assertEquals(21, implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().equals(ClasseType.Primaire)).findFirst().get().getNombreEleves());
    }

    @Test
    public void testComptageSecondaire() {
        ClasseComptage classeComptageM = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptages1 = ClasseComptage.creer(ClasseType.PremiereSecondaire, 1);
        ClasseComptage classeComptages2 = ClasseComptage.creer(ClasseType.DeuxiemeSecondaire, 2);
        ClasseComptage classeComptages3 = ClasseComptage.creer(ClasseType.TroisièmeSecondaire, 3);
        ClasseComptage classeComptages4 = ClasseComptage.creer(ClasseType.QuatriemeSecondaire, 4);
        ClasseComptage classeComptages5 = ClasseComptage.creer(ClasseType.CinquiemeSecondaire, 5);
        ClasseComptage classecomptages6 = ClasseComptage.creer(ClasseType.SixiemeSecondaire, 6);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptageM, classeComptages1, classeComptages2, classeComptages3, classeComptages4, classeComptages5, classecomptages6)));

        // Violation
        assertEquals(10, implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().equals(ClasseType.Maternelle)).findFirst().get().getNombreEleves());
        assertEquals(21, implantationAnneeScolaire.getClasseComptages().stream().filter(x -> x.getClasse().equals(ClasseType.Secondaire)).findFirst().get().getNombreEleves());
    }
}
