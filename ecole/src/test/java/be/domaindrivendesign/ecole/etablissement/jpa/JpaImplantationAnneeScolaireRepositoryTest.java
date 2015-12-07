package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.model.ClasseComptage;
import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@EnableJpaRepositories
public class JpaImplantationAnneeScolaireRepositoryTest {

    @Autowired
    private JpaImplantationAnneeScolaireRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void insertTest() {

        ClasseComptage classeComptage01 = ClasseComptage.creer(ClasseType.Maternelle, 10);
        ClasseComptage classeComptage02 = ClasseComptage.creer(ClasseType.PremierePrimaire, 14);
        Arrays.asList(classeComptage01, classeComptage02);
        ImplantationAnneeScolaire implantationAnneeScolaire = ImplantationAnneeScolaire.creer("1", new AnneeScolaire(2014, 2015),
                new ArrayList<>(Arrays.asList(classeComptage01, classeComptage02)));

        jpaRepository.insert(implantationAnneeScolaire);
        unitOfWork.commit();

        List<ImplantationAnneeScolaire> entity01s = jpaRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void testEmpty() {
        List<ImplantationAnneeScolaire> emptyList = jpaRepository.findAll();
        assertEquals(0, emptyList.size());
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public UnitOfWork unitOfWork() {
            return new UnitOfWorkJpa();
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

            LocalContainerEntityManagerFactoryBean lcemfb
                    = new LocalContainerEntityManagerFactoryBean();

            lcemfb.setDataSource(this.dataSource());
            lcemfb.setPackagesToScan("be.domaindrivendesign");
            lcemfb.setPersistenceUnitName("MyTestPU");

            HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
            lcemfb.setJpaVendorAdapter(va);

            Properties ps = new Properties();
            ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            ps.put("hibernate.hbm2ddl.auto", "create");
            lcemfb.setJpaProperties(ps);

            lcemfb.afterPropertiesSet();

            return lcemfb;

        }

        @Bean
        public DataSource dataSource() {

            DriverManagerDataSource ds = new DriverManagerDataSource();

            ds.setDriverClassName("org.hsqldb.jdbcDriver");
            ds.setUrl("jdbc:hsqldb:file:testdb;hsqldb.sqllog=3");
            ds.setUsername("sa");
            ds.setPassword("");

            return ds;

        }

        @Bean
        public JpaImplantationAnneeScolaireRepository jpaRepository01() {
            return new JpaImplantationAnneeScolaireRepository();
        }
    }
}
