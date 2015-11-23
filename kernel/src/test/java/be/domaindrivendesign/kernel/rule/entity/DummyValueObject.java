package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;

import java.time.LocalDateTime;

/**
 * Created by asmolabs on 15/11/15.
 */

public class DummyValueObject extends ValueObject {
    public final static LocalDateTime DATE_MAX_VALUE = LocalDateTime.of(2999, 01, 01, 00, 00, 00);
    public final static LocalDateTime DATE_MIN_VALUE = LocalDateTime.of(1754, 01, 01, 00, 00, 00);
    public final static DummyValueObject EMPTY = new DummyValueObject(DATE_MIN_VALUE, DATE_MAX_VALUE);

    private LocalDateTime debut;
    private LocalDateTime fin;

    private DummyValueObject(LocalDateTime debut, LocalDateTime fin) {
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
