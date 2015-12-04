package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.Entity;
import be.domaindrivendesign.kernel.common.model.EntityStateType;

import java.util.UUID;

/**
 * Created by eddie on 20/11/15.
 */
@javax.persistence.Entity
public class Entity01 extends Entity {

    protected String stringAttribute;

    protected Entity01() {
        super();
    }

    protected Entity01(UUID id) {
        super(id);
    }

    public static Entity01 create(UUID id) {
        Entity01 entity01 = new Entity01(id);
        entity01.setState(EntityStateType.Added);
        return entity01;
    }

    public String getStringAttribute() {
        return stringAttribute;
    }

    public void setStringAttribute(String stringAttribute) {
        this.stringAttribute = stringAttribute;
    }
}
