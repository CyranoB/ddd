package be.domaindrivendesign.kernel.common.valueobject;

import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

/**
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
        return this == o || !(o == null || getClass() != o.getClass()) && EqualsBuilder.reflectionEquals(this, o, false);
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
