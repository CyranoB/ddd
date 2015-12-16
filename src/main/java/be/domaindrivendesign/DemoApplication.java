package be.domaindrivendesign;

import be.domaindrivendesign.ecole.beneficiaire.data.jpa.AgrementRepositoryJpa;
import be.domaindrivendesign.ecole.beneficiaire.data.jpa.BeneficiaireRepositoryJpa;
import be.domaindrivendesign.ecole.budget.data.jpa.BudgetAnnuelRepositoryJpa;
import be.domaindrivendesign.ecole.etablissement.data.jpa.EtablissementRepositoryJpa;
import be.domaindrivendesign.ecole.etablissement.data.jpa.ImplantationAnneeScolaireRepositoryJpa;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpaImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@EntityScan
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public UnitOfWork unitOfWork() {
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
    public ImplantationAnneeScolaireRepositoryJpa jpaImplantationAnneeScolaireRepository() {
        return new ImplantationAnneeScolaireRepositoryJpa();
    }

    @Bean
    public EtablissementRepositoryJpa jpaEtablissementRepository() {
        return new EtablissementRepositoryJpa();
    }

    @Bean
    public BudgetAnnuelRepositoryJpa jpaBudgetAnnuelRepository() {
        return new BudgetAnnuelRepositoryJpa();
    }

    @Bean
    public BeneficiaireRepositoryJpa jpaBeneficiaireRepository() {
        return new BeneficiaireRepositoryJpa();
    }

    @Bean
    public AgrementRepositoryJpa jpaAgrementRepository() {
        return new AgrementRepositoryJpa();
    }


}
