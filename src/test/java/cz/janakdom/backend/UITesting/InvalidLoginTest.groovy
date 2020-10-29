package cz.janakdom.backend.UITesting

import cz.janakdom.backend.Creator
import cz.janakdom.backend.Randomizer
import cz.janakdom.backend.model.database.User
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class InvalidLoginTest {

    @Autowired
    private Creator creator

    @Autowired
    private Randomizer randomizer

    @Autowired
    private BCryptPasswordEncoder encoder

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        String generatedAuthenticationString = randomizer.randomString(15)
        creator.saveEntity(new User(username: generatedAuthenticationString, password: encoder.encode(generatedAuthenticationString)))

        Browser.drive {
            go 'http://127.0.0.1:3000/'
            assert title == "Přihlášení | Citáty"

            driver.findElement(By.name("username")).sendKeys(generatedAuthenticationString)
            driver.findElement(By.name("password")).sendKeys(generatedAuthenticationString + "1")
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click()
            WebDriverWait wait = new WebDriverWait(driver, 10)

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Nespráné přihlašovací údaje!')]")))

        }
    }
}