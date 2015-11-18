package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.common.valueobject.PeriodDateHeure;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

import java.sql.Date;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */
public class RuleObject02 implements RuleObject {
    int attribute02;
    Date date02;
    String string02;
    Object object02;
    List<Object> lists02;
    PeriodDateHeure periodDateHeure02;

    public int getAttribute02() {
        return attribute02;
    }

    public void setAttribute02(int attribute02) {
        this.attribute02 = attribute02;
    }

    public Date getDate02() {
        return date02;
    }

    public void setDate02(Date date02) {
        this.date02 = date02;
    }

    public String getString02() {
        return string02;
    }

    public void setString02(String string02) {
        this.string02 = string02;
    }

    public Object getObject02() {
        return object02;
    }

    public void setObject02(Object object02) {
        this.object02 = object02;
    }

    public List<Object> getLists02() {
        return lists02;
    }

    public void setLists02(List<Object> lists02) {
        this.lists02 = lists02;
    }

    public PeriodDateHeure getPeriodDateHeure02() {
        return periodDateHeure02;
    }

    public void setPeriodDateHeure02(PeriodDateHeure periodDateHeure02) {
        this.periodDateHeure02 = periodDateHeure02;
    }
}
