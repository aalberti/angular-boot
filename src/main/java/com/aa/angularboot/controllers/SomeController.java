package com.aa.angularboot.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/stats")
    public Collection<Stat> statsGroupedBy(@RequestParam(value="column") String columnName) throws SQLException{
        ArrayList<Stat> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:us-census.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select \"" + columnName + "\", avg(age) as avgAge, count(age) as count "
                + "from census_learn_sql "
                + "group by \"" + columnName + "\" "
                + "order by count desc "
                + "limit 100");
            while (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0)
                    result.add(new Stat(resultSet.getString(columnName), resultSet.getFloat("avgAge"), count));
            }
        }
        return result;
    }

    @SuppressWarnings("unused")
    public static class Stat
    {
        private String classifier;
        private float  avgAge;
        private int    count;

        private Stat(String classifier, float avgAge, int count)
        {
            this.classifier = classifier;
            this.avgAge = avgAge;
            this.count = count;
        }

        public String getClassifier()
        {
            return classifier;
        }

        public void setClassifier(String classifier)
        {
            this.classifier = classifier;
        }

        public float getAvgAge()
        {
            return avgAge;
        }

        public void setAvgAge(float avgAge)
        {
            this.avgAge = avgAge;
        }

        public int getCount()
        {
            return count;
        }

        public void setCount(int count)
        {
            this.count = count;
        }
    }
}
