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

    public Room convertToRoomEntity(RoomDTO roomDTO, Hotel hotel) {
        return Room.builder()
                .roomId(roomDTO.getRoomId())
                .hotel(hotel) // Set the hotel entity
                .roomNumber(roomDTO.getRoomNumber())
                .roomPrice(roomDTO.getRoomPrice())
                .build();
    }
}
