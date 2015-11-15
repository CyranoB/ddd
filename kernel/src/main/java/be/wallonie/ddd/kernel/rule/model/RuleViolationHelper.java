package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;
import be.wallonie.ddd.kernel.rule.type.RuleSeverityType;

import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */
public class RuleViolationHelper {
    /**
     * @param violations
     * @return
     */
    public static boolean HasBlockingError(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.BlockingError).count() > 0;
    }

    /**
     * @param violations
     * @return
     */
    public static boolean HasError(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.Error).count() > 0;
    }

    /**
     * @param violations
     * @return
     */
    public static boolean HasWarning(List<RuleViolation> violations) {
        return violations != null && violations.stream().filter(x -> x.getSeverityType() == RuleSeverityType.Warning).count() > 0;
    }
}
