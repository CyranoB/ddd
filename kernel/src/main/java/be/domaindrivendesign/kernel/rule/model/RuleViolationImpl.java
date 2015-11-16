package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import java.util.List;

/**
 * Created by asmolabs on 13/11/15.
 */
public class RuleViolationImpl implements RuleViolation {
    private RuleObject ruleObject;
    private List<String> propertyPaths;
    private long ruleId;
    private RuleSeverityType severityType;
    private String message;
    private List<String> values;
    private boolean isGuard;

    public RuleViolationImpl(
            RuleObject ruleObject,
            List<String> propertyPaths,
            long ruleId,
            RuleSeverityType severityType,
            String message,
            List<String> values,
            boolean isGuard) {
        this.ruleObject = ruleObject;
        this.propertyPaths = propertyPaths;
        this.ruleId = ruleId;
        this.severityType = severityType;
        this.message = message;
        this.values = values;
        this.isGuard = isGuard;
    }

    @Override
    public RuleObject getRuleObject() {
        return ruleObject;
    }

    public void setRuleObject(RuleObject ruleObject) {
        this.ruleObject = ruleObject;
    }

    @Override
    public List<String> getPropertyPaths() {
        return propertyPaths;
    }

    public void setPropertyPaths(List<String> propertyPaths) {
        this.propertyPaths = propertyPaths;
    }

    @Override
    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public RuleSeverityType getSeverityType() {
        return severityType;
    }

    public void setSeverityType(RuleSeverityType severityType) {
        this.severityType = severityType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public boolean isGuard() {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }
}
