package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.translation.LanguageType;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by eddie on 25/11/15.
 */
public class ContactTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testContactCreer() {
        Contact01 c = new Contact01();

        assertEquals(new Adresse01(), c.getAdresse());
        Assert.assertEquals(new NumeroTelephone01(), c.getFax());
        assertEquals(new NumeroTelephone02(), c.getTelephone());
        Assert.assertEquals(new Email01(), c.getEmail());
        Assert.assertEquals("Storm Trooper", c.getNomPrenom());
        Assert.assertEquals(LanguageType.French, c.getLangue());
    }
}
