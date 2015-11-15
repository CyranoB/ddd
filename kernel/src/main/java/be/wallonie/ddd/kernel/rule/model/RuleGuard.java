package be.wallonie.ddd.kernel.rule.model;

import be.wallonie.ddd.kernel.common.model.Guard;
import be.wallonie.ddd.kernel.rule.error.RuleException;
import be.wallonie.ddd.kernel.rule.interfaces.RuleObject;
import be.wallonie.ddd.kernel.rule.interfaces.RuleViolation;
import be.wallonie.ddd.kernel.rule.type.RuleSeverityType;
import com.trigersoft.jaque.expression.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 */
public class RuleGuard {


    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, int invariant) {
        return equalsInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, int invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();


        return (Guard.equals((Integer) t, invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), 1, severityType);
    }

    /**
     *
     * @param ruleObject
     * @param propertyLambda
     * @param value
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, String value, int ruleId, RuleSeverityType severityType) {
        List<String> values = new ArrayList<>();

        values.add(propertyLambda.get().toString());
        values.add(value);

        return raiseViolation(ruleObject, propertyLambda, values, ruleId, severityType);
    }

    /**
     *
     * @param ruleObject
     * @param propertyLambda
     * @param values
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, List<String> values, int ruleId, RuleSeverityType severityType) {
        RuleViolation ruleViolation = new RuleViolationImpl(
                ruleObject,
                new ArrayList<>(),
                ruleId,
                severityType,
                null,
                new ArrayList<>(),
                true);

        final LambdaExpression<Supplier<T>> parsedTree =
                LambdaExpression.parse(propertyLambda);

        Expression body = parsedTree.getBody();

        StringBuffer buffer = new StringBuffer();

        completePropertyPath(body, buffer);

        ruleViolation.getPropertyPaths().add(buffer.toString());

        for (String value : values) {
            ruleViolation.getValues().add(value);
        }

        UnitOfWorkRule.getDefault().getViolations().add(ruleViolation);

        if (severityType == RuleSeverityType.BlockingError)
            throw new RuleException(UnitOfWorkRule.getDefault().getViolations());

        return false;
    }

    /**
     *
     * @param expression
     * @param buffer
     */
    private static void completePropertyPath(Expression expression, StringBuffer buffer) {

        switch (expression.getExpressionType()) {
            case ExpressionType.Convert:

                UnaryExpression unaryExpression = (UnaryExpression) expression;

                final Expression first = unaryExpression.getFirst();

                completePropertyPath(first, buffer);

                System.out.println();
                break;
            case ExpressionType.Lambda:
                LambdaExpression lambdaExpression = (LambdaExpression) expression;

                completePropertyPath(lambdaExpression.getBody(), buffer);

                System.out.println();
                break;
            case ExpressionType.Invoke:
                InvocationExpression invocationExpression = (InvocationExpression) expression;

                final List<Expression> arguments = invocationExpression.getArguments();

                if (buffer.toString().equals("")) {

                    ConstantExpression constantExpression = (ConstantExpression) arguments.get(0);

                    Class baseClass = constantExpression.getResultType();

                    buffer.append(baseClass.getName());

                    buffer.append("|");

                    completePropertyPath(invocationExpression.getTarget(), buffer);
                } else {


                    completePropertyPath(invocationExpression.getTarget(), buffer);
                    System.out.println();
                }
                break;
            case ExpressionType.MethodAccess:
                MemberExpression memberExpression = (MemberExpression) expression;

                buffer.append(memberExpression.getMember().getName());

                System.out.println();
                break;
            default:
                break;
        }

    }

    /**
     *
     * @param <T>
     */
    public static interface Property<T> extends Supplier<T>, Serializable {
    }
}
