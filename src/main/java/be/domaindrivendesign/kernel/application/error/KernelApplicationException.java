package be.domaindrivendesign.kernel.application.error;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 20/11/15.
 */
public class KernelApplicationException extends KernelException {
    private List<RuleViolation> ruleViolations;

    public KernelApplicationException(List<RuleViolation> ruleViolations) {
        super("Application Exception");
        this.ruleViolations = ruleViolations;
    }

    public KernelApplicationException(String message) {
        super(message);
        this.ruleViolations = new ArrayList<>();
    }


    public KernelApplicationException(String message, Exception innerException) {
        super(message, innerException);
        this.ruleViolations = new ArrayList<>();
    }


    public List<RuleViolation> getRuleViolations() {
        return this.ruleViolations;
    }

    protected void setRuleViolations(List<RuleViolation> ruleViolations) {
        this.ruleViolations = ruleViolations;
    }
}
