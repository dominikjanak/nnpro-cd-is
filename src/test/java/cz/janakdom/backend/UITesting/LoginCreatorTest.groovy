package cz.janakdom.backend.UITesting

import cz.janakdom.backend.Creator
import cz.janakdom.backend.Randomizer
import cz.janakdom.backend.model.database.User
import cz.janakdom.backend.model.dto.RegisterUserDto
import cz.janakdom.backend.service.UserService
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
class LoginCreatorTest {

    @Autowired
    private Creator creator

    @Autowired
    private Randomizer randomizer

    @Autowired
    private UserService userService

    private final Logger log = LoggerFactory.getLogger(this.getClass())

    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        String username = "uzivatel"
        String password = randomizer.randomString(15)
        String email = "uzivatel@email.com"
        RegisterUserDto user = new RegisterUserDto(username: username, password: password, email: email, firstname: "F", surname: "F")
        userService.save(user)


        Browser.drive {
            go 'http://localhost:3000/login'
            assert title == "CDIS :: Přihlášení"

            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/form/div[2]/div[1]/input")).sendKeys(username)
            driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/form/div[2]/div[2]/input")).sendKeys(password)
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click()
            WebDriverWait wait = new WebDriverWait(driver, 10)

            wait.until(ExpectedConditions.titleIs("CDIS :: Dashboard"))
        }
    }
}