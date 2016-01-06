package be.domaindrivendesign.ecole.presentation;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EcoleRestConfiguration.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DatabaseSetup("/datasets/etablissement/etablissements_testdto.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Category(be.domaindrivendesign.kernel.common.annotation.IntegrationTest.class)
public class EcoleControllerIntegrationTest {
    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void canListEtablissements() {
        when().get(EcoleRestConfiguration.URL_API)
                .then().statusCode(OK.value()).and().contentType(JSON);
    }

    @Test
    public void canGetEtablissementById() {
        when().get(EcoleRestConfiguration.URL_API + "9536d73b-9a4a-cf5c-7f6a-08d2892a9521")
                .then().statusCode(OK.value()).and().contentType(JSON);
    }
}