package dk.lyngby.model;

import dk.lyngby.dto.DogDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dogs")
@ToString
@NamedQuery(name = "dog.deleteAllRows", query = "DELETE from Dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private long dog_id;

    @Column(name = "dog_name", nullable = false)
    private String dogName;

    @Column(name = "dog_age", nullable = false)
    private int dogAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "dog_breed", nullable = false)
    private Breed dogBreed;

    @Enumerated(EnumType.STRING)
    @Column(name = "dog_gender", nullable = false)
    private Gender dogGender;

    public Dog(String dogName, int dogAge, Breed dogBreed, Gender dogGender) {
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogBreed = dogBreed;
        this.dogGender = dogGender;
    }

    public Dog(DogDto dogDto) {
        this.dogName = dogDto.getDogName();
        this.dogAge = dogDto.getDogAge();
        this.dogBreed = dogDto.getDogBreed();
        this.dogGender = dogDto.getDogGender();
    }
}
