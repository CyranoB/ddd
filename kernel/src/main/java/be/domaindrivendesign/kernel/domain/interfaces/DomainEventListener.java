package be.domaindrivendesign.kernel.domain.interfaces;

/**
 * Created by eddie on 23/11/15.
 */
public interface DomainEventListener<T> {
    void onEvent(T event);
}

