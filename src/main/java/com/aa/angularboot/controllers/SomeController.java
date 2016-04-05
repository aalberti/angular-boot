package com.aa.angularboot.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    @RequestMapping("/fields")
    public String[] fields() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:us-census.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from census_learn_sql limit 1");
            ResultSetMetaData metaData = resultSet.getMetaData();
            String[] columnNames = new String[metaData.getColumnCount()];
            for (int i = 0; i < columnNames.length; i++)
                columnNames[i] = metaData.getColumnName(i + 1);
            return columnNames;
        }
    }
}
