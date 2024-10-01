package dk.lyngby.model;

import dk.lyngby.dto.RoomDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @ToString.Exclude
    private Hotel hotel; // This is a foreign key
    private int roomNumber;
    private double roomPrice;

    public Room convertToRoomEntity(RoomDTO roomDTO) {
        return Room.builder()
                .roomId(roomDTO.getRoomId())
                //.hotel(null) // Set the hotel entity to null
                .roomNumber(roomDTO.getRoomNumber())
                .roomPrice(roomDTO.getRoomPrice())
                .build();
    }
}
