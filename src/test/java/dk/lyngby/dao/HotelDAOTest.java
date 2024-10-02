package dk.lyngby.dao;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
class HotelDAOTest {
    private static HotelDAO hotelDAO;
    private static EntityManagerFactory emf;
    Hotel h1, h2, h3;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactory();
        HibernateConfig.setTest(true);
        hotelDAO = new HotelDAO(emf);
    }


    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createQuery("DELETE FROM Room").executeUpdate();
            h1 = Hotel.builder()
                    .hotelId(1L)
                    .hotelName("Hotel 1")
                    .hotelAddress("Address 1")
                    .rooms(List.of(new Room(11L, h1, 11, 1.1), (new Room(12L, h1, 12, 1.2))))
                    .build();
            h2 = Hotel.builder()
                    .hotelId(2L)
                    .hotelName("Hotel 2")
                    .hotelAddress("Address 2")
                    .rooms(List.of(new Room(21L, h2, 21, 2.1), (new Room(22L, h2, 22, 2.2)))
                    )
                    .build();
            h3 = Hotel.builder()
                    .hotelId(3L)
                    .hotelName("Hotel 3")
                    .hotelAddress("Address 3")
                    .rooms(List.of(new Room(31L, h3, 31, 3.1), (new Room(32L, h3, 32, 3.2)))
                    )
                    .build();

            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            em.getTransaction().commit();
        }

    }

    @Test
    @DisplayName("Test create hotel")
    void create() {
        assertThat(hotelDAO.getAll(), hasSize(3));
        Hotel hotel = Hotel.builder()
                .hotelId(4L)
                .hotelName("Hotel 4")
                .hotelAddress("Address 4")
                .rooms(List.of(new Room(41L, h1, 41, 4.1), (new Room(42L, h1, 42, 4.2)))
                )
                .build();
        hotelDAO.create(hotel);
        assertThat(hotelDAO.getAll(), hasSize(4));
        assertThat(hotelDAO.getAll(), hasItem(hotel));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getById() {
    }
}