package be.domaindrivendesign.kernel.rule.error;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;

import java.util.List;

/**
 *
 */
public class RuleException extends KernelException {
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
        return String.format("%s \n %s \n", super.toString(), ruleViolations.toString());
    }

}
