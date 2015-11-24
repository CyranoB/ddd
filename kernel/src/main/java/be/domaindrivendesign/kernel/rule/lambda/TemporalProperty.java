package be.domaindrivendesign.kernel.rule.lambda;

/**
 * Created by asmolabs on 24/11/15.
 */

import java.time.temporal.Temporal;

/**
 * @param <T>
 */
public interface TemporalProperty<T extends Temporal & Comparable<T>> extends Property<T> {
}
