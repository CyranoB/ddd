package be.domaindrivendesign.kernel.rule.interfaces;

import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public interface RuleViolation extends Serializable {
    RuleObject getRuleObject();

    List<String> getPropertyPaths();

    long getRuleId();

    RuleSeverityType getSeverityType();

    String getMessage();

    List<String> getValues();

    boolean isGuard();
}
