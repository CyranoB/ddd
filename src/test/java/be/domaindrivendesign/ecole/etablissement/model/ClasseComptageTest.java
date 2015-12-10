package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by eddie on 27/11/2015.
 */
public class ClasseComptageTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void TestCreer() {
        ClasseComptage classeComptage = ClasseComptage.creer(ClasseType.Maternelle, 10);
        Assert.assertNotNull(classeComptage.getId());
        assertEquals(ClasseType.Maternelle, classeComptage.getClasse());
        Assert.assertEquals(10, classeComptage.getNombreEleves());
    }

    @Test
    public void TestCreerNullClasseType() {
        ClasseComptage classeComptage = ClasseComptage.creer(null, 10);
        // Violation
        Assert.assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        Assert.assertEquals(null, UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(new ArrayList<>(Collections.singleton("be.domaindrivendesign.ecole.etablissement.model|ClasseComptage.getClasse")), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(classeComptage, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Collections.EMPTY_LIST, UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void TestSupprimer() {
        ClasseComptage classeComptage = ClasseComptage.creer(ClasseType.Maternelle, 10);
        classeComptage.forceState(EntityStateType.Unchanged);
        LocalDateTime dateDeFermeture = LocalDateTime.now();
        classeComptage.supprimer(dateDeFermeture);

        // Suppression logique
        Assert.assertEquals(dateDeFermeture, classeComptage.getLogicalDeleteOn());
        Assert.assertEquals(EntityStateType.Modified, classeComptage.getState());
    }
}
