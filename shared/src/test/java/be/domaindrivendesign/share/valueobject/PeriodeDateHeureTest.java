package be.domaindrivendesign.share.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

/**
 * Created by eddie on 24/11/2015.
 */
public class PeriodeDateHeureTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testConstructor() {
        LocalDateTime debut = LocalDateTime.of(2010, 01, 01, 00, 00, 00);
        LocalDateTime fin = LocalDateTime.of(2015, 01, 01, 00, 00, 00);

        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.of(2010, 01, 01, 00, 00, 00), LocalDateTime.of(2015, 01, 01, 00, 00, 00));
        assertEquals(debut, periode1.getDebut());
        assertEquals(fin, periode1.getFin());
    }
}



