package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */
public class RuleObject02 implements RuleObject {
    int attribute02;
    LocalDateTime localDateTime02;
    String string02;
    Object object02;
    List<Object> lists02;
    DummyValueObject dummyValueObject02;

    public int getAttribute02() {
        return attribute02;
    }

    public void setAttribute02(int attribute02) {
        this.attribute02 = attribute02;
    }

    public LocalDateTime getLocalDateTime02() {
        return localDateTime02;
    }

    public void setLocalDateTime02(LocalDateTime localDateTime02) {
        this.localDateTime02 = localDateTime02;
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

    public DummyValueObject getDummyValueObject02() {
        return dummyValueObject02;
    }

    public void setDummyValueObject02(DummyValueObject dummyValueObject02) {
        this.dummyValueObject02 = dummyValueObject02;
    }
}
