package be.domaindrivendesign.ecole.laitecole.event;

import be.domaindrivendesign.ecole.laitecole.model.DemandeAide;
import be.domaindrivendesign.kernel.domain.control.DomainEventImpl;

public class DemandeAideValidee extends DomainEventImpl<DemandeAideValidee> {

    private DemandeAide demandeAide;

    public DemandeAideValidee(DemandeAide demandeAide) {
        super();
        this.demandeAide = demandeAide;
    }
}
