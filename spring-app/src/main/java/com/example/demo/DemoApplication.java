package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
@RestController
public class DemoApplication {

    @GetMapping("/")
    public String getPostgresTime() {
        String result = "";
        try {
            String url = System.getenv("SPRING_DATASOURCE_URL");
            String user = System.getenv("SPRING_DATASOURCE_USERNAME");
            String password = System.getenv("SPRING_DATASOURCE_PASSWORD");

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NOW()");
            if (rs.next()) {
                result = rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        return "Postgres time: " + result;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
