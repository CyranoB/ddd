package be.domaindrivendesign.kernel.common.model;

/**
 * Created by eddie on 13/11/15.
 */
public enum EntityStateType {
    // No change to the entity
    Unchanged(2),
    // The entity has been added (and in most case need to be created in the infrastructure layer)
    Added(4),
    // The entity has been deleted (and in most case need to be deleted in the infrastructure layer, or flag as deleted)
    Deleted(8),
    // The entity has been updated (and in most case need to be updated in the infrastructure layer).
    Modified(16);

    private final int value;

    EntityStateType(int value) {
        this.value = value;
    }
}
