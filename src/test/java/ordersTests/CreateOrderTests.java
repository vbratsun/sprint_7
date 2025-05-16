package ordersTests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.clients.OrdersClient;
import ru.yandex.practicum.constants.ScooterColor;
import ru.yandex.practicum.models.orders.OrderRequest;
import ru.yandex.practicum.models.orders.OrderResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private List<String> color;

    public CreateOrderTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][]{
                {null},
                {new ArrayList<>(Arrays.asList(ScooterColor.BLACK, ScooterColor.GRAY))},
                {new ArrayList<>(Arrays.asList(ScooterColor.BLACK))},
                {new ArrayList<>(Arrays.asList(ScooterColor.GRAY))}
        };
    }

    @Test
    @DisplayName("Проверка создания заказа")
    @Description("Позитивная проверка возможности создания заказа с разными цветами самоката")
    public void OrderCanBeCreatedSuccessfullyTest() {
        OrderRequest orderRequest = getOrderWithCustomColor(this.color);
        OrdersClient client  = new OrdersClient();

        Response orderResponse = client.create(orderRequest);
        assertEquals("Неверный статус-код", 201, orderResponse.statusCode());
        assertNotEquals("track не должен быть 0", 0, orderResponse.as(OrderResponse.class).getTrack());
    }

    private OrderRequest getOrderWithCustomColor(List<String> color) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFirstName("Ivan");
        orderRequest.setLastName("Pupkin");
        orderRequest.setAddress("5th str. Osaka");
        orderRequest.setMetroStation(4);
        orderRequest.setPhone("+7 800 356 36 36");
        orderRequest.setRentTime(5);
        orderRequest.setDeliveryDate(getDateInDaysFromNow(2));
        orderRequest.setComment("No comments!");
        orderRequest.setColor(color);

        return orderRequest;
    }

    private String getDateInDaysFromNow(int daysToAdd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.now().plusDays(daysToAdd).format(formatter);
    }
}
