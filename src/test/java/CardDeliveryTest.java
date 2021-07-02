import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    int days;

    public String dayOfMeeting(int days) {
        LocalDateTime today = LocalDateTime.now().plusDays(days);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String minDate = dateFormat.format(today);
        return minDate;
    }

    @Test
    void shouldSumbitRequest() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Киров");
        $("[data-test-id=date] [type='tel']").doubleClick().setValue(dayOfMeeting(3));
        form.$("[data-test-id=name] input").setValue("Виктор Иванов");
        form.$("[data-test-id=phone] input").setValue("+79896464784");
        form.$("[data-test-id=agreement").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + dayOfMeeting(3)));
    }

}
