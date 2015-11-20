package be.domaindrivendesign.kernel.application.interfaces;

import be.domaindrivendesign.kernel.domain.interfaces.DomainEvent;

/**
 * Created by eddie on 20/11/15.
 */
public interface ApplicationEvent extends DomainEvent {
    String getEnventType();
}