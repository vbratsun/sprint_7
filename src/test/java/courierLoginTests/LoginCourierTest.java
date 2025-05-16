package courierLoginTests;

import Base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.models.courier.CourierLoginResponse;

import static org.junit.Assert.*;

public class LoginCourierTest extends TestBase {

    @Test
    @DisplayName("Проверка логина курьера")
    @Description("Позитивная проверка возможности курьера залогиниться")
    public void CourierCanLoginSuccessfullyTest(){
        Response courierResponse = this.client.create(this.courier);
        courierResponse.then().statusCode(201);

        Response courierLoginResponse = this.client.login(courierLogin);
        assertEquals("Неверный статус-код", 200, courierLoginResponse.statusCode());
        assertNotEquals("ID не должен быть 0", 0, courierLoginResponse.as(CourierLoginResponse.class).getId());
    }
}
