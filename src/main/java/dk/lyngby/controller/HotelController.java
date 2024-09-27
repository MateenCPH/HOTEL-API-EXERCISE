package dk.lyngby.controller;

import dk.lyngby.dao.HotelDAO;
import dk.lyngby.dto.HotelDTO;
import dk.lyngby.model.Hotel;
import io.javalin.http.Context;
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

    public void getHotelById(Context ctx) {

        long id = Long.parseLong(ctx.pathParam("id"));

        Hotel hotel = hotelDAO.getById(id);

        HotelDTO hotelDto = new HotelDTO(hotel);

        ctx.res().setStatus(200);
        ctx.json(hotelDto, HotelDTO.class);
    }

    public void getAllHotels(Context ctx){

            List<Hotel> hotels = hotelDAO.getAll();

            List<HotelDTO> hotelDtos = HotelDTO.toHotelDtoList(hotels);

            ctx.res().setStatus(200);
            ctx.json(hotelDtos, HotelDTO.class);
    }

    public void createHotel(Context ctx) {
        HotelDTO hotelDTO = ctx.bodyAsClass(HotelDTO.class);
        ArrayList<Room>

        newHotel.convertToHotelEntity(hotelDTO);

    }
}
