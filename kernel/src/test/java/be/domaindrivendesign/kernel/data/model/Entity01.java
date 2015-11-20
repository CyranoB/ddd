package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;

import java.util.UUID;

/**
 * Created by eddie on 20/11/15.
 */
public class Entity01 extends Entity {

    protected Entity01(UUID id) {
        super(id);
    }

    public static Entity01 create(UUID guid) {
        return new Entity01(guid);
    }
}
