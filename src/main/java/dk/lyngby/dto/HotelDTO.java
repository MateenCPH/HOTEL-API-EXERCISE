package dk.lyngby.dto;

import dk.lyngby.model.Dog;
import dk.lyngby.model.Hotel;
import dk.lyngby.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class HotelDTO {

    private Long hotelId;
    private String hotelName;
    private String hotelAddress;
    private List<RoomDTO> rooms;

    public HotelDTO(Hotel hotel) {
        List<RoomDTO> roomDTOs = hotel.getRooms().stream().map(RoomDTO::new).collect(Collectors.toList());
        this.hotelId = hotel.getHotelId();
        this.hotelName = hotel.getHotelName();
        this.hotelAddress = hotel.getHotelAddress();
        this.rooms = roomDTOs;
    }

    public RoomDTO convertToRoomDTO(Room room) {
        return new RoomDTO(
                room.getRoomId(),
                room.getHotel().getHotelId(),
                room.getRoomNumber(),
                room.getRoomPrice()
        );
    }

    public HotelDTO convertToHotelDTO(Hotel hotel) {
        List<RoomDTO> roomDTOs = hotel.getRooms().stream()
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());

        return new HotelDTO(
                hotel.getHotelId(),
                hotel.getHotelName(),
                hotel.getHotelAddress(),
                roomDTOs
        );
    }

    public static List<HotelDTO> toHotelDtoList(List<Hotel> hotels) {
        return hotels.stream().map(HotelDTO::new).toList();
    }
}
