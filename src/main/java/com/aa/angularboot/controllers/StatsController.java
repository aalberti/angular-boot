package com.aa.angularboot.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.aa.angularboot.StatsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class StatsController {
    @Autowired
    private StatsConfig config;

    @RequestMapping(value = "/fields", method = RequestMethod.GET)
    public Collection<String> fields() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + config.getTable() + " LIMIT 1");
            ResultSetMetaData metaData = resultSet.getMetaData();
            Collection<String> columnNames = new ArrayList<>(metaData.getColumnCount());
            for (int i = 0; i < metaData.getColumnCount(); i++)
                columnNames.add(metaData.getColumnName(i + 1));
            return columnNames;
        }
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public Stats statsGroupedBy(@RequestParam(value = "column") String columnName) throws SQLException {
        ArrayList<StatsRow> rows = new ArrayList<>();
        int returnedCount = 0;
        int total = 0;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String rowsQuery = "SELECT  \"" + columnName + "\", AVG(age) as avgAge, COUNT(age) as count "
                    + "FROM " + config.getTable() + " "
                    + "GROUP BY\"" + columnName + "\" "
                    + "HAVING count > 0";
            try (ResultSet rowsSet = statement.executeQuery(rowsQuery + " ORDER BY count DESC LIMIT 100")) {
                while (rowsSet.next()) {
                    rows.add(new StatsRow(rowsSet.getString(columnName), rowsSet.getFloat("avgAge"), rowsSet.getInt("count")));
                    returnedCount++;
                }
            }
            try (ResultSet totalSet = statement.executeQuery("SELECT COUNT(*) as total FROM (" + rowsQuery + ")")) {
                if (totalSet.next())
                    total = totalSet.getInt("total");
            }
        }
        return new Stats(rows, returnedCount, total);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + config.getFile());
    }

    @SuppressWarnings("unused")
    public static class Stats {
        private Collection<StatsRow> rows;
        private int returnedCount;
        private int totalCount;

        private Stats(Collection<StatsRow> rows, int returnedCount, int totalCount) {
            this.rows = rows;
            this.returnedCount = returnedCount;
            this.totalCount = totalCount;
        }

        public Collection<StatsRow> getRows() {
            return rows;
        }

        public void setRows(Collection<StatsRow> rows) {
            this.rows = rows;
        }

        public int getReturnedCount() {
            return returnedCount;
        }

        public void setReturnedCount(int returnedCount) {
            this.returnedCount = returnedCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    @SuppressWarnings("unused")
    public static class StatsRow {
        private String classifier;
        private float avgAge;
        private int count;

        private StatsRow(String classifier, float avgAge, int count) {
            this.classifier = classifier;
            this.avgAge = avgAge;
            this.count = count;
        }

        public String getClassifier() {
            return classifier;
        }

        public void setClassifier(String classifier) {
            this.classifier = classifier;
        }

        public float getAvgAge() {
            return avgAge;
        }

        public void setAvgAge(float avgAge) {
            this.avgAge = avgAge;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
