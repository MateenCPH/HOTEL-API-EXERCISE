package dk.lyngby.config;


import dk.lyngby.controller.ExceptionController;
import dk.lyngby.exception.ApiException;
import dk.lyngby.routes.Routes;
import dk.lyngby.util.ApiProps;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AppConfig
{

    private static final Routes routes = new Routes();
    private static final ExceptionController exceptionController = new ExceptionController();

    private static void configuration(JavalinConfig config)
    {
        // == Server ==
        config.router.contextPath = ApiProps.API_CONTEXT;

        // == Plugins ==
        config.bundledPlugins.enableRouteOverview("/routes"); // Enable route overview
        config.bundledPlugins.enableDevLogging(); // Enable development logging

        // == Routes ==
        config.router.apiBuilder(routes.getApiRoutes());
    }

    // == Exception ==

    public static void exceptionHandler(Javalin app)
    {
         app.exception(ApiException.class, exceptionController::apiExceptionHandler);


    }

    // == Start server ==
    public static void startServer()
    {
        var app = Javalin.create(AppConfig::configuration);
        exceptionHandler(app);
        app.start(ApiProps.PORT);
    }
}