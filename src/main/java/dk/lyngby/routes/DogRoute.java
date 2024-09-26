package dk.lyngby.routes;


import dk.lyngby.config.HibernateConfig;
import dk.lyngby.controller.DogControllerImpl;
import dk.lyngby.dao.DogDaoImpl;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;


public class DogRoute
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final DogDaoImpl dogDao = new DogDaoImpl(emf);
    private final DogControllerImpl dogController = new DogControllerImpl(dogDao);


    public EndpointGroup getDogRoutes()
    {
        return () ->
        {
//            get("/dog", dogController::getAllDogs);
//            get("/dog/{id}", dogController::getDogById);
//            post("/dog", dogController::createDog);
//            delete("/dog/{id}", dogController::deleteDog);
//            put("/dog/{id}", dogController::updateDog);

            get("/", dogController::getAllDogs);
            get("/{id}", dogController::getDogById);
            post("/", dogController::createDog);
            delete("/{id}", dogController::deleteDog);
            put("/{id}", dogController::updateDog);
        };
    }
}
