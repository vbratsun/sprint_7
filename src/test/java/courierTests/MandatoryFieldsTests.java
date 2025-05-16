package courierTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.clients.CourierClient;
import ru.yandex.practicum.models.courier.CourierRequest;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MandatoryFieldsTests{

    private String login;
    private String password;
    private String firstName;
    private int expectedStatusCode;

    public MandatoryFieldsTests(String login, String password, String firstName, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][]{
                {null, null, null, 400},
                {"vlad_bratsun", null, null, 400},
                {null, "1", null, 400},
                {null, null, "vlad", 400},
                {"vlad_bratsun", null, "vlad", 400},
                {null, "1", "vlad", 400},
        };
    }

    @Test
    @DisplayName("Проверка создания курьера без обязательных полей")
    @Description("Проверка невозможности создания курьера без обязательных полей")
    public void AllFieldsShouldBeFilledInToCreateCourierTest() {
        CourierRequest courier = new CourierRequest(this.login, this.password, this.firstName);
        CourierClient client = new CourierClient();

        Response courierResponse = client.create(courier);
        assertEquals("Неверный статус-код", this.expectedStatusCode, courierResponse.statusCode());
    }
}
