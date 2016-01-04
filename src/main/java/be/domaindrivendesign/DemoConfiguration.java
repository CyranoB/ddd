package be.domaindrivendesign;

import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import be.domaindrivendesign.ecole.application.model.EcoleServiceImpl;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepository;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepositoryDto;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.BeneficiaireRepository;
import be.domaindrivendesign.ecole.module.beneficiaire.data.jpa.AgrementRepositoryJpa;
import be.domaindrivendesign.ecole.module.beneficiaire.data.jpa.BeneficiaireRepositoryJpa;
import be.domaindrivendesign.ecole.module.beneficiaire.data.mock.AgrementRepositoryDtoMock;
import be.domaindrivendesign.ecole.module.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.module.budget.data.jpa.BudgetAnnuelRepositoryJpa;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.jpa.EtablissementRepositoryDtoJpa;
import be.domaindrivendesign.ecole.module.etablissement.data.jpa.EtablissementRepositoryJpa;
import be.domaindrivendesign.ecole.module.etablissement.data.jpa.ImplantationAnneeScolaireRepositoryJpa;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainService;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainServiceImpl;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.ecole.module.laitecole.data.jpa.EtablissementParticipantRepositoryDtoJpa;
import be.domaindrivendesign.ecole.presentation.rest.v1.EcoleController;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpaImpl;
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

//import be.domaindrivendesign.ecole.module.beneficiaire.data.mock.AgrementRepositoryDtoMock;

//TODO compatimenter les configs
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"be.domaindrivendesign"})
@EnableJpaRepositories(basePackages = {"be.domaindrivendesign"})
@EnableTransactionManagement
public class DemoConfiguration {
    @Bean
    public EcoleController ecoleController() {
        return new EcoleController();
    }

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
        return builder.setType(EmbeddedDatabaseType.HSQL).setName("AppDB").build();
    }

    @Bean
    public ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository() {
        return new ImplantationAnneeScolaireRepositoryJpa();
    }

    @Bean
    public EtablissementRepository etablissementRepository() {
        return new EtablissementRepositoryJpa();
    }

    @Bean
    public EtablissementRepositoryDto etablissementRepositoryDto() {
        return new EtablissementRepositoryDtoJpa();
    }

    @Bean
    public EtablissementParticipantRepositoryDto etablissementParticipantRepositoryDto() {
        return new EtablissementParticipantRepositoryDtoJpa();
    }

    @Bean
    public AgrementRepositoryDto agrementRepositoryDto() {
        return new AgrementRepositoryDtoMock();
    }

    @Bean
    public BudgetAnnuelRepository budgetAnnuelRepository() {
        return new BudgetAnnuelRepositoryJpa();
    }

    @Bean
    public BeneficiaireRepository beneficiaireRepository() {
        return new BeneficiaireRepositoryJpa();
    }

    @Bean
    public AgrementRepository agrementRepository() {
        return new AgrementRepositoryJpa();
    }

    @Bean
    public EcoleDomainService ecoleDomainService() {
        return new EcoleDomainServiceImpl();
    }

    @Bean
    public EcoleService ecoleService() {
        return new EcoleServiceImpl();
    }

}
