package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.context.KernelContext;
import be.wallonie.ddd.kernel.context.StandAloneContext;
import be.wallonie.ddd.kernel.rule.error.RuleException;
import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;
import be.wallonie.ddd.kernel.rule.type.RuleSeverityType;
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

    public static UnitOfWorkRule getInstance() {
        return unitOfWorkRule;
    }

    public List<RuleViolation> getViolations() {

        String contextKey = "UnitOfWorkManager.UnitOfWorkRule";
        List<RuleViolation> violations = ((KernelContext) staticApplicationContext.getBean("KernelContext")).getData(contextKey);

        if (violations == null) {
            violations = new ArrayList<>();

            ((KernelContext) staticApplicationContext.getBean("KernelContext")).setData(contextKey, violations);
        }

        return violations;
    }

    public void clear() {
        ((KernelContext) staticApplicationContext.getBean("KernelContext")).clearData(contextKey);
    }

    public void addViolation(RuleViolation violation) {
        getViolations().add(violation);
    }

    public boolean hasViolations() {
        return getViolations() != null && getViolations().size() > 0;
    }

    public boolean hasBlockingError() {
        return RuleViolationHelper.HasBlockingError(getViolations());
    }

    public boolean hasError() {
        return RuleViolationHelper.HasError(getViolations());
    }

    public boolean hasWarning() {
        return RuleViolationHelper.HasWarning(getViolations());
    }

    public void raiseExceptionInCaseOfError() {
        if (hasError()) {
            throw new RuleException(getViolations());
        }
    }

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
