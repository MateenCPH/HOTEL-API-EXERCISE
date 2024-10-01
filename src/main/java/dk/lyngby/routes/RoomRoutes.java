package dk.lyngby.routes;

import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.RoomController;
import dk.lyngby.dao.RoomDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class RoomRoutes {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final RoomDAO roomDAO = new RoomDAO(emf);
    private final RoomController roomController = new RoomController(roomDAO);

    public EndpointGroup getRoomRoutes()
    {
        return () ->
        {
            get("/", roomController::getAllRooms);
            get("/{id}", roomController::getRoomById);
            post("/", roomController::createRoom);
            put("/{id}", roomController::updateRoom);
            delete("/{id}", roomController::deleteRoom);
        };
    }
}