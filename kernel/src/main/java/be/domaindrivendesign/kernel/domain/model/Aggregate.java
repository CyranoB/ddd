package be.domaindrivendesign.kernel.domain.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.common.model.EntityStateType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by eddie on 19/11/15.
 */
public class Aggregate extends Entity {
    protected Aggregate() {
        super();
    }

    protected Aggregate(UUID id, EntityStateType state) {
        super(id, state);
    }

    protected Aggregate(UUID id) {
        super(id);
    }

    protected void supprimerLogiquement(LocalDateTime date) {
        setLogicalDeleteOn(date == null ? LocalDateTime.now() : date);
        setState(EntityStateType.Modified);
    }

    protected void supprimerLogiquement() {
        supprimerLogiquement(null);
    }
}
