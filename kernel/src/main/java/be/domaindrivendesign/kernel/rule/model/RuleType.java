package be.domaindrivendesign.kernel.rule.model;

/**
 * Created by asmolabs on 16/11/15.
 */
public enum RuleType {
    EqualsNumberInvariant(1),
    SmallerThanInvariant(2),
    SmallerOrEqualThanInvariant(3),
    GreaterThanInvariant(4),
    GreaterOrEqualThanInvariant(5),
    EqualsNumber(6),
    SmallerThan(7),
    GreaterThan(8),
    SmallerOrEqualThan(9),
    GreaterOrEqualThan(10),
    Between(11),
    IsFormatDecimal(13),
    DomainNumber(14),

    Before(50),
    After(51),
    BeforeOrEqual(52),
    AfterOrEqual(53),
    EqualsTemporal(54),
    Include(55),
    BeforeInvariant(56),
    AfterInvariant(57),
    BeforeOrEqualInvariant(58),
    AfterOrEqualInvariant(59),
    EqualsTemporalInvariant(60),
    DomainTemporal(61),

    NullOrEmpty(100),
    EqualsString(101),
    OnlyDigit(102),
    Length(103),
    MustBeNull(104),

    Mandatory(150),
    Unique(151),
    NotFound(152),
    Immutable(153),

    // Constraints about the number of elements within a list of a property
    NbrOfElementsInList(200),
    // Constratins about the number of properties in an IRuleObject
    NbrOfElementsAsProperty(201),
    // Overlap of period, or day, ...
    Overlap(202),
    NotInList(203),


    PhoneNbr(500),
    EMail(501);

    public int typeValue;

    RuleType(int typeValue) {
        this.typeValue = typeValue;
    }

}
