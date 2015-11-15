package be.wallonie.ddd.kernel.rule.entity;

import be.wallonie.ddd.kernel.rule.interfaces.RuleObject;

/**
 * Created by asmolabs on 13/11/15.
 */
public class MyClass implements RuleObject {

    private int number;

    private String name;

    private MySubClass mySubClass;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MySubClass getMySubClass() {
        return mySubClass;
    }

    public void setMySubClass(MySubClass mySubClass) {
        this.mySubClass = mySubClass;
    }
}
