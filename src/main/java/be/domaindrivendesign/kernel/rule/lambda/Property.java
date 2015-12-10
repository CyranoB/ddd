package be.domaindrivendesign.kernel.rule.lambda;

/**
 * Created by asmolabs on 24/11/15.
 */

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @param <T>
 */
public interface Property<T> extends Supplier<T>, Serializable {
}

