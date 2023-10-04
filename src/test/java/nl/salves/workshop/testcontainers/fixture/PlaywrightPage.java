package nl.salves.workshop.testcontainers.fixture;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class PlaywrightPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaywrightPage.class);

    public static final int BROWSER_WIDTH = 1352;
    public static final int BROWSER_HEIGHT = 878;

    // Shared between all tests in this project:
    static Page page = null;
    static BrowserContext context;
    static boolean generateTrace = true;
    static Path tracesPath = Path.of("playwright-reports");


    private PlaywrightPage() {
        // Util class
    }

    public static void instantiate() {
        final Playwright playwright = Playwright.create();
        final Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setTracesDir(tracesPath));

        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(BROWSER_WIDTH, BROWSER_HEIGHT)
                .setTimezoneId("Europe/Amsterdam"));

        if (generateTrace) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true));
        }
        page = context.newPage();
    }

    public static void tearDown() {
        if (generateTrace) {
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(tracesPath.resolve("trace.zip")));
        }
    }

    public static Page getPage() {
        if (page == null) {
            throw new IllegalStateException("Page is nog niet geinstantieerd");
        }
        return page;
    }

    private static boolean isTextVisible(String text) {
        return PlaywrightPage.getPage().getByText(text).isVisible();
    }

    private static void assertThatTextIsVisible(String text) {
        assertThat(PlaywrightPage.getPage().getByText(text)).isVisible();
    }
}
