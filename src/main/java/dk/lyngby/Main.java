package dk.lyngby;

import dk.lyngby.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        AppConfig.startServer();
    }
}