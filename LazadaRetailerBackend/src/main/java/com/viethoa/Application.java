package com.viethoa;

import com.viethoa.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

/**
 * Created by VietHoa on 16/01/2017.
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            log.info("Creating tables");
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
