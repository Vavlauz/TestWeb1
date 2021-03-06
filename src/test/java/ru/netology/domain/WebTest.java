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
        driver.get("http://localhost:9999/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendForm() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Давид");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldSendForm2() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormFieldNameEmpty() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName1() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("David");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName2() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Анна_Мария");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldName3() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormFieldTelephoneEmpty() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldTelephone1() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormInvalidFieldTelephone2() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Вадим");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7(999)333-55-55");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormAllFieldsEmpty() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("");
        driver.findElement(By.cssSelector(".checkbox__text")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void notSendFormAllFieldCheckBoxNotClick() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Давид");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79993336666");
        driver.findElement(By.cssSelector("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, actualText);
    }

}
