package ru.yandex.practicum.orders.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.practicum.clients.OrdersClient;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class GetOrdersTests {

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Позитивная проверка возможности получить заполненный список заказов")
    public void getOrdersListTest() {
        OrdersClient client = new OrdersClient();
        Response ordersResponse = client.getList();
        assertEquals("Неверный статус-код", HttpStatus.SC_OK, ordersResponse.statusCode());
        ordersResponse
                .then()
                .assertThat()
                .body("orders", not(empty()))
                .body("orders.size()", greaterThan(0));
    }
}
