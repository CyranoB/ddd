package be.domaindrivendesign.kernel.domain.interfaces;

/**
 * Created by eddie on 23/11/15.
 */
public interface DomainEventManager {
    <T> void registerObserver(Class<T> event, DomainEventListener<?> listener);

    <T> void unregisterObserver(Class<T> event, DomainEventListener<T> listener);

    void fire(Object event);

    void destroy();
}
