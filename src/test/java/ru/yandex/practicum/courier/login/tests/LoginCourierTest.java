package ru.yandex.practicum.courier.login.tests;

import org.apache.http.HttpStatus;
import ru.yandex.practicum.base.LoginCourierTestBase;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.models.courier.CourierLoginResponse;

import static org.junit.Assert.*;

public class LoginCourierTest extends LoginCourierTestBase {

    @Test
    @DisplayName("Проверка логина курьера")
    @Description("Позитивная проверка возможности курьера залогиниться")
    public void courierCanLoginSuccessfullyTest() {
        Response courierLoginResponse = this.client.login(courierLogin);
        assertEquals("Неверный статус-код", HttpStatus.SC_OK, courierLoginResponse.statusCode());
        assertNotEquals("ID не должен быть 0", 0, courierLoginResponse.as(CourierLoginResponse.class).getId());
    }
}
