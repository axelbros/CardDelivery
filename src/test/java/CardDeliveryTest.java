import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldSumbitRequest() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Киров");
        $("[data-test-id=date] input").doubleClick().sendKeys(formatter.format(newDate));
        form.$("[data-test-id=name] input").setValue("Виктор Иванов");
        form.$("[data-test-id=phone] input").setValue("+79896464784");
        form.$("[data-test-id=agreement").click();
        $$("button").find(exactText("Забронировать")).click();
        Configuration.timeout = 15000;
        $(withText("Успешно")).shouldBe(visible);
    }

}
