package be.domaindrivendesign.kernel.domain.interfaces;

import java.time.LocalDateTime;

/**
 * Created by eddie on 20/11/15.
 */
public interface DomainEvent<T> {
    LocalDateTime getEventOccuredTime();
}
