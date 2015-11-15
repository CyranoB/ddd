package be.wallonie.ddd.kernel.rule.interfaces;

import be.wallonie.ddd.kernel.rule.type.RuleSeverityType;

import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public interface RuleViolation {
    RuleObject getRuleObject();

    List<String> getPropertyPaths();

    long getRuleId();

    RuleSeverityType getSeverityType();

    String getMessage();

    List<String> getValues();

    boolean isGuard();
}
