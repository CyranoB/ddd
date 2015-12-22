package be.domaindrivendesign.ecole.application;

import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import be.domaindrivendesign.ecole.application.model.EcoleServiceImpl;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepositoryDto;
import be.domaindrivendesign.ecole.module.beneficiaire.data.mock.AgrementRepositoryDtoMock;
import be.domaindrivendesign.ecole.module.budget.data.interfaces.BudgetAnnuelRepository;
import be.domaindrivendesign.ecole.module.budget.data.jpa.BudgetAnnuelRepositoryJpa;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.jpa.ImplantationAnneeScolaireRepositoryJpa;
import be.domaindrivendesign.ecole.module.etablissement.data.mock.EtablissementRepositoryMock;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainService;
import be.domaindrivendesign.ecole.module.etablissement.service.EcoleDomainServiceImpl;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepository;
import be.domaindrivendesign.ecole.module.laitecole.data.interfaces.EtablissementParticipantRepositoryDto;
import be.domaindrivendesign.ecole.module.laitecole.data.jpa.EtablissementParticipantRepositoryDtoJpa;
import be.domaindrivendesign.ecole.module.laitecole.mock.EtablissementParticipantRepositoryMock;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpa;
import be.domaindrivendesign.kernel.data.jpa.UnitOfWorkJpaImpl;
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


@Configuration
@EnableAutoConfiguration

@EntityScan(basePackages = {"be.domaindrivendesign"})
@EnableJpaRepositories(basePackages = {"be.domaindrivendesign"})
@EnableTransactionManagement
public class EcoleServiceConfiguration {

    @Bean
    public UnitOfWorkJpa unitOfWork() {
        return new UnitOfWorkJpaImpl();
    }

    @Bean
    public EcoleService ecoleService() {
        return new EcoleServiceImpl();
    }

    @Bean
    public EcoleDomainService ecoleDomainService() {
        return new EcoleDomainServiceImpl();
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
    public EtablissementParticipantRepositoryDto etablissementParticipantRepositoryDto() {
        return new EtablissementParticipantRepositoryDtoJpa();
    }

    @Bean
    public EtablissementParticipantRepository etablissementParticipantRepositoryMock() {
        return new EtablissementParticipantRepositoryMock();
    }

    @Bean
    public ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository() {
        return new ImplantationAnneeScolaireRepositoryJpa();
    }

    @Bean
    public EtablissementRepository etablissementRepository() {
        return new EtablissementRepositoryMock();
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
}
