package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.BrowserstackConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;


public class BrowserstackDriver implements WebDriverProvider {

    private static final BrowserstackConfig config = ConfigFactory.create(BrowserstackConfig.class);

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        // Set your access credentials
        caps.setCapability("browserstack.user", config.browserstackUser());
        caps.setCapability("browserstack.key", config.browserstackKey());

        // Set URL of the application under test
        caps.setCapability("app", config.app());

        // Specify device and os_version for testing
        caps.setCapability("device", config.device());
        caps.setCapability("os_version", config.osVersion());

        // Set other BrowserStack capabilities
        caps.setCapability("project", config.projectName());
        caps.setCapability("build", config.buildName());
        caps.setCapability("name", config.name());
        caps.setCapability("url", config.browserstackUrl());

        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        try {
            return new RemoteWebDriver(
                    new URL(config.browserstackUrl()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
