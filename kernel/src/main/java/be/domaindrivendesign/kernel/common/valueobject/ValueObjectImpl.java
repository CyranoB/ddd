package be.domaindrivendesign.kernel.common.valueobject;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by eddie on 17/11/15.
 */
public class ValueObjectImpl<T> implements ValueObject<T>, RuleObject {
    private transient int cachedHashCode = 0;

    @Override
    public final boolean isSameValueObjectAs(final T otherValueObject) {
        return otherValueObject != null && EqualsBuilder.reflectionEquals(this, otherValueObject, false);
    }

    @Override
    public final int hashCode() {
        int h = cachedHashCode;
        if (h == 0) {
            h = HashCodeBuilder.reflectionHashCode(this, false);
            cachedHashCode = h;
        }
        return h;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return isSameValueObjectAs((T) o);
    }
}
