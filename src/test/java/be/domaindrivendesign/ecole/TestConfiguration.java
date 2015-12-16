package be.domaindrivendesign.ecole;

import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.BeneficiaireRepository;
import be.domaindrivendesign.ecole.beneficiaire.data.jpa.JpaAgrementRepository;
import be.domaindrivendesign.ecole.beneficiaire.data.jpa.JpaBeneficiaireRepository;
import be.domaindrivendesign.ecole.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.budget.data.jpa.JpaBudgetAnnuelRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaEtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.jpa.JpaImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.service.EcoleDomainService;
import be.domaindrivendesign.ecole.etablissement.service.EcoleDomainServiceImpl;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TestConfiguration {

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
    public ImplantationAnneeScolaireRepository jpaImplantationAnneeScolaireRepository() {
        return new JpaImplantationAnneeScolaireRepository();
    }

    @Bean
    public EtablissementRepository etablissementRepository() {
        return new JpaEtablissementRepository();
    }

    @Bean
    public BudgetAnnuelRepository budgetAnnuelRepository() {
        return new JpaBudgetAnnuelRepository();
    }

    @Bean
    public BeneficiaireRepository beneficiaireRepository() {
        return new JpaBeneficiaireRepository();
    }

    @Bean
    public AgrementRepository agrementRepository() {
        return new JpaAgrementRepository();
    }

    @Bean
    public EcoleDomainService ecoleDomainService() {
        return new EcoleDomainServiceImpl();
    }

}
