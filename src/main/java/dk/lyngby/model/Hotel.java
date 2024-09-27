package dk.lyngby.model;

import dk.lyngby.dto.HotelDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String hotelName;
    private String hotelAddress;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;

    public Hotel convertToHotelEntity(HotelDTO hotelDTO, List<Room> rooms) {
        return Hotel.builder()
                .hotelId(hotelDTO.getHotelId())
                .hotelName(hotelDTO.getHotelName())
                .hotelAddress(hotelDTO.getHotelAddress())
                .rooms(rooms)
                .build();
    }
}
