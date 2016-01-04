package be.domaindrivendesign;

import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication
@SpringApplicationConfiguration(classes = DemoConfiguration.class)
@WebAppConfiguration
public class DemoApplication {

    @Autowired
    EcoleService ecoleService;

    @Autowired
    ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository;

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);

    }

    @PostConstruct
    private void init() throws Exception {
        FlatXmlDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(ClassLoader.getSystemResourceAsStream("test.xml"))));
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }


}
