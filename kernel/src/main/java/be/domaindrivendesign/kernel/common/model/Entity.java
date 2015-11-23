package be.domaindrivendesign.kernel.common.model;

import be.domaindrivendesign.kernel.common.error.KernelException;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An object that is not defined by its attributes, but rather by a thread of continuity and its identity.
 *
 * Created by eddie on 18/11/15.
 */
public class Entity {
    protected UUID id;
    protected EntityStateType state;
    protected LocalDateTime logicalDeleteOn;
    protected LocalDateTime lastUpdateOn;

    //region Constructors
    protected Entity(UUID id, EntityStateType state) {
        this.id = id;
        this.state = state;
    }

    protected Entity(UUID id) {
        this(id, EntityStateType.Added);
    }

    protected Entity() {
        this.state = EntityStateType.Unchanged;
    }

    //endregion

    // Utiliser cette méthode que dans des cas bien spécifique.
//    public void forceState(EntityStateType state) {
//        this.state = state;
//    }

    // Equality for non complex entity
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == null) {
            return false;
        }

        if (this == otherObject) {
            return true;
        }
        return otherObject instanceof Entity && id.equals(((Entity) otherObject).getId());
    }

    @Override
    public final int hashCode() {
        return id.hashCode();
    }

    //region Getters and Setters
    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }

    public EntityStateType getState() {
        return state;
    }

    protected void setState(EntityStateType state) {
        if (state == EntityStateType.Added) {
            throw new KernelException("State can only be set to added by the factory methods.");
        } else {
            if ((state == EntityStateType.Deleted) && (this.state == EntityStateType.Added)) {
                throw new KernelException("The entity was just added, set the state to delete is a non-sense.");
            } else //noinspection StatementWithEmptyBody
                if ((state == EntityStateType.Modified) && (this.state == EntityStateType.Added)) {
                // don't change the state because the entity is not yet added.
            } else {
                if (state == EntityStateType.Unchanged) {
                    throw new KernelException("State can only be set to unchanged by the infrastructure layer.");
                } else {
                    this.state = state;
                }
            }
        }
    }

    public LocalDateTime getLogicalDeleteOn() {
        return logicalDeleteOn;
    }

    protected void setLogicalDeleteOn(LocalDateTime logicalDeleteOn) {
        this.logicalDeleteOn = logicalDeleteOn;
    }

    public LocalDateTime getLastUpdateOn() {
        return lastUpdateOn;
    }

    protected void setLastUpdateOn(LocalDateTime lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;
    }
    //endregion

}
