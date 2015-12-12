package be.domaindrivendesign.ecole.laitecole.domain.event;

import be.domaindrivendesign.ecole.laitecole.domain.model.DemandeAide;
import be.domaindrivendesign.kernel.domain.control.DomainEventImpl;

public class DemandeAideValidee extends DomainEventImpl<DemandeAideValidee> {

    private DemandeAide demandeAide;

    public DemandeAideValidee(DemandeAide demandeAide) {
        super();
        this.demandeAide = demandeAide;
    }

    public DemandeAide getDemandeAide() {
        return demandeAide;
    }
}
