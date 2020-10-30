package cz.janakdom.backend.UITesting

import cz.janakdom.backend.Creator
import cz.janakdom.backend.Randomizer
import cz.janakdom.backend.model.database.User
import org.junit.jupiter.api.Test
//import geb.Browser
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class FormTest {

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
            go 'http://127.0.0.1:8080/'
            assert title == "Přihlášení | Citáty"

            driver.findElement(By.name("username")).sendKeys(generatedAuthenticationString)
            driver.findElement(By.name("password")).sendKeys(generatedAuthenticationString)
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click()
            WebDriverWait wait = new WebDriverWait(driver, 10)

            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))

            driver.findElement(By.xpath("//a[contains(text(), 'Autoři')]")).click()
            assert title == "Seznam autorů | Citáty"

            driver.findElement(By.xpath("//a[contains(text(), 'Nový autor')]")).click()
            assert title == "Nový autor | Citáty"

            String firstname = randomizer.randomString(15)
            String surname = randomizer.randomString(15)
            String country = randomizer.randomString(2)
            driver.findElement(By.name("firstname")).sendKeys(firstname)
            driver.findElement(By.name("surname")).sendKeys(surname)
            driver.findElement(By.name("country")).sendKeys(country)

            driver.findElement(By.xpath("//div/span[2][contains(text(), 'Přidat')]")).click()
            wait.until(ExpectedConditions.titleIs("Seznam autorů | Citáty"))

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + firstname + "')]")))
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + surname + "')]")))
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '" + country + "')]")))
        }
    }
}
