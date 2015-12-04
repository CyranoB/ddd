package be.domaindrivendesign.ecole.etablissement.data;

import be.domaindrivendesign.ecole.SpringConfiguration4Test;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringConfiguration4Test.class)
@EnableJpaRepositories
public class JpaImplantationAnneeScolaireTest {

    @Autowired
    private ImplantationAnneeScolaireRepository repository;
    @Autowired
    private EtablissementRepository etablissementRepository;

    @Before
    public void init() throws Throwable {
        assertNotNull(this.repository);
    }

    @Test
    public void testfindAll() throws Throwable {
        repository.getByImplantationNumeroReference("test");
    }

}
