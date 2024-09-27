package dk.lyngby.dao;

import dk.lyngby.model.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomDAO implements IDAO<Room> {

    private final EntityManagerFactory emf;

    public RoomDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Room create(Room room) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(room);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while saving room");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Room update(Room room) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Room foundRoom = em.find(Room.class, room.getRoomId());

            if (room.getRoomNumber() != 0) {
                foundRoom.setRoomNumber(room.getRoomNumber());
            }
            if (room.getRoomPrice() != 0) {
                foundRoom.setRoomPrice(room.getRoomPrice());
            }
            if (room.getHotel() != null) {
                foundRoom.setHotel(room.getHotel());
            }
            em.merge(foundRoom);
            em.getTransaction().commit();
            return foundRoom;
        }
    }

    @Override
    public void delete(Room roomDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Room room = em.find(Room.class, roomDTO.getRoomId());
            em.remove(room);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Error while deleting room");
            e.printStackTrace();
        }
    }

    @Override
    public Room getById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Room room = em.find(Room.class, id);
            return room;
        } catch (PersistenceException e) {
            System.out.println("Error while getting room by id: " + id);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Room> rooms = em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
            return rooms;
        } catch (PersistenceException e) {
            System.out.println("Error while getting all rooms");
            e.printStackTrace();
        }
        return null;
    }
}

