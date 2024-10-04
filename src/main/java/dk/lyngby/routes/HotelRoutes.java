package dk.lyngby.routes;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.HotelController;
import dk.lyngby.dao.HotelDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.put;

public class HotelRoutes {

    private final HotelController hotelController;

    public HotelRoutes(EntityManagerFactory emf) {
        this.hotelController = new HotelController(new HotelDAO(emf));
    }

    public EndpointGroup getHotelRoutes()
    {
        return () ->
        {
            get("/", hotelController::getAllHotels);
            get("/{id}", hotelController::getHotelById);
            get("/{id}/rooms", hotelController::getRoomsByHotelId);
            put("/{id}", hotelController::updateHotel);
            post("/", hotelController::createHotel);
            delete("/{id}", hotelController::deleteHotel);
        };
    }
}