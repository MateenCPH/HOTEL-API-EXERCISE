package dk.lyngby.controller;

import dk.lyngby.dao.DogDaoImpl;
import dk.lyngby.dto.DogDto;
import dk.lyngby.exception.ApiException;
import dk.lyngby.model.Dog;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DogControllerImpl implements DogController
{
    private final Logger log = LoggerFactory.getLogger(DogControllerImpl.class);
    private final DogDaoImpl dogDaoImpl;

    public DogControllerImpl(DogDaoImpl dogDaoImpl)
    {
        this.dogDaoImpl = dogDaoImpl;
    }

    @Override
    public void getDogById(Context ctx)
    {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));

        // == querying ==
        Dog dog = dogDaoImpl.getDogById(id);

        // == response ==
        DogDto dogDto = new DogDto(dog);
        ctx.res().setStatus(200);
        ctx.json(dogDto, DogDto.class);
    }

    @Override
    public void getAllDogs(Context ctx)
    {
        // == querying ==
        List<Dog> dogs = dogDaoImpl.getAllDogs();

        // == response ==
        List<DogDto> dogDtos = DogDto.toDogDTOList(dogs);
        ctx.res().setStatus(200);
        ctx.json(dogDtos, DogDto.class);
    }

    @Override
    public void createDog(Context ctx)
    {
        try
        {
            // == request ==
            DogDto dogDto = ctx.bodyAsClass(DogDto.class);

            // == querying ==
            Dog newDog = new Dog(dogDto);
            dogDaoImpl.createDog(newDog);

            // == response ==
            ctx.res().setStatus(201);
            ctx.result("Dog created");
        } catch (Exception e)
        {
            // Vi logger en fejl, hvis der er en fejl
            log.error("400 {} ", e.getMessage());

            // Vi kaster vores egen exception, som vi har lavet i ApiException.java
            throw new ApiException(400, e.getMessage());
        }
    }

    @Override
    public void updateDog(Context ctx)
    {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));
        DogDto dogDto = ctx.bodyAsClass(DogDto.class);

        // == querying ==
        Dog dog = dogDaoImpl.getDogById(id);
        dogDaoImpl.updateDog(dog, new Dog(dogDto));

        // == response ==
        ctx.res().setStatus(200);
    }

    @Override
    public void deleteDog(Context ctx)
    {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));

        // == querying ==
        Dog dog = dogDaoImpl.getDogById(id);

        // == response ==
        dogDaoImpl.deleteDog(id);
        ctx.res().setStatus(204);
    }
}
