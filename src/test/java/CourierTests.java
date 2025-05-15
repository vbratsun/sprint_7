import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.clients.CourierClient;
import ru.yandex.practicum.models.courier.CourierLoginRequest;
import ru.yandex.practicum.models.courier.CourierLoginResponse;
import ru.yandex.practicum.models.courier.CourierRequest;

public class CourierTests {

    private CourierClient client;
    private int courierId;

    @Before
    public void setUp(){
        this.client = new CourierClient();
        this.courierId = 0;
    }

    @Test
    public void CreateCourierTest(){
        CourierRequest courier = new CourierRequest("vlad_bratsun","qqq111!!!","vlad");
        CourierLoginRequest courierLogin = new CourierLoginRequest("vlad_bratsun","qqq111!!!");

        Response courierResponse = this.client.create(courier);
        courierResponse.then().statusCode(201);

        Response courierLoginResponse = this.client.login(courierLogin);
        courierLoginResponse.then().statusCode(200);

        this.courierId = courierLoginResponse.as(CourierLoginResponse.class).getId();
    }

    @After
    public void teatDown(){
        this.client.delete(this.courierId);
    }
}
