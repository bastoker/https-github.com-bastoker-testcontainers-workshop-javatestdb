package nl.salves.workshop.testcontainers.vakantieplanner.api;

import io.restassured.http.ContentType;
import nl.salves.workshop.testcontainers.vakantieplanner.VakantieplannerApplication;
import nl.salves.workshop.testcontainers.vakantieplanner.dao.VakantieRepository;
import nl.salves.workshop.testcontainers.vakantieplanner.model.Holiday;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = VakantieplannerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class VakantieplannerApiTests {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"))
            .withDatabaseName("integration-tests-db")
            .withUsername("admin")
            .withPassword("@dm1n");

    @LocalServerPort
    protected int localServerPort;

    @Autowired
    VakantieRepository vakantieRepository;

    @DynamicPropertySource
    static void configureApplicationContext(DynamicPropertyRegistry registry) {
        postgres.start();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Vakanties zijn op te vragen")
    void retrieveHolidays() {
        // Voeg een vakantie toe en bewaar het technische id
        int idOfHoliday = vakantieRepository.addHoliday("sam", new Holiday(
                -1,
                "Zomervakantie",
                LocalDate.of(2019, 7, 2),
                LocalDate.of(2019, 7, 22)
        )).id();

        // Call our API using this bearer token:
        when()
                .get(String.format("http://localhost:%d/holiday/sam", localServerPort))
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("findAll{i -> i.id == %s && i.description == \"%s\"}".formatted(idOfHoliday, "Zomervakantie"), not(empty()))
        ;
    }

    @DisplayName("Een vakantie toevoegen lukt als deze niet overlapt met een bestaande vakantie")
    @Test
    void addingASecondHolidaySucceeds() {
        // Voeg een vakantie toe en bewaar het technische id
        vakantieRepository.addHoliday("sam", new Holiday(
                -1,
                "Zomervakantie",
                LocalDate.of(2018, 7, 2),
                LocalDate.of(2018, 7, 22)
        ));

        // Tweede vakantie om toe te voegen:
        Holiday holiday2 = new Holiday(
                123,
                "Zomervakantie",
                LocalDate.of(2017, 7, 20),
                LocalDate.of(2017, 12, 30)
        );

        // Call our API using this bearer token:
        given()
                .contentType(ContentType.JSON)
                .body(holiday2)
                .when()
                .post(String.format("http://localhost:%d/holiday/bob", localServerPort))
                .then()
                .log().ifValidationFails()
                .statusCode(200);
    }

    @DisplayName("Een vakantie toevoegen kan niet als deze overlapt met een bestaande vakantie")
    @Test
    void addingHolidayThatConflictsWithExistingHolidayIsRejected() {
        // Voeg een vakantie toe en bewaar het technische id
        vakantieRepository.addHoliday("sam", new Holiday(
                -1,
                "Zomervakantie",
                LocalDate.of(2022, 7, 2),
                LocalDate.of(2022, 7, 22)
        ));

        // 2nd holiday to post:
        Holiday holiday2 = new Holiday(
                123,
                "Zomervakantie",
                LocalDate.of(2022, 7, 20),
                LocalDate.of(2022, 8, 30)
        );

        // Call our API using this bearer token:
        given()
                .contentType(ContentType.JSON)
                .body(holiday2)
                .when()
                .post(String.format("http://localhost:%d/holiday/bob", localServerPort))
                .then()
                .log().ifValidationFails()
                .statusCode(409);
    }
}
