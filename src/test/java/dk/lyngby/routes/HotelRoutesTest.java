package dk.lyngby.routes;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import dk.lyngby.Populator;
import dk.lyngby.config.AppConfig;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.HotelDAO;
import dk.lyngby.dto.HotelDTO;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelRoutesTest {
    private static Javalin app;
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static String BASE_URL = "http://localhost:7777/api/v1";
    private static HotelDAO hotelDAO = new HotelDAO(emf);
    private static Populator populator = new Populator(emf, hotelDAO);

    private static Hotel h1, h2, h3;
    private static List<Hotel> hotels;

    @BeforeAll
    void beforeAll() {
        app = AppConfig.startServer(7777, emf);
        //HibernateConfig.setTest(true);
    }

    @BeforeEach
    void setUp() {
        populator.populateDatabase();
        hotels = hotelDAO.getAll();
        h1 = hotels.get(0);
        //h2 = hotels.get(1);
        //h3 = hotels.get(2);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpHotelsAndRooms();
    }

    @AfterAll
    void afterAll() {
        AppConfig.stopServer(app);
    }

    @Test
    @DisplayName("Test get a single hotel")
    void getHotel() {
        Hotel hotel =
                given()
                        .when()
                        .get(BASE_URL + "/hotel/" + h1.getHotelId())
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Hotel.class);
        assertThat(h1.getHotelName(), equalTo(hotel.getHotelName()));
    }

    @Test
    @DisplayName("Test get all hotels")
    void getAllHotels() {
        Hotel[] hotels =
                given()
                        .when()
                        .get(BASE_URL + "/hotel")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Hotel[].class);
        assertThat(hotels, arrayContainingInAnyOrder(h1));
    }

    @Test
    @DisplayName("Test get rooms by hotel id")
    void getRoomsByHotelId() {
        Hotel hotel = hotelDAO.getById(h1.getHotelId());
        Room[] rooms =
                given()
                        .when()
                        .get(BASE_URL + "/hotel/" + hotel.getHotelId() + "/rooms")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .as(Room[].class);
        System.out.println("ALL ROOMS IN GET METHOD LIST: ");
        for (Room room : rooms) {
            System.out.println(room);
        }
        System.out.println("ALL ROOMS IN POPULATOR LIST: ");
        h1.getRooms().forEach(System.out::println);
        assertThat(rooms, arrayContainingInAnyOrder(h1.getRooms()));
    }
}