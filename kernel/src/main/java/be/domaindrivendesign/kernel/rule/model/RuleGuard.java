package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.common.model.Guard;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import com.trigersoft.jaque.expression.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 */
public class RuleGuard {

//region Number

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant) {
        return equalsInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant La valeur de référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.equals(propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.EqualsNumberInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant La valeur de référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant) {
        return smallerThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.smallerThan(propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param minimum
     * @param maximum
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean between(RuleObject ruleObject, Property<T> propertyLambda01, T minimum, T maximum, RuleSeverityType severityType) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(propertyLambda01.get().toString());
        list.add(minimum.toString());
        list.add(maximum.toString());
        return (Guard.betweenNumber(propertyLambda01.get(), minimum, maximum)) || raiseViolation(ruleObject, propertyLambda01, list, RuleType.Between.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param minimum
     * @param maximum
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean between(RuleObject ruleObject, Property<T> propertyLambda01, T minimum, T maximum) {
        return between(ruleObject, propertyLambda01, minimum, maximum, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant) {
        return smallerOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.smallerOrEqualThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerOrEqualThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant) {
        return greaterThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.greaterThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterThanInvariant.typeValue, severityType);

    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @return
     */
    public static boolean greaterOrEqualThanInvariant(RuleObject ruleObject, Property<Integer> propertyLambda, Integer invariant) {
        return greaterOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThanInvariant(RuleObject ruleObject, Property<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.greaterOrEqualThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterOrEqualThanInvariant.typeValue, severityType);
    }


    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return equals(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }


    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.equals(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.EqualsNumber.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return smallerThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.smallerThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.SmallerThan.typeValue
                , severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return greaterThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType ff
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.greaterThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterThan.typeValue
                , severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return smallerOrEqualThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.smallerOrEqualThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.SmallerOrEqualThan.typeValue, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return greaterOrEqualThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.greaterOrEqualThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterOrEqualThan.typeValue, severityType);
    }

    public static <T extends Number & Comparable<T>> boolean domain(RuleObject ruleObject, Property propertyLambda01, List<T> domain) {
        return domain(ruleObject, propertyLambda01, domain, RuleSeverityType.Error);
    }

    public static <T extends Number & Comparable<T>> boolean domain(RuleObject ruleObject, Property propertyLambda01, List<T> domain, RuleSeverityType severityType) {
        if (Guard.domain((T) propertyLambda01.get(), domain)) {
            return true;
        }

        List<String> values = new ArrayList<>();

        values.add(propertyLambda01.get().toString());

        domain.forEach(x -> values.add(x.toString()));

        raiseViolation(ruleObject, propertyLambda01, values, RuleType.Domain.typeValue, severityType);
        return false;
    }

    //endregion

    //region double

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property propertyLambda01, Integer digitBeforeComma, Integer decimalPlaces) {
        return isFormat(ruleObject, propertyLambda01, digitBeforeComma, decimalPlaces, false, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param isNullAllowed
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property propertyLambda01, Integer digitBeforeComma, Integer decimalPlaces, boolean isNullAllowed) {
        return isFormat(ruleObject, propertyLambda01, digitBeforeComma, decimalPlaces, isNullAllowed, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param isNullAllowed
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property propertyLambda01, Integer digitBeforeComma, Integer decimalPlaces, boolean isNullAllowed, RuleSeverityType severityType) {
        return (Guard.isFormat((T) propertyLambda01.get(), digitBeforeComma, decimalPlaces, isNullAllowed)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, Arrays.asList(propertyLambda01.get() == null ? null : propertyLambda01.get().toString(), digitBeforeComma.toString(), decimalPlaces.toString()), RuleType.IsFormatDecimal.typeValue, severityType);
    }

    //endregion

    //region RaiseViolation

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
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
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, int ruleId, RuleSeverityType severityType) {
        List<String> values = new ArrayList<>();

        values.add(propertyLambda01.get().toString());
        values.add(propertyLambda02.get().toString());

        return raiseViolation(ruleObject, Arrays.asList(propertyLambda01, propertyLambda02), values, ruleId, severityType);
    }


    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param values
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, List<String> values, int ruleId, RuleSeverityType severityType) {
        return raiseViolation(ruleObject, Arrays.asList(propertyLambda), values, ruleId, severityType);
    }

    /**
     * @param ruleObject L'objet sur lequel la validation est effectué
     * @param propertiesLambda
     * @param values
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, List<Property<T>> propertiesLambda, List<String> values, int ruleId, RuleSeverityType severityType) {
        RuleViolation ruleViolation = new RuleViolationImpl(
                ruleObject,
                new ArrayList<>(),
                ruleId,
                severityType,
                null,
                new ArrayList<>(),
                true);

        for (Property propertyLambda : propertiesLambda) {
            final LambdaExpression<Supplier<T>> parsedTree =
                    LambdaExpression.parse(propertyLambda);

            Expression body = parsedTree.getBody();

            StringBuffer buffer = new StringBuffer();

            completePropertyPath(body, buffer);

            ruleViolation.getPropertyPaths().add(buffer.toString());
        }

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

    //endregion

    /**
     * @param <T>
     */
    public interface Property<T> extends Supplier<T>, Serializable {
    }
}
