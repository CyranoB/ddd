package be.wallonie.ddd.kernel.rule.entity;

import be.wallonie.ddd.kernel.rule.interfaces.RuleObject;

/**
 * Created by asmolabs on 13/11/15.
 */
public class RuleObjectA implements RuleObject {

    private int number;

    private String name;

    private RuleObjectB ruleObjectB;

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

    public RuleObjectB getRuleObjectB() {
        return ruleObjectB;
    }

    public void setRuleObjectB(RuleObjectB ruleObjectB) {
        this.ruleObjectB = ruleObjectB;
    }
}
