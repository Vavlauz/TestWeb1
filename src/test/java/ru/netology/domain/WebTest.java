package ru.netology.domain;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Давид");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormFieldNameEmpty() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName1() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("David");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName2() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна_Мария");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName3() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormFieldTelephoneEmpty() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone']")).findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldTelephone1() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid")).findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldTelephone2() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7(999)333-55-55");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid")).findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormAllFieldsEmpty() {
        driver.get("http://localhost:9999/");
        System.out.println("");
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector(".input_invalid")).findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }
}
