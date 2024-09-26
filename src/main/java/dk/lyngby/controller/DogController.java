package dk.lyngby.controller;

import io.javalin.http.Context;

public interface DogController {
    public void getDogById(Context ctx);
    public void createDog(Context ctx);
    public void updateDog(Context ctx);
    public void deleteDog(Context ctx);
    public void getAllDogs(Context ctx);

}
