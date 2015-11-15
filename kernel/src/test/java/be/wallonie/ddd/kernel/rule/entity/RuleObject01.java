package be.wallonie.ddd.kernel.rule.entity;

import be.wallonie.ddd.kernel.common.valueobject.PeriodDateHeure;
import be.wallonie.ddd.kernel.rule.interfaces.RuleObject;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */
@Data
public class RuleObject01 implements RuleObject {
    int attribute01;
    Date date01;
    String string01;
    Object object01;
    List<Object> lists01;
    PeriodDateHeure periodDateHeure01;
}
