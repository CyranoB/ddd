package be.domaindrivendesign.kernel.domain.control;

import be.domaindrivendesign.kernel.common.context.StandAloneContext;
import be.domaindrivendesign.kernel.domain.interfaces.DomainEventListener;
import be.domaindrivendesign.kernel.domain.interfaces.DomainEventManager;
import org.springframework.context.support.StaticApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by eddie on 23/11/15.
 */

public class DefaultDomainEventManager implements DomainEventManager {

    private static DefaultDomainEventManager domainEventManager = new DefaultDomainEventManager();
    protected final Map<Class<?>, Set<DomainEventListener<?>>> registrations = new HashMap<>(); // synchronized set
    private final StaticApplicationContext staticApplicationContext;

    private DefaultDomainEventManager() {

        staticApplicationContext = new StaticApplicationContext();

        staticApplicationContext.registerSingleton("DomainEventManager", StandAloneContext.class);

    }

    public static DefaultDomainEventManager getInstance() {
        return domainEventManager;
    }

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
     * Raises the event's class' event on the given context.  This event object is passed (if configured) to the
     * registered observer's method.
     *
     * @param event observed
     */
    @Override
    public void fire(Object event) {

        final Set<DomainEventListener<?>> observers = registrations.get(event.getClass());
        if (observers == null) return;

        for (DomainEventListener observer : observers)
            //noinspection unchecked
            observer.onEvent(event);

    }

    /**
     * Destroys event manager. Clear listeners.
     */
    @Override
    public void destroy() {
        for (Entry<Class<?>, Set<DomainEventListener<?>>> e : registrations.entrySet())
            e.getValue().clear();
        registrations.clear();
    }
}
