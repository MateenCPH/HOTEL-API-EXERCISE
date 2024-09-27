package dk.lyngby.dto;

import dk.lyngby.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomDTO {

    private Long roomId;
    private Long hotelId; // This is a foreign key
    private int roomNumber;
    private double roomPrice;

    public RoomDTO(Room room) {
        this.roomId = room.getRoomId();
        this.hotelId = room.getHotel().getHotelId();
        this.roomNumber = room.getRoomNumber();
        this.roomPrice = room.getRoomPrice();
    }

    public RoomDTO convertToRoomDTO(Room room) {
        return new RoomDTO(
                room.getRoomId(),
                room.getHotel().getHotelId(),
                room.getRoomNumber(),
                room.getRoomPrice()
        );
    }
}
