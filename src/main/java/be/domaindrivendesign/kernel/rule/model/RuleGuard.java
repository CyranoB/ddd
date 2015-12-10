package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.common.model.Guard;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.lambda.*;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import com.trigersoft.jaque.expression.*;

import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Supplier;

/**
 *
 */
public class RuleGuard {

//region Number


    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant      La valeur de référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant) {
        return smallerThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.smallerThan(propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param minimum
     * @param maximum
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean between(RuleObject ruleObject, NumberProperty<T> propertyLambda01, T minimum, T maximum, RuleSeverityType severityType) {
        ArrayList<String> list = new ArrayList<>();
        list.add(propertyLambda01.get().toString());
        list.add(minimum.toString());
        list.add(maximum.toString());
        return (Guard.betweenNumber(propertyLambda01.get(), minimum, maximum)) || raiseViolation(ruleObject, propertyLambda01, list, RuleType.Between.typeValue, severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param minimum
     * @param maximum
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean between(RuleObject ruleObject, NumberProperty<T> propertyLambda01, T minimum, T maximum) {
        return between(ruleObject, propertyLambda01, minimum, maximum, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant) {
        return smallerOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.smallerOrEqualThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerOrEqualThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant) {
        return greaterThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.greaterThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterThanInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant) {
        return greaterOrEqualThanInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThanInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.greaterOrEqualThan(propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterOrEqualThanInvariant.typeValue, severityType);
    }


    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02) {
        return equals(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.equals(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.EqualsNumber.typeValue, severityType);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant) {
        return equalsInvariant(ruleObject, propertyLambda, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param invariant      La valeur de référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, NumberProperty<T> propertyLambda, T invariant, RuleSeverityType severityType) {
        return (Guard.equals(propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.EqualsNumberInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02) {
        return smallerThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02, RuleSeverityType severityType) {
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
    public static <T extends Number & Comparable<T>> boolean greaterThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02) {
        return greaterThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType     ff
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.greaterThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterThan.typeValue
                , severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02) {
        return smallerOrEqualThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean smallerOrEqualThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.smallerOrEqualThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.SmallerOrEqualThan.typeValue, severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02) {
        return greaterOrEqualThan(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param propertyLambda02 La propriété dans laquelle on va chercher la référence
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterOrEqualThan(RuleObject ruleObject, NumberProperty<T> propertyLambda01, NumberProperty<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.greaterOrEqualThan(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterOrEqualThan.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean domain(RuleObject ruleObject, NumberProperty<T> propertyLambda01, List<T> domain) {
        return domain(ruleObject, propertyLambda01, domain, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean domain(RuleObject ruleObject, NumberProperty<T> propertyLambda01, List<T> domain, RuleSeverityType severityType) {
        if (Guard.domain(propertyLambda01.get(), domain)) {
            return true;
        }

        List<String> values = new ArrayList<>();

        values.add(propertyLambda01.get().toString());

        domain.forEach(x -> values.add(x.toString()));

        raiseViolation(ruleObject, propertyLambda01, values, RuleType.DomainNumber.typeValue, severityType);
        return false;
    }

    //endregion

    //region double

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property propertyLambda01, int digitBeforeComma, int decimalPlaces) {
        return isFormat(ruleObject, propertyLambda01, digitBeforeComma, decimalPlaces, false, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param isNullAllowed
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property propertyLambda01, int digitBeforeComma, int decimalPlaces, boolean isNullAllowed) {
        return isFormat(ruleObject, propertyLambda01, digitBeforeComma, decimalPlaces, isNullAllowed, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
     * @param propertyLambda01 La propriété sur laquelle la validation est effectuée
     * @param digitBeforeComma
     * @param decimalPlaces
     * @param isNullAllowed
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Double> boolean isFormat(RuleObject ruleObject, Property<T> propertyLambda01, int digitBeforeComma, int decimalPlaces, boolean isNullAllowed, RuleSeverityType severityType) {
        return (Guard.isFormat(propertyLambda01.get(), digitBeforeComma, decimalPlaces, isNullAllowed)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, Arrays.asList(propertyLambda01.get() == null ? null : propertyLambda01.get().toString(), Integer.toString(digitBeforeComma), Integer.toString(decimalPlaces)), RuleType.IsFormatDecimal.typeValue, severityType);
    }

    //endregion

    //region Temporal

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean before(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02) {
        return before(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean before(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.before(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.Before.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant) {
        return beforeInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.before(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.BeforeInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeOrEqual(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02) {
        return beforeOrEqual(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeOrEqual(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.beforeOrEqual(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.BeforeOrEqual.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeOrEqualInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant) {
        return beforeOrEqualInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean beforeOrEqualInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.beforeOrEqual(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.BeforeOrEqualInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean after(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02) {
        return after(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean after(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.after(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.After.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant) {
        return afterInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.after(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.AfterInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterOrEqual(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02) {
        return afterOrEqual(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterOrEqual(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, TemporalProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.afterOrEqual(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.AfterOrEqual.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterOrEqualInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant) {
        return afterOrEqualInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean afterOrEqualInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.afterOrEqual(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.AfterOrEqualInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambdaTemporal01
     * @param propertyLambdaTemporal02
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean equals(RuleObject ruleObject, TemporalProperty<T> propertyLambdaTemporal01, TemporalProperty<T> propertyLambdaTemporal02) {
        return equals(ruleObject, propertyLambdaTemporal01, propertyLambdaTemporal02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambdaTemporal01
     * @param propertyLambdaTemporal02
     * @param severityType
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean equals(RuleObject ruleObject, TemporalProperty<T> propertyLambdaTemporal01, TemporalProperty<T> propertyLambdaTemporal02, RuleSeverityType severityType) {

        return (Guard.equals(propertyLambdaTemporal01.get(), propertyLambdaTemporal02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambdaTemporal01, propertyLambdaTemporal02, RuleType.EqualsTemporal.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant) {
        return equalsInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */

    public static <T extends Temporal & Comparable<T>> boolean equalsInvariant(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.equals(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.EqualsTemporalInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param <T>
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean domain(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, List<T> domain) {
        return domain(ruleObject, propertyLambda01, domain, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Temporal & Comparable<T>> boolean domain(RuleObject ruleObject, TemporalProperty<T> propertyLambda01, List<T> domain, RuleSeverityType severityType) {
        if (Guard.domain(propertyLambda01.get(), domain)) {
            return true;
        }

        List<String> values = new ArrayList<>();

        values.add(propertyLambda01.get().toString());

        domain.forEach(x -> values.add(x.toString()));

        raiseViolation(ruleObject, propertyLambda01, values, RuleType.DomainTemporal.typeValue, severityType);
        return false;
    }

    /*public static <T extends Temporal & Comparable> boolean include(RuleObject ruleObject, Property<T> propertyLambda01, Property propertyLambda02, RuleSeverityType severityType)
    {
        return (PeriodDateHeure.(value01, value02)) || RuleGuard.RaiseViolation(ruleObject, propertyLambda01, value01.ToString(), propertyLambda02, value02.ToString(), (int)RuleGuardRuleId.Include, severityType);
    }*/

    //endregion

    //region String

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean nullOrEmpty(RuleObject ruleObject, StringProperty<T> propertyLambda) {
        return nullOrEmpty(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean nullOrEmpty(RuleObject ruleObject, StringProperty<T> propertyLambda, RuleSeverityType severityType) {
        return (Guard.nullOrEmpty(propertyLambda.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.NullOrEmpty.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends String & Comparable<String>> boolean equals(RuleObject ruleObject, StringProperty<T> propertyLambda01, StringProperty<T> propertyLambda02) {
        return equals(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends String & Comparable<String>> boolean equals(RuleObject ruleObject, StringProperty<T> propertyLambda01, StringProperty<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.equals(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.EqualsString.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static <T extends String & Comparable<String>> boolean equalsInvariant(RuleObject ruleObject, StringProperty<T> propertyLambda01, T invariant) {
        return equalsInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */

    public static <T extends String & Comparable<String>> boolean equalsInvariant(RuleObject ruleObject, StringProperty<T> propertyLambda01, T invariant, RuleSeverityType severityType) {
        return (Guard.equals(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant, RuleType.EqualsStringInvariant.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean digitOnly(RuleObject ruleObject, StringProperty<T> propertyLambda) {
        return digitOnly(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean digitOnly(RuleObject ruleObject, StringProperty<T> propertyLambda, RuleSeverityType severityType) {
        return Guard.digitOnly(propertyLambda.get()) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.DigitOnly.typeValue, severityType);
    }

    public static <T extends String & Comparable<String>> boolean length(RuleObject ruleObject, StringProperty<T> propertyLambda, int length) {
        return length(ruleObject, propertyLambda, length, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param length
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean length(RuleObject ruleObject, StringProperty<T> propertyLambda, int length, RuleSeverityType severityType) {
        return Guard.length(propertyLambda.get(), length) || RuleGuard.raiseViolation(ruleObject, propertyLambda, Integer.toString(length), RuleType.Length.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean domain(RuleObject ruleObject, StringProperty<T> propertyLambda01, List<T> domain) {
        return domain(ruleObject, propertyLambda01, domain, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param domain
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String & Comparable<String>> boolean domain(RuleObject ruleObject, StringProperty<T> propertyLambda01, List<T> domain, RuleSeverityType severityType) {
        if (Guard.domain(propertyLambda01.get(), domain)) {
            return true;
        }

        List<String> values = new ArrayList<>();

        values.add(propertyLambda01.get());

        domain.forEach(x -> values.add(x));

        raiseViolation(ruleObject, propertyLambda01, values, RuleType.DomainString.typeValue, severityType);
        return false;
    }

    //endregion

    //region Object

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T> boolean mandatory(RuleObject ruleObject, Property<T> propertyLambda) {
        return mandatory(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean mandatory(RuleObject ruleObject, Property<T> propertyLambda, RuleSeverityType severityType) {
        if (propertyLambda.get() instanceof String) {
            return (!Guard.nullOrEmpty((String) propertyLambda.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.Mandatory.typeValue, severityType);
        } else {
            return (propertyLambda.get() != null) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.Mandatory.typeValue, severityType);
        }
    }

    // endregion

    //region List

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param <T>
     * @return
     */
    public static <T> boolean nbrOfElements(RuleObject ruleObject, ListProperty<List<T>> propertyLambdas, int minElement, int maxElement) {
        return nbrOfElements(ruleObject, propertyLambdas, minElement, maxElement, false, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param nullCountAsElement
     * @param <T>
     * @return
     */
    public static <T> boolean nbrOfElements(RuleObject ruleObject, ListProperty<List<T>> propertyLambdas, int minElement, int maxElement, boolean nullCountAsElement) {
        return nbrOfElements(ruleObject, propertyLambdas, minElement, maxElement, nullCountAsElement, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param nullCountAsElement
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean nbrOfElements(RuleObject ruleObject, ListProperty<List<T>> propertyLambdas, int minElement, int maxElement, boolean nullCountAsElement, RuleSeverityType severityType) {
        return Guard.nbrOfElements(propertyLambdas.get(), minElement, maxElement, nullCountAsElement) || RuleGuard.raiseViolation(ruleObject, propertyLambdas, new ArrayList<>(Arrays.asList(propertyLambdas.get() == null ? "0" : Integer.toString(propertyLambdas.get().size()), Integer.toString(minElement), Integer.toString(maxElement))), RuleType.NbrOfElementsInList.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param <T>
     * @return
     */
    public static <TKey, T> boolean nbrOfElements(RuleObject ruleObject, MapProperty<Map<TKey, T>> propertyLambdas, int minElement, int maxElement) {
        return nbrOfElements(ruleObject, propertyLambdas, minElement, maxElement, false, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param nullCountAsElement
     * @param <T>
     * @return
     */
    public static <TKey, T> boolean nbrOfElements(RuleObject ruleObject, MapProperty<Map<TKey, T>> propertyLambdas, int minElement, int maxElement, boolean nullCountAsElement) {
        return nbrOfElements(ruleObject, propertyLambdas, minElement, maxElement, nullCountAsElement, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambdas
     * @param minElement
     * @param maxElement
     * @param nullCountAsElement
     * @param severityType
     * @param <T>
     * @return
     */
    public static <TKey, T> boolean nbrOfElements(RuleObject ruleObject, MapProperty<Map<TKey, T>> propertyLambdas, int minElement, int maxElement, boolean nullCountAsElement, RuleSeverityType severityType) {
        return Guard.nbrOfElements(new ArrayList<>(propertyLambdas.get().values()), minElement, maxElement, nullCountAsElement) || RuleGuard.raiseViolation(ruleObject, propertyLambdas, new ArrayList<>(Arrays.asList(propertyLambdas.get().values() == null ? "0" : Integer.toString(propertyLambdas.get().values().size()), Integer.toString(minElement), Integer.toString(maxElement))), RuleType.NbrOfElementsInList.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean notInList(RuleObject ruleObject, Property<T> propertyLambda01, List<T> list) {
        return notInList(ruleObject, propertyLambda01, list, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param list
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean notInList(RuleObject ruleObject, Property<T> propertyLambda01, List<T> list, RuleSeverityType severityType) {
        if (!Guard.contains(propertyLambda01.get(), list)) {
            return true;
        }
        List<String> values = new ArrayList<>();
        values.add(propertyLambda01.get() == null ? "0" : propertyLambda01.get().toString());
        list.forEach(x -> values.add(x.toString()));
        raiseViolation(ruleObject, propertyLambda01, values, RuleType.NotInList.typeValue, severityType);
        return false;
    }

    //endregion

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T extends String> boolean email(RuleObject ruleObject, Property<T> propertyLambda) {
        return email(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String> boolean email(RuleObject ruleObject, Property<T> propertyLambda, RuleSeverityType severityType) {
        return (Guard.email(propertyLambda.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.EMail.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T extends String> boolean phone(RuleObject ruleObject, Property<T> propertyLambda) {
        return phone(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends String> boolean phone(RuleObject ruleObject, Property<T> propertyLambda, RuleSeverityType severityType) {
        return (Guard.phone(propertyLambda.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.PhoneNbr.typeValue, severityType);
    }

    //region RaiseViolation

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T> boolean raiseUniqueViolation(RuleObject ruleObject, Property<T> propertyLambda) {
        return raiseUniqueViolation(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseUniqueViolation(RuleObject ruleObject, Property<T> propertyLambda, RuleSeverityType severityType) {
        return RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.Unique.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param <T>
     * @return
     */
    public static <T> boolean raiseImmutableViolation(RuleObject ruleObject, Property<T> propertyLambda) {
        return raiseImmutableViolation(ruleObject, propertyLambda, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseImmutableViolation(RuleObject ruleObject, Property<T> propertyLambda, RuleSeverityType severityType) {
        return RuleGuard.raiseViolation(ruleObject, propertyLambda, RuleType.Immutable.typeValue, severityType);
    }


    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, int ruleId, RuleSeverityType severityType) {
        List<String> values = new ArrayList<>();

        if (propertyLambda.get() != null) values.add(propertyLambda.get().toString());

        return raiseViolation(ruleObject, propertyLambda, values, ruleId, severityType);
    }

    /**
     * @param ruleObject     L'objet sur lequel la validation est effectué
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
     * @param ruleObject       L'objet sur lequel la validation est effectué
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
     * @param ruleObject     L'objet sur lequel la validation est effectué
     * @param propertyLambda La propriété sur laquelle la validation est effectuée
     * @param values
     * @param ruleId
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T> boolean raiseViolation(RuleObject ruleObject, Property<T> propertyLambda, List<String> values, int ruleId, RuleSeverityType severityType) {
        return raiseViolation(ruleObject, Collections.singletonList(propertyLambda), values, ruleId, severityType);
    }

    /**
     * @param ruleObject       L'objet sur lequel la validation est effectué
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


}
