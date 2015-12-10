package be.domaindrivendesign.kernel.rule.entity;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;

/**
 * Created by asmolabs on 13/11/15.
 */
public class RuleObjectB implements RuleObject {

    private String name;

    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
