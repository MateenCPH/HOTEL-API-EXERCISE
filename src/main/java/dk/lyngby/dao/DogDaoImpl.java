package dk.lyngby.dao;

import dk.lyngby.model.Dog;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DogDaoImpl implements DogDao
{

    private final EntityManagerFactory emf;

    public DogDaoImpl(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public List<Dog> getAllDogs()
    {
        try (var em = emf.createEntityManager())
        {
            return em.createQuery("SELECT d FROM Dog d", Dog.class).getResultList();
        }
    }

    @Override
    public Dog getDogById(long id)
    {
        try (var em = emf.createEntityManager())
        {
            return em.find(Dog.class, id);
        }
    }

    @Override
    public void createDog(Dog dog)
    {
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(dog);
            em.getTransaction().commit();
        }
    }

    @Override
    public void updateDog(Dog dog, Dog updateDog)
    {
        System.out.println(updateDog);
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            dog.setDogName(updateDog.getDogName());
            dog.setDogBreed(updateDog.getDogBreed());
            dog.setDogAge(updateDog.getDogAge());
            dog.setDogGender(updateDog.getDogGender());
            em.merge(dog);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteDog(long id)
    {
        try (var em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            em.remove(dog);
            em.getTransaction().commit();
        }
    }
}
