package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.common.valueobject.PeriodDateHeure;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

import java.sql.Date;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */

public class RuleObject01 implements RuleObject {
    int attribute01;
    Date date01;
    String string01;
    Object object01;
    List<Object> lists01;
    PeriodDateHeure periodDateHeure01;

    public int getAttribute01() {
        return attribute01;
    }

    public void setAttribute01(int attribute01) {
        this.attribute01 = attribute01;
    }

    public Date getDate01() {
        return date01;
    }

    public void setDate01(Date date01) {
        this.date01 = date01;
    }

    public String getString01() {
        return string01;
    }

    public void setString01(String string01) {
        this.string01 = string01;
    }

    public Object getObject01() {
        return object01;
    }

    public void setObject01(Object object01) {
        this.object01 = object01;
    }

    public List<Object> getLists01() {
        return lists01;
    }

    public void setLists01(List<Object> lists01) {
        this.lists01 = lists01;
    }

    public PeriodDateHeure getPeriodDateHeure01() {
        return periodDateHeure01;
    }

    public void setPeriodDateHeure01(PeriodDateHeure periodDateHeure01) {
        this.periodDateHeure01 = periodDateHeure01;
    }
}
