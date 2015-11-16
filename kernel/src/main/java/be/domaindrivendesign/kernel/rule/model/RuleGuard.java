package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.common.model.Guard;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
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
    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant) {
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
    public static <T> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        return (Guard.equals((Integer) t, invariant)) || raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), RuleType.EqualsNumberInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T> boolean smallerThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant) {
        return smallerThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean smallerThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, int invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        return (Guard.smallerThan((Integer) t, invariant)) || raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), RuleType.SmallerThanInvariant.typeValue, severityType);
    }

    /**
     *
     * @param ruleObject
     * @param propertyLambda01
     * @param value
     * @param minimum
     * @param maximum
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean Between(RuleObject ruleObject, Property<T> propertyLambda01, int value, int minimum, int maximum, RuleSeverityType severityType)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add(Integer.toString(value));
        list.add(Integer.toString(minimum));
        list.add(Integer.toString(maximum));
        return (Guard.betweenNumber(value, minimum, maximum)) || raiseViolation(ruleObject, propertyLambda01, list, RuleType.Between.typeValue, severityType);
    }

    /**
     *
     * @param ruleObject
     * @param propertyLambda01
     * @param value
     * @param minimum
     * @param maximum
     * @param <T>
     * @return
     */
    public static <T> boolean Between(RuleObject ruleObject, Property<T> propertyLambda01, int value, int minimum, int maximum)
    {
        return Between(ruleObject,propertyLambda01,value,minimum,maximum,RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant) {
        return smallerOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        return (Guard.smallerOrEqualThan((Integer) t, invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), RuleType.SmallerOrEqualThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T> boolean greaterThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant) {
        return greaterThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean greaterThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        return (Guard.greaterThan((Integer) t, invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), RuleType.GreaterThanInvariant.typeValue, severityType);

    }

    public static <T> boolean greaterOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant) {
        return greaterOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean greaterOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, Integer invariant, RuleSeverityType severityType) {
        final T t = propertyLambda.get();

        return (Guard.greaterOrEqualThan((Integer) t, invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, Integer.toString(invariant), RuleType.GreaterOrEqualThanInvariant.typeValue, severityType);
    }

    /**
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

        UnitOfWorkRule.getInstance().getViolations().add(ruleViolation);

        if (severityType == RuleSeverityType.BlockingError)
            throw new RuleException(UnitOfWorkRule.getInstance().getViolations());

        return false;
    }

    /**
     * @param expression
     * @param buffer
     */
    private static void completePropertyPath(Expression expression, StringBuffer buffer) {

        switch (expression.getExpressionType()) {
            case ExpressionType.Convert:

                UnaryExpression unaryExpression = (UnaryExpression) expression;

                final Expression first = unaryExpression.getFirst();

                completePropertyPath(first, buffer);

                break;
            case ExpressionType.Lambda:
                LambdaExpression lambdaExpression = (LambdaExpression) expression;

                completePropertyPath(lambdaExpression.getBody(), buffer);

                break;
            case ExpressionType.Invoke:
                InvocationExpression invocationExpression = (InvocationExpression) expression;

                final List<Expression> arguments = invocationExpression.getArguments();

                if (buffer.toString().equals("")) {

                    ConstantExpression constantExpression = (ConstantExpression) arguments.get(0);

                    Class baseClass = constantExpression.getResultType();

                    buffer.append(baseClass.getPackage().getName());

                    buffer.append("|");

                    buffer.append(baseClass.getSimpleName());

                    buffer.append(".");

                    completePropertyPath(invocationExpression.getTarget(), buffer);
                } else {
                    completePropertyPath(invocationExpression.getTarget(), buffer);
                }
                break;
            case ExpressionType.MethodAccess:
                MemberExpression memberExpression = (MemberExpression) expression;

                final Expression instance = memberExpression.getInstance();

                if (instance != null) {
                    String before = buffer.toString();

                    completePropertyPath(instance, buffer);

                    if (!before.equals(buffer.toString())) {
                        buffer.append(".");
                    }
                }

                buffer.append(memberExpression.getMember().getName());

                break;
            default:
                break;
        }

    }

    /**
     * @param <T>
     */
    public interface Property<T> extends Supplier<T>, Serializable {
    }
}
