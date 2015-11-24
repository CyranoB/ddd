package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.common.valueobject.ValueObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by asmolabs on 15/11/15.
 */

public class RuleObject01 extends ValueObject implements RuleObject {
    int attribute01;
    LocalDateTime localDateTime01;
    String string01;
    Object object01;
    List<Object> lists01;
    DummyValueObject dummyValueObject01;
    double double01;

    public double getDouble01() {
        return double01;
    }

    public void setDouble01(double double01) {
        this.double01 = double01;
    }

    public int getAttribute01() {
        return attribute01;
    }

    public void setAttribute01(int attribute01) {
        this.attribute01 = attribute01;
    }

    public LocalDateTime getLocalDateTime01() {
        return localDateTime01;
    }

    public void setLocalDateTime01(LocalDateTime localDateTime01) {
        this.localDateTime01 = localDateTime01;
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

    public DummyValueObject getDummyValueObject01() {
        return dummyValueObject01;
    }

    public void setDummyValueObject01(DummyValueObject dummyValueObject01) {
        this.dummyValueObject01 = dummyValueObject01;
    }
}
