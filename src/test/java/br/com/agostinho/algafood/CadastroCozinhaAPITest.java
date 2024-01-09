package br.com.agostinho.algafood;

import br.com.agostinho.algafood.domain.model.Cozinha;
import br.com.agostinho.algafood.domain.service.CozinhaService;
import br.com.agostinho.algafood.utils.DataBaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaAPITest {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @Autowired
    private DataBaseCleaner dataBaseCleaner;

    @Autowired
    private CozinhaService cozinhaService;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
        flyway.migrate();

        dataBaseCleaner.clearTables();
        this.prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
                RestAssured.given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarRespostaEStatus200_QuandoConsultarCozinhaExistente(){
        RestAssured
                .given()
                    .pathParams("cozinhaId", 2)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("titulo", Matchers.equalTo("Teste2"));
    }

    @Test
    public void deveRetornarRespostaEStatus500_QuandoConsultarCozinhaInexistente(){
        RestAssured
                .given()
                    .pathParams("cozinhaId", 10)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void verificaCoisas(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                .body("", IsCollectionWithSize.hasSize(2))
                .body("titulo", Matchers.hasItems("Teste", "Teste2"));
    }

    @Test
    public void retornaStatus201_QuandoCadastrar(){
        RestAssured
                .given()
                    .body("{ \"titulo\": \"Chinesa\" }")
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    private void prepararDados(){
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Teste");
        cozinhaService.criar(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Teste2");
        cozinhaService.criar(cozinha2);
    }
}
