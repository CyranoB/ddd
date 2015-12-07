package be.domaindrivendesign.ecole.etablissement.jpa;

import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.model.Adresse01;
import be.domaindrivendesign.ecole.etablissement.model.Contact01;
import be.domaindrivendesign.ecole.etablissement.model.Etablissement;
import be.domaindrivendesign.ecole.etablissement.model.Implantation;
import be.domaindrivendesign.ecole.etablissement.type.EcoleType;
import be.domaindrivendesign.ecole.etablissement.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.etablissement.type.NiveauType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
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
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@EnableJpaRepositories
public class JpaEtablissementRepositoryTest {

    @Autowired
    private JpaEtablissementRepository jpaRepository;
    @Autowired
    private UnitOfWork unitOfWork;

    @Test
    public void insertTest() {
        ArrayList<Implantation> implantations = new ArrayList<>();
        Implantation implantation01A = Implantation.creer("reference a", "01 a", new Adresse01(), Collections.singletonList(NiveauType.Maternelle), new Contact01(), PeriodDateHeure.EMPTY);
        implantations.add(implantation01A);
        Etablissement etablissement = Etablissement.creer("1", "Test", EnseignementReseauType.LibreSubventionneCf, new Adresse01(), EcoleType.EtablissementScolaire, new Contact01(), implantations);
        jpaRepository.insert(etablissement);
        unitOfWork.commit();

        List<Etablissement> entity01s = jpaRepository.findAll();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void testEmpty() {
        List<Etablissement> emptyList = jpaRepository.findAll();
        assertEquals(0, emptyList.size());
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public UnitOfWork unitOfWork() {
            UnitOfWorkJpa unitOfWorkJpa = new UnitOfWorkJpa();
            return unitOfWorkJpa;
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
            ds.setUrl("jdbc:hsqldb:file:testdb");
            ds.setUsername("sa");
            ds.setPassword("");

            return ds;

        }

        @Bean
        public JpaEtablissementRepository jpaRepository01() {
            return new JpaEtablissementRepository();
        }
    }
}
