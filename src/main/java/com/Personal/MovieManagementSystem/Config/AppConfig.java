package com.Personal.MovieManagementSystem.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class AppConfig {

    @Value("${mysql.uri}")
    private String uri;
    @Value("${mysql.username}")
    private String userName;
    @Value("${mysql.password}")
    private String password;
    @Bean("MySqlConnection")
    @Primary
    public Connection getMySQLConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(uri, userName, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
