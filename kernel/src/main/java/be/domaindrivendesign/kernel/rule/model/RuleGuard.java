package be.domaindrivendesign.kernel.rule.model;

import be.domaindrivendesign.kernel.common.model.Guard;
import be.domaindrivendesign.kernel.rule.error.RuleException;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.interfaces.RuleViolation;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import com.trigersoft.jaque.expression.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        return (Guard.equals((T) propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.EqualsNumberInvariant.typeValue, severityType);
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
        return (Guard.smallerThan((T) propertyLambda.get(), invariant)) || raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerThanInvariant.typeValue, severityType);
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
        return (Guard.betweenNumber((T) propertyLambda01.get(), minimum, maximum)) || raiseViolation(ruleObject, propertyLambda01, list, RuleType.Between.typeValue, severityType);
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
        return (Guard.smallerOrEqualThan((T) propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.SmallerOrEqualThanInvariant.typeValue, severityType);
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
        return (Guard.greaterThan((T) propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterThanInvariant.typeValue, severityType);

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
        return (Guard.greaterOrEqualThan((T) propertyLambda.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda, invariant.toString(), RuleType.GreaterOrEqualThanInvariant.typeValue, severityType);
    }


    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return equals(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }


    public static <T extends Number & Comparable<T>> boolean equals(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.equals((T) propertyLambda01.get(), (T) propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.EqualsNumber.typeValue, severityType);
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
        return (Guard.smallerThan((T) propertyLambda01.get(), (T) propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.SmallerThan.typeValue
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
     * @param severityType
     * @param <T>
     * @return
     */
    public static <T extends Number & Comparable<T>> boolean greaterThan(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {
        return (Guard.greaterThan((T) propertyLambda01.get(), (T) propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterThan.typeValue
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
        return (Guard.smallerOrEqualThan((T) propertyLambda01.get(), (T) propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.SmallerOrEqualThan.typeValue, severityType);
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
        return (Guard.greaterOrEqualThan((T) propertyLambda01.get(), (T) propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.GreaterOrEqualThan.typeValue, severityType);
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
        return (Guard.isFormat((T) propertyLambda01.get(), digitBeforeComma, decimalPlaces, isNullAllowed)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, Arrays.asList((T) propertyLambda01.get() == null ? null : propertyLambda01.get().toString(), digitBeforeComma.toString(), decimalPlaces.toString()), RuleType.IsFormatDecimal.typeValue, severityType);
    }

    //endregion

    //region LocalDateTime && Date

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @return
     */
    public static <T extends Date> boolean before(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02) {
        return before(ruleObject, propertyLambda01, propertyLambda02, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param propertyLambda02
     * @param severityType
     * @return
     */
    public static <T extends Date> boolean before(RuleObject ruleObject, Property<T> propertyLambda01, Property<T> propertyLambda02, RuleSeverityType severityType) {

        return (Guard.before(propertyLambda01.get(), propertyLambda02.get())) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, propertyLambda02, RuleType.Before.typeValue, severityType);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @return
     */
    public static boolean beforeInvariant(RuleObject ruleObject, Property<LocalDateTime> propertyLambda01, LocalDateTime invariant) {
        return beforeInvariant(ruleObject, propertyLambda01, invariant, RuleSeverityType.Error);
    }

    /**
     * @param ruleObject
     * @param propertyLambda01
     * @param invariant
     * @param severityType
     * @return
     */
    public static boolean beforeInvariant(RuleObject ruleObject, Property<LocalDateTime> propertyLambda01, LocalDateTime invariant, RuleSeverityType severityType) {
        return (Guard.before(propertyLambda01.get(), invariant)) || RuleGuard.raiseViolation(ruleObject, propertyLambda01, invariant.toString(), RuleType.BeforeInvariant.typeValue, severityType);
    }

    /*/// <summary>
    /// Vérifie qu'un <see cref="Nullable{DateTime}"/> est plus récent (après) qu'un autre et lève une violation de type <see cref="RuleGuardRuleId.After"/> si ce n'est pas le cas.
    /// </summary>
    /// <typeparam name="T">Le type des propriétés pour lesquelles la validation est effectuée.</typeparam>
    /// <param name="ruleObject">Le <see cref="IRuleObject"/> pour lequel la validation est effectuée.</param>
    /// <param name="propertyLambda01">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value01"/>.</param>
    /// <param name="value01">La première date.</param>
    /// <param name="propertyLambda02">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value02"/>.</param>
    /// <param name="value02">La seconde date.</param>
    /// <param name="severityType">La sévérité de l'erreur.</param>
    /// <returns><c>True</c> si <paramref name="value01"/> est "après" <paramref name="value02"/>, sinon <c>False</c></returns>
    /// <exception cref="RuleException">Lancée si la validation échoue et que le niveau de sévérité spécifié est <see cref="RuleSeverityType.BlockingError"/>.</exception>
    public static bool After<T>(IRuleObject ruleObject, Expression<Func<T>> propertyLambda01, DateTime? value01, Expression<Func<T>> propertyLambda02, DateTime? value02, RuleSeverityType severityType = RuleSeverityType.Error)
    {
        return (Guard.After(value01, value02)) || RuleGuard.RaiseViolation(ruleObject, propertyLambda01, value01, propertyLambda02, value02, (int)RuleGuardRuleId.After, severityType);
    }

    /// <summary>
    /// Vérifie qu'un <see cref="Nullable{DateTime}"/> est plus vieux (avant) qu'un autre et lève une violation de type <see cref="RuleGuardRuleId.BeforeOrEqual"/> si ce n'est pas le cas.
    /// </summary>
    /// <typeparam name="T">Le type des propriétés pour lesquelles la validation est effectuée.</typeparam>
    /// <param name="ruleObject">Le <see cref="IRuleObject"/> pour lequel la validation est effectuée.</param>
    /// <param name="propertyLambda01">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value01"/>.</param>
    /// <param name="value01">La première date.</param>
    /// <param name="propertyLambda02">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value02"/>.</param>
    /// <param name="value02">La seconde date.</param>
    /// <param name="severityType">La sévérité de l'erreur.</param>
    /// <returns><c>True</c> si <paramref name="value01"/> est identique ou "avant" <paramref name="value02"/>, sinon <c>False</c></returns>
    /// <exception cref="RuleException">Lancée si la validation échoue et que le niveau de sévérité spécifié est <see cref="RuleSeverityType.BlockingError"/>.</exception>
    public static bool BeforeOrEqual<T>(IRuleObject ruleObject, Expression<Func<T>> propertyLambda01, DateTime? value01, Expression<Func<T>> propertyLambda02, DateTime? value02, RuleSeverityType severityType = RuleSeverityType.Error)
    {
        return (Guard.BeforeOrEqual(value01, value02)) || RuleGuard.RaiseViolation(ruleObject, propertyLambda01, value01, propertyLambda02, value02, (int)RuleGuardRuleId.BeforeOrEqual, severityType);
    }

    /// <summary>
    /// Vérifie qu'un <see cref="Nullable{DateTime}"/> est plus récent (après) qu'un autre et lève une violation de type <see cref="RuleGuardRuleId.AfterOrEqual"/> si ce n'est pas le cas.
    /// </summary>
    /// <typeparam name="T">Le type des propriétés pour lesquelles la validation est effectuée.</typeparam>
    /// <param name="ruleObject">Le <see cref="IRuleObject"/> pour lequel la validation est effectuée.</param>
    /// <param name="propertyLambda01">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value01"/>.</param>
    /// <param name="value01">La première date.</param>
    /// <param name="propertyLambda02">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value02"/>.</param>
    /// <param name="value02">La seconde date.</param>
    /// <param name="severityType">La sévérité de l'erreur.</param>
    /// <returns><c>True</c> si <paramref name="value01"/> est identique ou "après" <paramref name="value02"/>, sinon <c>False</c></returns>
    /// <exception cref="RuleException">Lancée si la validation échoue et que le niveau de sévérité spécifié est <see cref="RuleSeverityType.BlockingError"/>.</exception>
    public static bool AfterOrEqual<T>(IRuleObject ruleObject, Expression<Func<T>> propertyLambda01, DateTime? value01, Expression<Func<T>> propertyLambda02, DateTime? value02, RuleSeverityType severityType = RuleSeverityType.Error)
    {
        return (Guard.AfterOrEqual(value01, value02)) || RuleGuard.RaiseViolation(ruleObject, propertyLambda01, value01, propertyLambda02, value02, (int)RuleGuardRuleId.AfterOrEqual, severityType);
    }

    /// <summary>
    /// Vérifie que deux <see cref="Nullable{DateTime}"/> représentent le même moment (sont égaux) et lève une violation de type <see cref="RuleGuardRuleId.EqualsDateTime"/> si ce n'est pas le cas.
    /// </summary>
    /// <typeparam name="T">Le type des propriétés pour lesquelles la validation est effectuée.</typeparam>
    /// <param name="ruleObject">Le <see cref="IRuleObject"/> pour lequel la validation est effectuée.</param>
    /// <param name="propertyLambda01">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value01"/>.</param>
    /// <param name="value01">La première date.</param>
    /// <param name="propertyLambda02">La propriété de <paramref name="ruleObject"/> correspondant à <paramref name="value02"/>.</param>
    /// <param name="value02">La seconde date.</param>
    /// <param name="severityType">La sévérité de l'erreur.</param>
    /// <returns><c>True</c> si <paramref name="value01"/> est identique à <paramref name="value02"/>, sinon <c>False</c></returns>
    /// <exception cref="RuleException">Lancée si la validation échoue et que le niveau de sévérité spécifié est <see cref="RuleSeverityType.BlockingError"/>.</exception>
    public static bool Equals<T>(IRuleObject ruleObject, Expression<Func<T>> propertyLambda01, DateTime? value01, Expression<Func<T>> propertyLambda02, DateTime? value02, RuleSeverityType severityType = RuleSeverityType.Error)
    {
        return (Guard.Equals(value01, value02)) || RuleGuard.RaiseViolation(ruleObject, propertyLambda01, value01, propertyLambda02, value02, (int)RuleGuardRuleId.EqualsDateTime, severityType);
    }*/

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
