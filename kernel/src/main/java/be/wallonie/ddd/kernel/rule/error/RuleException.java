package be.wallonie.ddd.kernel.rule.error;

import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;

import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleException extends Exception {
    private List<RuleViolation> ruleViolations;

    public RuleException(List<RuleViolation> ruleViolations) {
        super("Rule Violation");
        this.ruleViolations = ruleViolations;
    }

    public RuleException(String message) {
        super(message);
    }

    public RuleException(String message, Exception innerException) {
        super(message, innerException);
    }

    public List<RuleViolation> getRuleViolations() {
        return ruleViolations;
    }

    @Override
    public String toString() {
        return String.format("{0} \n {1} \n", super.toString(), ruleViolations.toString());
    }

}
