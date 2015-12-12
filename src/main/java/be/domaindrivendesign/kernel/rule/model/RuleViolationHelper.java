package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;

import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleViolationHelper {

    private RuleViolationHelper() {}

    /**
     * @param violations
     * @return
     */
    public static boolean hasBlockingError(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.BlockingError).count() > 0;
    }

    /**
     * @param violations
     * @return
     */
    public static boolean hasError(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.Error).count() > 0;
    }

    /**
     * @param violations
     * @return
     */
    public static boolean hasWarning(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.Warning).count() > 0;
    }
}
