package be.domaindrivendesign.shared.valueobject;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

import java.time.LocalDateTime;

/**
 * Created by asmolabs on 15/11/15.
 */

public class PeriodDateHeure extends ValueObject {
    public final static LocalDateTime DATE_MAX_VALUE = LocalDateTime.of(2999, 01, 01, 00, 00, 00);
    public final static LocalDateTime DATE_MIN_VALUE = LocalDateTime.of(1754, 01, 01, 00, 00, 00);
    public final static PeriodDateHeure EMPTY = new PeriodDateHeure(DATE_MIN_VALUE, DATE_MAX_VALUE);

    private LocalDateTime debut;
    private LocalDateTime fin;

    public PeriodDateHeure(LocalDateTime debut, LocalDateTime fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public LocalDateTime getDebut() {
        return this.debut;
    }

    public LocalDateTime getFin() {
        return this.fin;
    }
}
