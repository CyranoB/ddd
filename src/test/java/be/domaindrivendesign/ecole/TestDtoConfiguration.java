package be.domaindrivendesign.ecole;

import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.data.jpa.EtablissementRepositoryDtoJpa;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.ecole.module.laitecole.data.jpa.EtablissementParticipantRepositoryDtoJpa;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TestDtoConfiguration {

    @Bean
    public UnitOfWorkJpa unitOfWork() {
        return new UnitOfWorkJpaImpl();
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
        ps.put("hsqldb.sqllog", "3");
        ps.put("hsqldb.applog", "1");
        lcemfb.setJpaProperties(ps);

        lcemfb.afterPropertiesSet();

        return lcemfb;
    }

    @Bean
    public DataSource dataSource() {
        //jdbc:hsqldb:mem:testdb
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).setName("UnitTestDB").build();
    }

    @Bean
    public EtablissementRepositoryDto etablissementRepositoryDto() {
        return new EtablissementRepositoryDtoJpa();
    }

    @Bean
    public EtablissementParticipantRepositoryDto etablissementParticipantRepositoryDto() {
        return new EtablissementParticipantRepositoryDtoJpa();
    }

}
