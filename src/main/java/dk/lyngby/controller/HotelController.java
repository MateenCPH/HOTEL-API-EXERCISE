package dk.lyngby.controller;

import dk.lyngby.dao.HotelDAO;
import dk.lyngby.dto.HotelDTO;
import dk.lyngby.dto.RoomDTO;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HotelController {

    private final Logger log = LoggerFactory.getLogger(HotelController.class);
    private final HotelDAO hotelDAO;

    public HotelController(HotelDAO hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    public void getAllHotels(Context ctx){

            List<Hotel> hotels = hotelDAO.getAll();

            List<HotelDTO> hotelDtos = HotelDTO.toHotelDtoList(hotels);

            ctx.status(200);
            ctx.json(hotelDtos, HotelDTO.class);
    }

    public void getHotelById(Context ctx) {

        long id = Long.parseLong(ctx.pathParam("id"));

        Hotel hotel = hotelDAO.getById(id);

        HotelDTO hotelDto = new HotelDTO(hotel);

        ctx.res().setStatus(200);
        ctx.json(hotelDto, HotelDTO.class);
    }

    public void createHotel(Context ctx) {
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        List<RoomDTO> roomDTOs = hotelDTO.getRooms();

        Hotel hotel = new Hotel().convertToHotelEntity(hotelDTO);

        List<Room> rooms = new ArrayList<>();
        for (RoomDTO roomDTO : roomDTOs) {
            Room room = new Room().convertToRoomEntity(roomDTO);
            room.setHotel(hotel);
            rooms.add(room);
        }
        hotel.setRooms(rooms);
        hotelDAO.create(hotel);
        ctx.status(201);
        HotelDTO newHotelDTO = new HotelDTO().convertToHotelDTO(hotel);
        ctx.json(newHotelDTO, HotelDTO.class);
    }

    public void getRoomsByHotelId(Context ctx) {
        long id = Long.parseLong(ctx.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        List<Room> rooms = hotel.getRooms();
        List<RoomDTO> roomDtos = RoomDTO.toRoomDtoList(rooms);
        ctx.res().setStatus(200);
        ctx.json(roomDtos, RoomDTO.class);

    }

    public void updateHotel(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);

        Hotel hotel = hotelDAO.getById(id);

        hotel.setHotelName(hotelDTO.getHotelName());
        hotel.setHotelAddress(hotelDTO.getHotelAddress());
        Hotel updatedHotel = hotelDAO.update(hotel);

        HotelDTO updatedHotelDTO = new HotelDTO().convertToHotelDTO(updatedHotel);

        ctx.status(200);
        ctx.json(updatedHotelDTO, HotelDTO.class);
    }

    public void deleteHotel(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Hotel hotel = hotelDAO.getById(id);
        HotelDTO hotelDTO = new HotelDTO().convertToHotelDTO(hotel);
        hotelDAO.delete(hotel);

        ctx.status(204);
        ctx.json(hotelDTO, HotelDTO.class);
    }
}