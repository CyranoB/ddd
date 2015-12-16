package be.domaindrivendesign.ecole.etablissement.service;

import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaImplantationAnneeScolaireRepository;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpaImpl;
import be.domaindrivendesign.kernel.data.model.JpaRepository01;
import be.domaindrivendesign.kernel.data.model.JpaRepository01Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by asmol on 16-12-15.
 */
@Configuration
@EnableAutoConfiguration

@EntityScan(basePackages = {"be.domaindrivendesign"})
@EnableJpaRepositories(basePackages = {"be.domaindrivendesign"})
@EnableTransactionManagement
public class EcoleDomainServiceConfiguration {
    @Bean
    public UnitOfWorkJpa unitOfWork() {
        return new UnitOfWorkJpaImpl();
    }

    @Bean
    public EcoleDomainService ecoleDomainService(){return new EcoleDomainServiceImpl();}

    @Bean
    public ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository(){return new JpaImplantationAnneeScolaireRepository();}

    @Bean
    public EtablissementRepository etablissementRepository(){return new JpaEtablissementRepository();}

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


}
