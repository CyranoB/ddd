package be.domaindrivendesign.kernel.common.valueobject;

/**
 * Created by eddie on 17/11/15.
 */
public interface ValueObject<T> {
    boolean isSameValueObjectAs(T otherObject);
}
