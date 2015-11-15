package be.wallonie.ddd.kernel.rule.error;

import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;

import java.util.List;

/**
 *
 */
public class RuleException extends RuntimeException {
    private List<RuleViolation> ruleViolations;

    /**
     * @param ruleViolations
     */
    public RuleException(List<RuleViolation> ruleViolations) {
        super("Rule Violation");
        this.ruleViolations = ruleViolations;
    }

    /**
     *
     * @param message
     */
    public RuleException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param innerException
     */
    public RuleException(String message, Exception innerException) {
        super(message, innerException);
    }

    /**
     *
     * @return
     */
    public List<RuleViolation> getRuleViolations() {
        return ruleViolations;
    }

    @Override
    public String toString() {
        return String.format("{0} \n {1} \n", super.toString(), ruleViolations.toString());
    }

}
