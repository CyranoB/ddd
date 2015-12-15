package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NumeroEntrepriseTest {
    @After
    public void cleanCallContext() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testNumeroEntrepriseCreer() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "333", "444");
        assertEquals("1", nbce.getPart01());
        assertEquals("222", nbce.getPart02());
        assertEquals("333", nbce.getPart03());
        assertEquals("444", nbce.getPart04());
    }

    @Test
    public void testNumeroEntrepriseToString() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "333", "444");

        assertEquals("BE-1-222-333-444", nbce.toString());
    }

    @Test
    public void testNumeroEntrepriseNotOnlyDigitsPart01() {
        NumeroEntreprise nbce = new NumeroEntreprise("a", "2222", "3333", "4444");

        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart01"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.DigitOnly.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("a"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseNotOnlyDigitsPart02() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "aaaa", "333", "444");

        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart02"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.DigitOnly.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("aaaa"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseNotOnlyDigitsPart03() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "aaaa", "444");

        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart03"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.DigitOnly.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("aaaa"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseNotOnlyDigitsPart04() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "333", "aaaa");

        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart04"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.DigitOnly.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("aaaa"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseLenghtPart01() {
        NumeroEntreprise nbce = new NumeroEntreprise("1111", "222", "333", "444");

        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart01"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Length.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("1111", "1"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseLenghtPart02() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "2", "333", "444");

        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart02"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Length.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("2", "3"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseLenghtPart03() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "3333", "444");

        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart03"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Length.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("3333", "3"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testNumeroEntrepriseLenghtPart04() {
        NumeroEntreprise nbce = new NumeroEntreprise("1", "222", "333", "4");

        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertEquals(nbce, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.shared.valueobject|NumeroEntreprise.getPart04"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Length.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList("4", "3"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }
}
