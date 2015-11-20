package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.error.KernelException;
import be.domaindrivendesign.kernel.common.model.Entity;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RepositoryTest {

    @Test
    public void TestDeferredInsert() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        Entity entity01 = Entity01.create(UUID.randomUUID());
        repository.insert(entity01);

        // insert is recorded (on the appropriate repository)
        assertEquals(entity01, unitOfWork.getAddedEntities().keySet().toArray()[0]);
        assertEquals(repository, unitOfWork.getAddedEntities().get(entity01));

        // insert is executed (on the appropriate repository)
        unitOfWork.commit();
        assertEquals(0, unitOfWork.getAddedEntities().size());
        assertEquals(repository.persistedInsertedItems.get(0), entity01);
    }

    @Test
    public void TestDeferredUpdated() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        Entity01 entity01 = Entity01.create(UUID.randomUUID());
        repository.update(entity01);

        // update is recorded (on the appropriate repository)
        assertEquals(entity01, unitOfWork.getUpdatedEntities().keySet().toArray()[0]);
        assertEquals(repository, unitOfWork.getUpdatedEntities().get(entity01));

        // update is executed (on the appropriate repository)
        unitOfWork.commit();
        assertEquals(0, unitOfWork.getAddedEntities().size());
        assertEquals(repository.persistedUpdatedItems.get(0), entity01);
    }

    @Test
    public void TestDeferredDeleted() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        Entity01 entity01 = Entity01.create(UUID.randomUUID());
        repository.delete(entity01);

        // delete is recorded (on the appropriate repository)
        assertEquals(entity01, unitOfWork.getDeletedEntities().keySet().toArray()[0]);
        assertEquals(repository, unitOfWork.getDeletedEntities().get(entity01));

        // delete is executed (on the appropriate repository)
        unitOfWork.commit();
        assertEquals(0, unitOfWork.getAddedEntities().size());
        assertEquals(repository.persistedDeletedItems.get(0), entity01);
    }

    @Test(expected = KernelException.class)
    public void TestInsertNull() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        // null insert is not allowed
        repository.insert(null);
    }

    @Test(expected = KernelException.class)
    public void TestUpdateNull() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        // null update is not allowed
        repository.update(null);
    }

    @Test(expected = KernelException.class)
    public void TestDeleteNull() {
        UnitOfWork01 unitOfWork = new UnitOfWork01();
        Repository01 repository = new Repository01(unitOfWork);
        // null deleted is not allowed
        repository.delete(null);
    }
}
