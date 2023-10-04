package nl.salves.workshop.testcontainers.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static nl.salves.workshop.testcontainers.fixture.PlaywrightPage.getPage;

public class VerwijderenTest {

    @BeforeAll
    static void setup() {
        website.start();
        PlaywrightPage.instantiate();
    }

}
