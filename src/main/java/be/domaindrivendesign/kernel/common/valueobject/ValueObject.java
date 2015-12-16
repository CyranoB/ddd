package be.domaindrivendesign.kernel.common.valueobject;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
 * An object that contains attributes but has no conceptual identity. They should be treated as immutable.
 *
 * Created by eddie on 17/11/15.
 */
public class ValueObject implements RuleObject {
    private transient int cachedHashCode = 0;

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
        if (o == null)
            return false;
        return EqualsBuilder.reflectionEquals(this, o, false);
    }

    public final boolean areEntirePropertyNull() throws IllegalAccessException {
        final String clazz = this.getClass().getName();
        for (Field f : this.getClass().getDeclaredFields()) {
            if (!f.isSynthetic() && !clazz.equals(f.getName()) && FieldUtils.readField(f, this, true) != null) {
                return false;
            }
        }
        return true;
    }
}
