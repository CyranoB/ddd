package be.domaindrivendesign.ecole.module.beneficiaire.domain.event;

import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.kernel.domain.control.DomainEventImpl;


public class DemandeAgrementValidee extends DomainEventImpl<DemandeAgrementValidee> {

    protected Agrement demandeAgrement;

    public DemandeAgrementValidee(Agrement demandeAgrement) {
        this.demandeAgrement = demandeAgrement;
    }

    public Agrement getDemandeAgrement() {
        return demandeAgrement;
    }
}
