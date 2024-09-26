package dk.lyngby.dao;

import dk.lyngby.model.Dog;

import java.util.List;

public interface DogDao {
    public List<Dog> getAllDogs();
    public Dog getDogById(long id);
    public void createDog(Dog dog);
    public void updateDog(Dog dog, Dog updateDog);
    public void deleteDog(long id);
}
