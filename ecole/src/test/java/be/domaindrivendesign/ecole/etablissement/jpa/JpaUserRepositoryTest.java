package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void insertTest() {
        User entity01 = User.create("Toto", "test@test.com");
        jpaUserRepository.insert(entity01);
        unitOfWork.commit();

        List<User> entity01s = jpaUserRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }
}
