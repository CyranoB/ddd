package be.domaindrivendesign.kernel.domain.control;

import be.domaindrivendesign.kernel.domain.interfaces.DomainEventListener;
import be.domaindrivendesign.kernel.domain.interfaces.DomainEventManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by eddie on 23/11/15.
 */


@Service
@Scope("singleton")
public class DomainEventManagerImpl implements DomainEventManager {

    protected final Map<Class<?>, Set<DomainEventListener<?>>> registrations = new HashMap<>(); // synchronized set

    /**
     * Register the given EventListener to the contest and event class.
     *
     * @param event    observed
     * @param listener to be triggered
     * @param <T>      event type
     */
    @Override
    public <T> void registerObserver(Class<T> event, DomainEventListener<?> listener) {
        Set<DomainEventListener<?>> observers = registrations.get(event);
        if (observers == null) {
            observers = new CopyOnWriteArraySet<>();
            registrations.put(event, observers);
        }

        observers.add(listener);

    }

    /**
     * Registers given method with provided context and event.
     *
     * @param instance to be called
     * @param method   to be called
     * @param event    observed
     */
    @Override
    public <T> void registerObserver(Object instance, Method method, Class<T> event) {
        registerObserver(event, new DomainEventObserverMethodListener<T>(instance, method));
    }

    /**
     * Unregisters the provided event listener from the given event
     *
     * @param event    observed
     * @param listener to be unregistered
     * @param <T>      event type
     */
    @Override
    public <T> void unregisterObserver(Class<T> event, DomainEventListener<T> listener) {

        final Set<DomainEventListener<?>> observers = registrations.get(event);
        if (observers == null) return;

        observers.remove(listener);
    }

    /**
     * Unregister all methods observing the given event from the provided context.
     *
     * @param instance to be unregistered
     * @param event    observed
     */
    @Override
    public <T> void unregisterObserver(Object instance, Class<T> event) {

        final Set<DomainEventListener<?>> observers = registrations.get(event);
        if (observers == null) return;

        // As documented in http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Collections.html#synchronizedSet(java.util.Set)
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        DomainEventObserverMethodListener<?> toRemove = null;

        for (final DomainEventListener<?> listener : observers) {
            if (listener instanceof DomainEventObserverMethodListener) {
                final DomainEventObserverMethodListener<?> observer = ((DomainEventObserverMethodListener<?>) listener);
                if (observer.getInstance() == instance) {
                    toRemove = observer;
                    break;
                }
            }
        }
        if (toRemove != null) {
            observers.remove(toRemove);
        }
    }

    /**
     * Raises the event's class' event on the given context.  This event object is passed (if configured) to the
     * registered observer's method.
     *
     * @param event observed
     */
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void fire(Object event) {

        final Set<DomainEventListener<?>> observers = registrations.get(event.getClass());
        if (observers == null) return;

        for (DomainEventListener observer : observers)
            //noinspection unchecked
            observer.onEvent(event);

    }

    protected Set<DomainEventListener<?>> copyObservers(Set<DomainEventListener<?>> observers) {
        // As documented in http://docs.oracle.com/javase/1.4.2/docs/api/java/util/Collections.html#synchronizedSet(java.util.Set)
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (observers) {
            return new LinkedHashSet<>(observers);
        }
    }


    @Override
    public void destroy() {
        for (Entry<Class<?>, Set<DomainEventListener<?>>> e : registrations.entrySet())
            e.getValue().clear();
        registrations.clear();
    }
}
