package be.domaindrivendesign.kernel.data.model;

import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
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
@EnableTransactionManagement
@Transactional(readOnly = true)
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

    @Configuration
    static class ContextConfiguration {

        @Bean
        @Autowired
        public UnitOfWork unitOfWork() {
            return new UnitOfWorkJpa();
        }

        @Bean
        public DataSource dataSource() {

            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.HSQL).build();
        }

        @Bean
        @Autowired
        public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {

            LocalContainerEntityManagerFactoryBean lcemfb
                    = new LocalContainerEntityManagerFactoryBean();

            lcemfb.setDataSource(dataSource);
            lcemfb.setPackagesToScan("be.domaindrivendesign");
            lcemfb.setPersistenceUnitName("MyTestPU");

            HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
            lcemfb.setJpaVendorAdapter(va);

            Properties ps = new Properties();
            ps.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
            ps.put("hibernate.hbm2ddl.auto", "create");
            ps.put("hsqldb.sqllog", "3");
            ps.put("hsqldb.applog", "1");
            lcemfb.setJpaProperties(ps);

            lcemfb.afterPropertiesSet();

            return lcemfb;
        }

        @Bean
        @Autowired
        public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
            jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
            jpaTransactionManager.setDataSource(dataSource());
            return jpaTransactionManager;
        }



        @Bean
        @Autowired
        public JpaRepository01 jpaRepository01(UnitOfWork unitOfWork) {
            return new JpaRepository01();
        }
    }
}
