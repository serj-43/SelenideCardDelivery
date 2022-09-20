
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTest {

    @BeforeEach
    void initOperations() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPlanMeeting() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id='city']//input").setValue("Киров");
        $x("//*[@data-test-id='city']//input").clear();
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").setValue(date);
        $x("//*[@data-test-id='name']//input").setValue("Иван Петров-Сидоров");
        $x("//*[@data-test-id='phone']//input").setValue("+79536992752");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button//*[text()='Забронировать']").click();
        $x("//*[@data-test-id='notification']//*[@class='notification__title'][text()='Успешно!']").should(visible, Duration.ofSeconds(20));
        $x("//*[@data-test-id='notification']//*[@class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

}
