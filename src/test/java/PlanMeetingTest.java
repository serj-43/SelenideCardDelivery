
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PlanMeetingTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void initOperations() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPlanMeeting() {
        String planDate = generateDate(3);
        $x("//*[@data-test-id='city']//input").setValue("Киров");
        $x("//*[@data-test-id='date']//input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $x("//*[@data-test-id='date']//input").setValue(planDate);
        $x("//*[@data-test-id='name']//input").setValue("Иван Петров-Сидоров");
        $x("//*[@data-test-id='phone']//input").setValue("+79536992752");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button//*[text()='Забронировать']").click();
        $x("//*[@data-test-id='notification']//*[@class='notification__title'][text()='Успешно!']").should(visible, Duration.ofSeconds(20));
        $x("//*[@data-test-id='notification']//*[@class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + planDate));
    }

    @Test
    void shouldPlanMeetingByComplexElements() {
        $x("//*[@data-test-id='city']//input").setValue("Ки");
        $x("//*[@class='popup__container']//*[@class='menu-item__control'][text()='Киров']").click();
        $x("//*[contains(@class,'calendar-input')]//button").click();
        LocalDate currentDate = LocalDate.now().plusDays(3);
        LocalDate planDate = LocalDate.now().plusDays(7);
        if (currentDate.getMonth() != planDate.getMonth()) $x("//*[@data-step=\"1\"]").click();
        String day = String.valueOf(planDate.getDayOfMonth());
        $$x("//*[contains(@class,'calendar__day')]").findBy(text(day)).click();
        $x("//*[@data-test-id='name']//input").setValue("Иван Петров-Сидоров");
        $x("//*[@data-test-id='phone']//input").setValue("+79536992752");
        $x("//*[@data-test-id='agreement']").click();
        $x("//button//*[text()='Забронировать']").click();
        $x("//*[@data-test-id='notification']//*[@class='notification__title'][text()='Успешно!']").should(visible, Duration.ofSeconds(20));
        $x("//*[@data-test-id='notification']//*[@class='notification__content']").shouldHave(exactText("Встреча успешно забронирована на " + generateDate(7)));
    }

}
