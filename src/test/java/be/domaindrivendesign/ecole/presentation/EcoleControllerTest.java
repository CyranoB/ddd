package be.domaindrivendesign.ecole.presentation;

import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoList;
import be.domaindrivendesign.ecole.presentation.rest.v1.EcoleController;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EcoleRestConfiguration.class)
@WebAppConfiguration
@DatabaseSetup("/datasets/etablissement/etablissements_testdto.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@IntegrationTest("server.port:0")
public class EcoleControllerTest {

    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new EcoleController());
        RestAssured.port = port;
    }

    @Test
    public void canListEtablissements() {
        RestAssuredMockMvc.reset();
        Response response = given().contentType(JSON).when().get(EcoleRestConfiguration.URL_API);
        assertEquals(OK.value(), response.getStatusCode());
        List<EtablissementDtoList> etablissements = Arrays.asList(response.getBody().as(EtablissementDtoList[].class));
        assertEquals(5, etablissements.size());
    }

    @Test
    public void canGetEtablissementById() {
        Response response = given().contentType(JSON).when().get(EcoleRestConfiguration.URL_API + "9536d73b-9a4a-cf5c-7f6a-08d2892a9521");
        assertEquals(OK.value(), response.getStatusCode());
        EtablissementDto dto = response.as(EtablissementDto.class);
        assertEquals(UUID.fromString("9536d73b-9a4a-cf5c-7f6a-08d2892a9521"), dto.id);
        assertEquals(1000, dto.adresse.codePostal);
        assertEquals("445", dto.numeroReference);
    }
}