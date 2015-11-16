package be.domaindrivendesign.kernel.common.valueobject;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import lombok.Value;

import java.util.Date;

/**
 * Created by asmolabs on 15/11/15.
 */
@Value
public class PeriodDateHeure implements RuleObject {
    public final static Date DATE_MAX_VALUE = new Date(2999, 01, 01);
    public final static Date DATE_MIN_VALUE = new Date(1754, 01, 01);
    public final static PeriodDateHeure EMPTY = new PeriodDateHeure(DATE_MIN_VALUE, DATE_MAX_VALUE);
    Date debut;
    Date fin;
}
