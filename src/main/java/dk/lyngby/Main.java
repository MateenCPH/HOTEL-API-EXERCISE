package dk.lyngby;

import dk.lyngby.config.AppConfig;
import dk.lyngby.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("hotel");
    private static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        AppConfig.startServer(7777, emf);
    }
}