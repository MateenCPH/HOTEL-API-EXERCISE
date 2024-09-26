package dk.lyngby.dto;

import dk.lyngby.model.Breed;
import dk.lyngby.model.Dog;
import dk.lyngby.model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DogDto {

    private String dogName;
    private int dogAge;
    private Breed dogBreed;
    private Gender dogGender;

    public DogDto(Dog dog) {
        this.dogName = dog.getDogName();
        this.dogAge = dog.getDogAge();
        this.dogBreed = dog.getDogBreed();
        this.dogGender = dog.getDogGender();
    }

    public static List<DogDto> toDogDTOList(List<Dog> dogs) {
        return dogs.stream().map(DogDto::new).toList();
    }
}
