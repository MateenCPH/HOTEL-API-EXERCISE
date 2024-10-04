package dk.lyngby.dao;

import dk.lyngby.Populator;
import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
class HotelDAOTest {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static HotelDAO hotelDAO = new HotelDAO(emf);
    private static Populator populator = new Populator(emf, hotelDAO);

    private static Hotel h1, h2, h3;
    private static List<Hotel> hotels;

    @BeforeAll
    static void beforeAll() {
    }


    @BeforeEach
    void setUp() {
        populator.populateDatabase();
        hotels = hotelDAO.getAll();
        h1 = hotels.get(0);
        h2 = hotels.get(1);
        h3 = hotels.get(2);
    }

    @AfterEach
    void tearDown() {
        populator.cleanUpHotelsAndRooms();
    }

    @Test
    @DisplayName("Test create hotel")
    void create() {
        assertThat(hotelDAO.getAll(), hasSize(3));
        List<Room> testRooms = new ArrayList<>();
        Hotel testHotel = Hotel.builder()
                .hotelName("Hotel 4")
                .hotelAddress("Address 4")
                .build();
        Room testRoom = Room.builder()
                .roomNumber(401)
                .roomPrice(8000)
                .hotel(testHotel)
                .build();
        testRooms.add(testRoom);
        testHotel.setRooms(testRooms);

        hotelDAO.create(testHotel);
        assertThat(hotelDAO.getAll(), hasSize(4));
    }

    @Test
    void update() {
        assertThat(h1.getHotelName(), is("Hotel 1"));
        h1.setHotelName("Hotel 1 updated");
        hotelDAO.update(h1);
        assertThat(hotelDAO.getById(h1.getHotelId()).getHotelName(), is("Hotel 1 updated"));
    }

    @Test
    void delete() {
        assertThat(hotelDAO.getAll(), hasSize(3));
        hotelDAO.delete(h1);
        assertThat(hotelDAO.getAll(), hasSize(2));
    }

    @Test
    void getById() {
        Hotel hotel = hotelDAO.getById(h1.getHotelId());
        assertThat(hotel.getHotelName(), is(h1.getHotelName()));
    }
}