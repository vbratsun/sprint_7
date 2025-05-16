package courierTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.clients.CourierClient;
import ru.yandex.practicum.constants.ErrorMessages;
import ru.yandex.practicum.constants.UserData;
import ru.yandex.practicum.models.courier.CourierLoginRequest;
import ru.yandex.practicum.models.courier.CourierLoginResponse;
import ru.yandex.practicum.models.courier.CourierRequest;
import ru.yandex.practicum.models.courier.CourierResponse;
import ru.yandex.practicum.models.errors.ErrorResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests {

    private CourierClient client;
    private int courierId;
    private CourierRequest courier;
    private CourierLoginRequest courierLogin;

    @Before
    public void setUp() {
        this.client = new CourierClient();
        this.courierId = 0;
        this.courier = new CourierRequest(UserData.LOGIN, UserData.PASSWORD, UserData.FIRST_NAME);
        this.courierLogin = new CourierLoginRequest(UserData.LOGIN, UserData.PASSWORD);
    }

    @Test
    @DisplayName("Проверка создания курьера")
    @Description("Позитивная проверка возможности создания курьера")
    public void CourierCanBeCreatedTest() {
        Response courierResponse = this.client.create(this.courier);
        assertEquals("Неверный статус-код", 201, courierResponse.statusCode());
        assertTrue("Неверное значение поля 'ok'", courierResponse.as(CourierResponse.class).isOk());
    }

    @Test
    @DisplayName("Проверка невозможности создания 2х одинаковых курьеров")
    @Description("Проверка невозможности создания 2х курьеров с одинаковыми данными")
    public void UnableToCreateTwoSameCouriersTest() {
        Response courierResponse = this.client.create(this.courier);
        courierResponse.then().statusCode(201);

        Response sameCourierResponse = this.client.create(this.courier);
        assertEquals("Неверный статус-код", 409, sameCourierResponse.statusCode());
        assertEquals("Неверное значение поля 'message'", ErrorMessages.CREATE_ACCOUNT_ALREADY_USED, sameCourierResponse.as(ErrorResponse.class).getMessage());
    }

    @Test
    @DisplayName("Проверка невозможности создания курьеров с одинаковым логином")
    @Description("Проверка невозможности создания 2х курьеров с одинаковым логином")
    public void UnableToCreateCourierWithSameLoginTest() {
        Response courierResponse = this.client.create(this.courier);
        courierResponse.then().statusCode(201);

        CourierRequest sameLoginCourier = new CourierRequest(UserData.LOGIN, UserData.PASSWORD_UPDATED, UserData.FIRST_NAME_UPDATED);
        Response sameLoginCourierResponse = this.client.create(sameLoginCourier);
        assertEquals("Неверный статус-код", 409, sameLoginCourierResponse.statusCode());
        assertEquals("Неверное значение поля 'message'", ErrorMessages.CREATE_ACCOUNT_ALREADY_USED, sameLoginCourierResponse.as(ErrorResponse.class).getMessage());
    }

    @After
    public void tearDown() {
        try {
            this.courierId = getExistingCourierId(this.courierLogin);
        } catch (Exception e) {
            System.out.println("Не удалось получить идентификатор курьера: " + e);
        } finally {
            this.client.delete(this.courierId);
        }
    }

    private int getExistingCourierId(CourierLoginRequest courierLogin){
        Response courierLoginResponse = this.client.login(courierLogin);
        courierLoginResponse.then().statusCode(200);

        return courierLoginResponse.as(CourierLoginResponse.class).getId();
    }
}
