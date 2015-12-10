package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eddie on 19/11/15.
 */
public class UnitOfWorkTest {
    @Test
    public void testEquals() {
        UnitOfWork unitOfWork01 = new UnitOfWorkImpl();
        UnitOfWork unitOfWork02 = new UnitOfWorkImpl();

        // Different unit of work must
        assertEquals(unitOfWork01, unitOfWork01);
        assertNotSame(unitOfWork01, unitOfWork02);
    }

    @Test
    public void testUniqueId() {
        UnitOfWork unitOfWork01 = new UnitOfWorkImpl();
        UnitOfWork unitOfWork02 = new UnitOfWorkImpl();

        // ID must be generated automatically
        assertNotNull(unitOfWork01.getId());

        // Different unit of work must have different id
        assertNotSame(unitOfWork01.getId(), unitOfWork02.getId());
    }
}
