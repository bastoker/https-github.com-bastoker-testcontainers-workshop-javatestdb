package nl.salves.workshop.testcontainers.db;

import nl.salves.workshop.testcontainers.fixture.PlaywrightPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static nl.salves.workshop.testcontainers.fixture.PlaywrightPage.getPage;

public class HappyFlowTest {

    static GenericContainer<?> website =  new GenericContainer<>(DockerImageName.parse("docker-101:main"))
            .withExposedPorts(80)
            .waitingFor(Wait.forHttp("/")
                    .forStatusCode(200));

    @BeforeAll
    static void setup() {
        website.start();
        PlaywrightPage.instantiate();
    }

    @Test
    void paginaWordtWeergegeven() {
        getPage().navigate("http://" + website.getHost() + ':' + website.getFirstMappedPort());

        assertThat(getPage().getByText("Docker-101")).isVisible();
    }

    @Test
    void mijnEigenTekstWordtGoedWeergegeven() {
       // Maak hier je eigen test

    }
}
