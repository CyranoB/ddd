package be.domaindrivendesign.kernel.domain.control;

import be.domaindrivendesign.kernel.domain.interfaces.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by eddie on 26/11/15.
 */
public class DomainEventImpl<T> implements DomainEvent<T> {
    // DateTime when the event occured
    public LocalDateTime eventOccurredTime;

    // Unique identifier of the event
    public UUID id;

    // Default constructor
    public DomainEventImpl() {
        this.eventOccurredTime = LocalDateTime.now();
        this.id = UUID.randomUUID();
    }

    // Getter
    public LocalDateTime getEventOccuredTime() {
        return this.eventOccurredTime;
    }
}
