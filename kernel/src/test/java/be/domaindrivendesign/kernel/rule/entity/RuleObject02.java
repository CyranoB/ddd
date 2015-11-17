package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.common.valueobject.PeriodDateHeure;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */
@Data
public class RuleObject02 implements RuleObject {
    int attribute02;
    Date date02;
    String string02;
    Object object02;
    List<Object> lists02;
    PeriodDateHeure periodDateHeure02;
}
