package be.domaindrivendesign.ecole.etablissement.model;

import be.domaindrivendesign.ecole.etablissement.event.ImplantationFermee;
import be.domaindrivendesign.kernel.domain.control.SimpleDomainEventManager;
import be.domaindrivendesign.kernel.domain.interfaces.DomainEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eddie on 26/11/15.
 */
public class DummyEventRegister implements DomainEventListener<ImplantationFermee> {

    public ArrayList<Implantation> implantations;

    public DummyEventRegister() {
        SimpleDomainEventManager.getInstance().registerObserver(ImplantationFermee.class, this);
        this.implantations = new ArrayList<Implantation>();
    }

    public List<Implantation> getImplantations() {
        return this.implantations;
    }

    @Override
    public void onEvent(ImplantationFermee event) {
        this.implantations.add(event.getImplantation());
    }
}
