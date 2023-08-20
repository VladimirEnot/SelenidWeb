import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {


    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldGetDeliveryCard() {
        String date = generateDate(4);
        Selenide.open("http://localhost:9999");
        $("[data-test-id = 'city'] input").setValue("Петрозаводск");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id= 'name'] input").setValue("Никитин Александр");
        $("[data-test-id = 'phone'] input").setValue("+79012345678");
        $("[data-test-id = 'agreement']").click();
        $$("[type = button]").find(exactText("Забронировать")).click();
        $("[data-test-id = 'notification']").shouldHave(exactText("Успешно! Встреча успешно забронирована на " + date), Duration.ofMillis(15000)).shouldBe(exist);
    }
}