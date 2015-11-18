package be.domaindrivendesign.kernel.common.entity;

import be.domaindrivendesign.kernel.common.error.KernelException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Created by eddie on 18/11/15.
 */
public class EntityTests {

    @SuppressWarnings({"EqualsWithItself", "ObjectEqualsNull"})
    @Test
    public void testEquals() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());
        Entity entity02 = EntityGuid01.Create(UUID.randomUUID());
        Entity entity03 = EntityGuid02.Create(entity01.getId());
        Object object01 = new Object();

        // equals to itself
        assertTrue(entity01.equals(entity01));

        // 2 entities with different ID
        assertTrue(!entity01.equals(entity02));

        // 2 entities with same ID => Must be equal cause ID is unique
        assertTrue(entity01.equals(entity03));

        // entity vs non entity always non equal
        assertTrue(!entity01.equals(object01));

        // entity vs null always non equal
        assertTrue(!entity01.equals(null));
    }

    @Test
    public void testDefaultState() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());

        // default state must be equal to Added
        assertTrue(entity01.getState() == EntityStateType.Added);
    }

    @Test(expected = KernelException.class)
    public void testSetStateToAdded() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());
        // set state to added is only allowed by constructor
        entity01.setState(EntityStateType.Added);
    }

    @Test(expected = KernelException.class)
    public void TestSetStateToDeleted() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());

        // set state to deleted when = to added
        entity01.setState(EntityStateType.Deleted);
    }

    @Test(expected = KernelException.class)
    public void TestSetStateToUnchanged() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());

        // set state to deleted when = to added
        entity01.setState(EntityStateType.Unchanged);
    }

    @Test
    public void TestSetStateToModified() {
        Entity entity01 = EntityGuid01.Create(UUID.randomUUID());

        // set to modified when state = to added does not modify the state
        entity01.setState(EntityStateType.Modified);

        assertTrue(entity01.getState() == EntityStateType.Added);
    }


    public static class EntityGuid01 extends Entity {
        EntityGuid01(UUID id) {
            super(id);
        }

        public static EntityGuid01 Create(UUID guid) {
            return new EntityGuid01(guid);
        }
    }

    public static class EntityGuid02 extends Entity {
        EntityGuid02(UUID id) {
            super(id);
        }

        public static EntityGuid02 Create(UUID guid) {
            return new EntityGuid02(guid);
        }
    }

}
