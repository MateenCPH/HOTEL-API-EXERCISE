package dk.lyngby;

import dk.lyngby.dao.HotelDAO;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class Populator {

    private static EntityManagerFactory emf;
    private static HotelDAO hotelDAO;

    public Populator(EntityManagerFactory emf, HotelDAO hotelDAO) {
        this.emf = emf;
        this.hotelDAO = hotelDAO;
    }

    public void populateDatabase() {
        List<Room> rooms = new ArrayList<>();

            Hotel h1 = Hotel.builder()
                    .hotelName("Hotel 1")
                    .hotelAddress("Address 1")
                    .build();

            Room r1 = Room.builder()
                    .roomNumber(101)
                    .roomPrice(5000)
                    .hotel(h1)
                    .build();

            Room r2 = Room.builder()
                    .roomNumber(102)
                    .roomPrice(6000)
                    .hotel(h1)
                    .build();

            rooms.add(r1);
            rooms.add(r2);
            h1.setRooms(rooms);
            hotelDAO.create(h1);
    }

    public void cleanUpHotelsAndRooms() {
        //Delete all fro database and reset id sequence
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Room").executeUpdate();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hotel_hotelid_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE room_roomid_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
