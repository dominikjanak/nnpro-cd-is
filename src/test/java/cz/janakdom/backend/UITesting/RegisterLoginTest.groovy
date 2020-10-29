package cz.janakdom.backend.UITesting

import cz.janakdom.backend.Creator
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class RegisterLoginTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    @Autowired
    private Creator creator

    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        String username = "tomas"

        Browser.drive {
            go 'http://127.0.0.1:3000/'
            WebDriverWait wait = new WebDriverWait(driver, 10)

            assert title == "Přihlášení | Citáty"
            driver.findElement(By.xpath("//*[contains(text(), 'Zaregistrovat se')]")).click()

            assert title == "Registrace | Citáty"
            driver.findElement(By.name("username")).sendKeys(username)
            driver.findElement(By.name("firstname")).sendKeys("janakdom12")
            driver.findElement(By.name("surname")).sendKeys("janakdom12")
            driver.findElement(By.name("email")).sendKeys(username + "@dominikjanak.cz")
            driver.findElement(By.name("password")).sendKeys("heslo123")
            driver.findElement(By.name("passwordAgain")).sendKeys("heslo123")
            driver.findElement(By.xpath("//input[@value='Zaregistrovat se']")).click()

            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))

            driver.findElement(By.xpath("//a[@href='/logout']")).click()
            wait.until(ExpectedConditions.titleIs("Přihlášení | Citáty"))

            driver.findElement(By.name("username")).sendKeys(username)
            driver.findElement(By.name("password")).sendKeys("heslo123")

            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click()
            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))

        }
    }
}