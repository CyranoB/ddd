package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.SpringConfiguration4Test;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import org.junit.Assert;
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
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by eddie on 03/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@EnableJpaRepositories
public class JpaRepository01Test {

    @Configuration
    static class ContextConfiguration {

        // this bean will be injected into the OrderServiceTest class
        @Bean
        public UnitOfWork unitOfWork() {
            UnitOfWorkJpa unitOfWorkJpa = new UnitOfWorkJpa();
            // set properties, etc.
            return unitOfWorkJpa;
        }

        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {

            LocalContainerEntityManagerFactoryBean lcemfb
                    = new LocalContainerEntityManagerFactoryBean();

            lcemfb.setDataSource(this.dataSource());
            lcemfb.setPackagesToScan(new String[]{"be.domaindrivendesign"});
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
        public JpaRepository01 jpaRepository01() {
            return new JpaRepository01();
        }
    }

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

        List<Entity01>entity01s = jpaRepository01.findAll();
        assertTrue(entity01s.size() > 0);
    }

    @Test
    public void udapteTest() {
        Entity01 entity01 = Entity01.create(UUID.randomUUID());

        entity01.setStringAttribute("Tutu");

        jpaRepository01.insert(entity01);

        unitOfWork.commit();

        entity01 = jpaRepository01.findAll().get(0);

        entity01.setStringAttribute("Toto");

        jpaRepository01.update(entity01);

        unitOfWork.commit();

        List<Entity01>entity01s = jpaRepository01.findAll();

        assertTrue(entity01s.size() > 0);
        assertEquals(EntityStateType.Unchanged, entity01s.get(0).getState());

    }

    @Test
    public void testEmpty() {
        List<Entity01> emptyList = jpaRepository01.findAll();
        assertEquals(0, emptyList.size());
    }
}
