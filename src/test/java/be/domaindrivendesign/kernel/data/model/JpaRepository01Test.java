package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by eddie on 03/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {JpaRepositoryConfiguration.class})
public class JpaRepository01Test {

    @Autowired
    private JpaRepository01 jpaRepository01;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void insertTest() {
        Entity01 entity01 = Entity01.create(UUID.randomUUID());
        entity01.setStringAttribute("Tutu");
        jpaRepository01.insert(entity01);
        unitOfWork.commit();

        List<Entity01> entity01s = jpaRepository01.list();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void updateTest() {
        Entity01 entity01 = Entity01.create(UUID.randomUUID());

        entity01.setStringAttribute("Tutu");

        jpaRepository01.insert(entity01);

        unitOfWork.commit();

        entity01 = jpaRepository01.list().get(0);

        entity01.setStringAttribute("Toto");

        jpaRepository01.update(entity01);

        unitOfWork.commit();

        List<Entity01> entity01s = jpaRepository01.list();

        assertTrue(entity01s.size() > 0);
        assertEquals(EntityStateType.Unchanged, entity01s.get(0).getState());
    }

    @Test
    public void testEmpty() {
        List<Entity01> emptyList = jpaRepository01.list();
        assertEquals(0, emptyList.size());
    }

}
