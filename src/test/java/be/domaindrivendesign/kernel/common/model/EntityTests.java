package be.domaindrivendesign.kernel.common.model;

import be.domaindrivendesign.kernel.common.error.KernelException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by eddie on 18/11/15.
 */
public class EntityTests {

    @SuppressWarnings({"EqualsWithItself", "ObjectEqualsNull"})
    @Test
    public void testEquals() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());
        Entity entity02 = EntityGuid01.create(UUID.randomUUID());
        Entity entity03 = EntityGuid02.create(entity01.getId());
        Object object01 = new Object();

        // equal to itself
        assertTrue(entity01.equals(entity01));

        // 2 entities with different ID
        assertFalse(entity01.equals(entity02));

        // 2 entities with same ID => Must be equal cause ID is unique
        assertTrue(entity01.equals(entity03));

        // entity vs non entity always non equal
        assertFalse(entity01.equals(object01));

        // entity vs null always non equal
        assertFalse(entity01.equals(null));
    }

    @Test
    public void testDefaultState() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());

        // default state must be equal to Added
        assertTrue(entity01.getState() == EntityStateType.Added);
    }

    @Test(expected = KernelException.class)
    public void testSetStateToAdded() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());
        // set state to added is only allowed by constructor
        entity01.setState(EntityStateType.Added);
    }

    @Test(expected = KernelException.class)
    public void testSetStateToDeleted() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());

        // set state to deleted when = to added
        entity01.setState(EntityStateType.Deleted);
    }

    @Test(expected = KernelException.class)
    public void testSetStateToUnchanged() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());

        // set state to deleted when = to added
        entity01.setState(EntityStateType.Unchanged);
    }

    @Test
    public void testSetStateToModified() {
        Entity entity01 = EntityGuid01.create(UUID.randomUUID());

        // set to modified when state = to added does not modify the state
        entity01.setState(EntityStateType.Modified);

        assertTrue(entity01.getState() == EntityStateType.Added);
    }


    public static class EntityGuid01 extends Entity {
        EntityGuid01(UUID id) {
            super(id);
        }

        public static EntityGuid01 create(UUID guid) {
            EntityGuid01 e = new EntityGuid01(guid);
            e.setState(EntityStateType.Added);
            return e;
        }
    }

    public static class EntityGuid02 extends Entity {
        EntityGuid02(UUID id) {
            super(id);
        }

        public static EntityGuid02 create(UUID guid) {
            EntityGuid02 e = new EntityGuid02(guid);
            e.setState(EntityStateType.Added);
            return e;
        }
    }
}