package dk.lyngby.routes;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.DogControllerImpl;
import dk.lyngby.controller.HotelController;
import dk.lyngby.dao.DogDaoImpl;
import dk.lyngby.dao.HotelDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.put;

public class HotelRoutes {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final HotelDAO hotelDAO = new HotelDAO(emf);
    private final HotelController hotelController = new HotelController(hotelDAO);

    public EndpointGroup getHotelRoutes()
    {
        return () ->
        {
//            get("/dog", dogController::getAllDogs);
//            get("/dog/{id}", dogController::getDogById);
//            post("/dog", dogController::createDog);
//            delete("/dog/{id}", dogController::deleteDog);
//            put("/dog/{id}", dogController::updateDog);

            get("/", hotelController::getAllHotels);
            get("/{id}", hotelController::getHotelById);
            post("/", hotelController::createHotel);
            //delete("/{id}", dogController::deleteDog);
            //put("/{id}", dogController::updateDog);
        };
    }
}
