package cz.janakdom.backend.UITesting

import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoginTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        Browser.drive {
            go 'https://nnpia.dominikjanak.cz/'
            assert title == "Přihlášení | Citáty"

            driver.findElement(By.name("username")).sendKeys("janakdom")
            driver.findElement(By.name("password")).sendKeys("739143789")
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click()
            WebDriverWait wait = new WebDriverWait(driver, 60)

            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))
        }
    }
}