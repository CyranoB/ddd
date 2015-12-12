package be.domaindrivendesign.kernel.domain.interfaces;

public interface DomainEventListener<T> {
    void onEvent(T event);
}

