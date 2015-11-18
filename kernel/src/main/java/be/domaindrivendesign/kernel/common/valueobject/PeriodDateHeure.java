package be.domaindrivendesign.kernel.common.valueobject;

import java.util.Date;

/**
 * Created by asmolabs on 15/11/15.
 */

public class PeriodDateHeure extends ValueObject {
    public final static Date DATE_MAX_VALUE = new Date(2999, 01, 01);
    public final static Date DATE_MIN_VALUE = new Date(1754, 01, 01);
    public final static PeriodDateHeure EMPTY = new PeriodDateHeure(DATE_MIN_VALUE, DATE_MAX_VALUE);

    private Date debut;
    private Date fin;

    private PeriodDateHeure(Date debut, Date fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public Date getDebut() {
        return this.debut;
    }

    public Date getFin() {
        return this.fin;
    }
}
