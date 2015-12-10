package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Created by eddie on 24/11/15.
 */
public class AdresseTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testAdresseCreer() {
        Adresse adresse = new Adresse("Rue XXX", 1180, "Uccle");

        Assert.assertEquals("Rue XXX", adresse.getAdr());
        Assert.assertEquals(1180, adresse.getCodePostal());
        Assert.assertEquals("Uccle", adresse.getLocalite());
        Assert.assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
    }

    @Test
    public void testAdresseAdrNull() {
        Adresse adresse = new Adresse(null, 1180, "Uccle");

        Assert.assertEquals(adresse, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.shared.valueobject|Adresse.getAdr"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testAdresseLocaliteNull() {
        Adresse adresse = new Adresse("Rue XXX", 1180, null);

        Assert.assertEquals(adresse, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.shared.valueobject|Adresse.getLocalite"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(emptyList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testCodePostalInvalide() {
        Adresse adresse = new Adresse("Rue XXX", 0, "Uccle");

        Assert.assertEquals(adresse, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        Assert.assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        Assert.assertEquals(singletonList("be.domaindrivendesign.shared.valueobject|Adresse.getCodePostal"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        Assert.assertEquals(RuleType.Between.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        Assert.assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        Assert.assertEquals(Arrays.asList("0", "1000", "9999"), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }
}