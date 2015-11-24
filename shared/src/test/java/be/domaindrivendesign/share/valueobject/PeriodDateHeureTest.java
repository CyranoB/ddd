package be.domaindrivendesign.share.valueobject;

import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by eddie on 24/11/2015.
 */
public class PeriodDateHeureTest {
    @After
    public void tearDown() {
        UnitOfWorkRule.getInstance().clear();
    }

    @Test
    public void testConstructor() {
        LocalDateTime debut = LocalDateTime.parse("2000-01-01T00:00:00");
        LocalDateTime fin = LocalDateTime.parse("2015-01-01T00:00:00");

        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2015-01-01T00:00:00"));
        assertEquals(debut, periode1.getDebut());
        assertEquals(fin, periode1.getFin());
    }

    @Test
    public void testDureeEnMinute() {
        LocalDateTime debut = LocalDateTime.parse("2000-01-01T14:00:00");
        LocalDateTime fin = LocalDateTime.parse("2000-01-01T16:00:00");

        PeriodDateHeure periode1 = new PeriodDateHeure(debut, fin);
        assertEquals(120.0, periode1.dureeEnMinute(), 0.01);
    }

    @Test
    public void testModifierDebut() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-01T00:00:00"));
        PeriodDateHeure periode2 = periode1.modifierFin(LocalDateTime.parse("2015-01-01T00:00:00"));
        assertEquals(LocalDateTime.parse("2015-01-01T00:00:00"), periode2.getFin());
        assertEquals(LocalDateTime.parse("2000-01-01T00:00:00"), periode2.getDebut());
    }

    @Test
    public void testModifierFin() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-05T00:00:00"));
        PeriodDateHeure periode2 = periode1.modifierFin(LocalDateTime.parse("2010-01-01T00:00:00"));
        assertEquals(LocalDateTime.parse("2010-01-01T00:00:00"), periode2.getFin());
        assertEquals(LocalDateTime.parse("2000-01-01T00:00:00"), periode2.getDebut());
    }

    @Test
    public void testModifierDuree() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-05T00:00:00"));
        PeriodDateHeure periode2 = periode1.modifierDuree(Period.ofDays(15));
        assertEquals(new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-16T00:00:00")), periode2);
    }

    @Test
    public void testCreerUnJourAPartirDe() {
        PeriodDateHeure unJour = PeriodDateHeure.creerUnJourAPartirDe(LocalDateTime.parse("2000-01-01T00:00:00"));
        assertEquals(LocalDateTime.parse("2000-01-02T00:00:00"), unJour.getFin());
        assertEquals(LocalDateTime.parse("2000-01-01T00:00:00"), unJour.getDebut());
    }

    @Test
    public void testCreerUneSemaineAPartirDe() {
        PeriodDateHeure unJour = PeriodDateHeure.creerUneSemaineAPartirDe(LocalDateTime.parse("2000-01-01T00:00:00"));
        assertEquals(LocalDateTime.parse("2000-01-08T00:00:00"), unJour.getFin());
        assertEquals(LocalDateTime.parse("2000-01-01T00:00:00"), unJour.getDebut());
    }

    @Test
    public void testChevauchement() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-31T00:00:00"));
        PeriodDateHeure periode2 = periode1.modifierFin(LocalDateTime.parse("2000-01-15T00:00:00"));
        assertTrue(PeriodDateHeure.chevauchements(periode1, periode2));

        PeriodDateHeure periode3 = periode1.modifierDebut(LocalDateTime.parse("2000-01-21T00:00:00"));
        assertFalse(PeriodDateHeure.chevauchements(periode2, periode3));
    }

    @Test
    public void testPasDeChevauchement() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-01T00:00:00"), LocalDateTime.parse("2000-01-15T00:00:00"));
        PeriodDateHeure periode2 = new PeriodDateHeure(LocalDateTime.parse("2000-01-21T00:00:00"), LocalDateTime.parse("2000-01-31T00:00:00"));
        assertFalse(PeriodDateHeure.chevauchements(periode1, periode2));
    }


    @Test
    public void testChevauchementList() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2000-01-21T00:00:00"), LocalDateTime.parse("2000-01-31T00:00:00"));
        PeriodDateHeure periode2 = periode1.modifierFin(LocalDateTime.parse("2000-01-15T00:00:00"));
        PeriodDateHeure periode3 = periode1.modifierFin(LocalDateTime.parse("2000-02-15T00:00:00"));
        assertTrue(PeriodDateHeure.chevauchements(periode1, Arrays.asList(periode2, periode3)));
    }

    @Test
    public void testComprend() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2001-01-01T00:00:00"), LocalDateTime.parse("2001-01-31T00:00:00"));
        PeriodDateHeure periode2 = new PeriodDateHeure(LocalDateTime.parse("2001-01-15T00:00:00"), LocalDateTime.parse("2001-01-21T00:00:00"));
        assertTrue(PeriodDateHeure.comprend(periode1, periode2));
    }

    @Test
    public void testNeComprendPas() {
        PeriodDateHeure periode1 = new PeriodDateHeure(LocalDateTime.parse("2001-01-01T00:00:00"), LocalDateTime.parse("2000-01-31T00:00:00"));
        PeriodDateHeure periode2 = new PeriodDateHeure(LocalDateTime.parse("2010-01-15T00:00:00"), LocalDateTime.parse("2010-01-21T00:00:00"));
        assertFalse(PeriodDateHeure.comprend(periode1, periode2));
    }
}



