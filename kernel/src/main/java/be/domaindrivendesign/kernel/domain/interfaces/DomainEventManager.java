package be.domaindrivendesign.kernel.domain.interfaces;

import java.lang.reflect.Method;

/**
 * Created by eddie on 23/11/15.
 */
public interface DomainEventManager {
    <T> void registerObserver(Class<T> event, DomainEventListener<?> listener);

    <T> void registerObserver(Object instance, Method method, Class<T> event);

    <T> void unregisterObserver(Class<T> event, DomainEventListener<T> listener);

    <T> void unregisterObserver(Object instance, Class<T> event);

    @SuppressWarnings({"rawtypes", "unchecked"})
    void fire(Object event);

    void destroy();
}
