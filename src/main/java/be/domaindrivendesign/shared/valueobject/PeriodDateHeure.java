package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Embeddable
public class PeriodDateHeure extends ValueObject {
    public static final LocalDateTime DATE_MAX_VALUE = LocalDateTime.of(2999, 1, 1, 0, 0, 0);
    public static final LocalDateTime DATE_MIN_VALUE = LocalDateTime.of(1754, 1, 1, 0, 0, 0);
    public static final PeriodDateHeure EMPTY = new PeriodDateHeure(DATE_MIN_VALUE, DATE_MAX_VALUE);

    protected LocalDateTime debut;
    protected LocalDateTime fin;

    protected PeriodDateHeure() {
    }

    public PeriodDateHeure(LocalDateTime debut, LocalDateTime fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public PeriodDateHeure(LocalDateTime debut, Period duree) {
        this(debut, debut.plus(duree));
    }

    public static PeriodDateHeure creerUnJourAPartirDe(LocalDateTime day) {
        return new PeriodDateHeure(day, day.plusDays(1));
    }

    public static PeriodDateHeure creerUneSemaineAPartirDe(LocalDateTime startDay) {
        return new PeriodDateHeure(startDay, startDay.plusDays(7));
    }

    public static boolean chevauchements(PeriodDateHeure periodDateHeure01, PeriodDateHeure periodDateHeure02) {
        return periodDateHeure01.getDebut().isBefore(periodDateHeure02.getFin()) &&
                periodDateHeure01.getFin().isAfter(periodDateHeure02.getDebut());

    }

    public static boolean chevauchements(PeriodDateHeure periodDateHeure, List<PeriodDateHeure> periodDateHeures) {
        for (PeriodDateHeure p : periodDateHeures) {
            if (chevauchements(periodDateHeure, p))
                return true;
        }
        return false;
    }

    public static boolean comprend(PeriodDateHeure periodDateHeure01, PeriodDateHeure periodDateHeure02) {
        return periodDateHeure01.getDebut().isBefore(periodDateHeure02.getDebut()) &&
                periodDateHeure01.getFin().isAfter(periodDateHeure02.getFin());
    }

    public double dureeEnMinute() {
        if (debut == null || fin == null)
            return 0.0;
        return ChronoUnit.MINUTES.between(debut, fin);
    }

    public PeriodDateHeure modifierFin(LocalDateTime newEnd) {
        return new PeriodDateHeure(debut, newEnd);
    }

    public PeriodDateHeure modifierDuree(Period duree) {
        return (debut != null) ? new PeriodDateHeure(debut, duree) : null;
    }

    public PeriodDateHeure modifierDebut(LocalDateTime debut) {
        return new PeriodDateHeure(debut, fin);
    }

    @Override
    public String toString() {
        return String.format("[%s-%s]", debut, fin);
    }


    public LocalDateTime getDebut() {
        return this.debut;
    }

    public LocalDateTime getFin() {
        return this.fin;
    }
}
