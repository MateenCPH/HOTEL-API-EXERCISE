package dk.lyngby.controller;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.dao.HotelDAO;
import dk.lyngby.dao.RoomDAO;
import dk.lyngby.dto.RoomDTO;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoomController {

    private final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomDAO roomDAO;

    public RoomController(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public void getAllRooms(Context ctx) {
        List<Room> rooms = roomDAO.getAll();
        List<RoomDTO> roomDtos = RoomDTO.toRoomDtoList(rooms);
        ctx.status(200);
        ctx.json(roomDtos, RoomDTO.class);
    }

    public void getRoomById(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        Room room = roomDAO.getById(id);

        RoomDTO roomDto = new RoomDTO().convertToRoomDTO(room);

        ctx.res().setStatus(200);
        ctx.json(roomDto, RoomDTO.class);
    }

    public void createRoom(Context ctx) {
        HotelDAO hotelDAO = new HotelDAO(HibernateConfig.getEntityManagerFactory());

        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
        Room room = new Room().convertToRoomEntity(roomDTO);
        room.setHotel(hotelDAO.getById(roomDTO.getHotelId()));
        roomDAO.create(room);
        ctx.status(201);
        ctx.json(new RoomDTO().convertToRoomDTO(room), RoomDTO.class);
    }

    public void updateRoom(Context ctx) {
        HotelDAO hotelDAO = new HotelDAO(HibernateConfig.getEntityManagerFactory());

        Long roomId = Long.parseLong(ctx.pathParam("id"));
        RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);

        Room room = roomDAO.getById(roomId);
        Hotel hotel = hotelDAO.getById(roomDTO.getHotelId());

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomPrice(roomDTO.getRoomPrice());
        room.setHotel(hotel);
        roomDAO.update(room);

        RoomDTO updatedRoomDTO = new RoomDTO().convertToRoomDTO(room);
        ctx.status(200);
        ctx.json(updatedRoomDTO, RoomDTO.class);
    }

    public void deleteRoom(Context ctx) {
        HotelDAO hotelDAO = new HotelDAO(HibernateConfig.getEntityManagerFactory());

        Long id = Long.parseLong(ctx.pathParam("id"));
        Room room = roomDAO.getById(id);

        if (room != null) {
            Hotel hotel = room.getHotel();

            if (hotel != null) {
                hotel.getRooms().remove(room);
                hotelDAO.update(hotel);
            }
            roomDAO.delete(room);

            ctx.status(204);
            ctx.json(new RoomDTO().convertToRoomDTO(room), RoomDTO.class);
        } else {
            ctx.status(404).result("Room not found");
        }
    }

}
