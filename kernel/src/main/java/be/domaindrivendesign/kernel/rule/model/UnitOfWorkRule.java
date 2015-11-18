package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.common.context.KernelContext;
import be.domaindrivendesign.kernel.common.context.StandAloneContext;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import org.springframework.context.support.StaticApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asmolabs on 14/11/15.
 */

public class UnitOfWorkRule {

    private static final UnitOfWorkRule unitOfWorkRule = new UnitOfWorkRule();

    private final StaticApplicationContext staticApplicationContext;

    private final String contextKey = "UnitOfWorkManager.UnitOfWorkRule";

    /**
     *
     */
    private UnitOfWorkRule() {
        staticApplicationContext = new StaticApplicationContext();

        staticApplicationContext.registerSingleton("KernelContext", StandAloneContext.class);
    }

    /**
     * @return
     */
    public static UnitOfWorkRule getInstance() {
        return unitOfWorkRule;
    }

    /**
     *
     * @return
     */
    public List<RuleViolation> getViolations() {

        String contextKey = "UnitOfWorkManager.UnitOfWorkRule";
        List<RuleViolation> violations = ((KernelContext) staticApplicationContext.getBean("KernelContext")).getData(contextKey);

        if (violations == null) {
            violations = new ArrayList<>();

            ((KernelContext) staticApplicationContext.getBean("KernelContext")).setData(contextKey, violations);
        }

        return violations;
    }

    /**
     *
     */
    public void clear() {
        ((KernelContext) staticApplicationContext.getBean("KernelContext")).clearData(contextKey);
    }

    /**
     *
     * @param violation
     */
    public void addViolation(RuleViolation violation) {
        getViolations().add(violation);
    }

    /**
     *
     * @return
     */
    public boolean hasViolations() {
        return getViolations() != null && getViolations().size() > 0;
    }

    /**
     *
     * @return
     */
    public boolean hasBlockingError() {
        return RuleViolationHelper.HasBlockingError(getViolations());
    }

    /**
     *
     * @return
     */
    public boolean hasError() {
        return RuleViolationHelper.HasError(getViolations());
    }

    /**
     *
     * @return
     */
    public boolean hasWarning() {
        return RuleViolationHelper.HasWarning(getViolations());
    }

    /**
     *
     */
    public void raiseExceptionInCaseOfError() {
        if (hasError()) {
            throw new RuleException(getViolations());
        }
    }

    /**
     *
     * @return
     */
    public RuleSeverityType getSeverity() {
        if (hasBlockingError())
            return RuleSeverityType.BlockingError;
        if (hasError())
            return RuleSeverityType.BlockingError;
        if (hasWarning())
            return RuleSeverityType.Warning;
        return null;
    }
}
