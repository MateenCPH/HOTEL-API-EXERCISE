package dk.lyngby.dao;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO implements IDAO<Hotel> {

    private final EntityManagerFactory emf;

    public HotelDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Hotel create(Hotel hotel) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while saving hotel");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Hotel update(Hotel hotel) {

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Hotel foundHotel = em.find(Hotel.class, hotel.getHotelId());

            if (hotel.getHotelName() != null) {
                foundHotel.setHotelName(hotel.getHotelName());
            }
            if (hotel.getHotelAddress() != null) {
                foundHotel.setHotelAddress(hotel.getHotelAddress());
            }
            em.merge(foundHotel);
            em.getTransaction().commit();
            return foundHotel;
        }
    }

    @Override
    public void delete(Hotel hotelInput) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, hotelInput.getHotelId());
            em.remove(hotel);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while deleting hotel");
            e.printStackTrace();
        }
    }

    @Override
    public Hotel getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Hotel hotel = em.find(Hotel.class, id);
            return hotel;
        } catch (PersistenceException e) {
            System.out.println("Error while getting hotel by id: " + id);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hotel> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
            return hotels;
        } catch (PersistenceException e) {
            System.out.println("Error while getting all hotels");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("hotel");
        HotelDAO hotelDAO = new HotelDAO(emf);
        List<Room> rooms = new ArrayList<>();

        // Create Hotel entity
        Hotel hotel = Hotel.builder()
                .hotelName("Hotel 2")
                .hotelAddress("Address 2")
                .build();

        // Create Room entities and set their corresponding hotel
        Room room1 = Room.builder()
                .roomNumber(201)
                .roomPrice(7000)
                .hotel(hotel)  // Set the hotel for the room
                .build();

        Room room2 = Room.builder()
                .roomNumber(202)
                .roomPrice(7500)
                .hotel(hotel)  // Set the hotel for the room
                .build();

        // Add rooms to a list of rooms
        rooms.add(room1);
        rooms.add(room2);

        // Set the rooms in the hotel entity
        hotel.setRooms(rooms);

        // Persist the hotel entity (which will also persist the rooms)
        hotelDAO.create(hotel);
    }
}