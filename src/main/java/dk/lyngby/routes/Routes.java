package dk.lyngby.routes;


import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
private HotelRoutes hotelRoutes = new HotelRoutes();
private RoomRoutes roomRoutes = new RoomRoutes();

    public EndpointGroup getApiRoutes() {
        return () -> {
            path("/hotel", hotelRoutes.getHotelRoutes());
            path("/room", roomRoutes.getRoomRoutes());
        };
    }
}
