package com.jetbrains.marco;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class JdbcTutorial {

    public static void main(String[] args) {

        DataSource dataSource = createDatasource();

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';");
            System.out.println("connection.isValid(0) = " + connection.isValid(0));

            //CRUD

            //Select
            PreparedStatement selectPs = connection.prepareStatement("select * from USERS where name = ?");
            selectPs.setString(1, "Marco");

            ResultSet resultSet = selectPs.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

            //Insert
            PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPs.setString(1, "Alex");
            int insertCount = insertPs.executeUpdate();
            System.out.println("insertCount: " + insertCount);

            //Update
            PreparedStatement updatePS = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePS.setString(1, "Alexander");
            updatePS.setString(2, "Alex");
            int updateCount = updatePS.executeUpdate();
            System.out.println("updateCount: " + updateCount);

            //Delete
            PreparedStatement deletePs = connection.prepareStatement("delete from USERS where name = ?");
            deletePs.setString(1, "Alexander");
            int deleteCount = deletePs.executeUpdate();
            System.out.println("deleteCount: " + deleteCount);



        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//        DataSource dataSource = createDataSource();
//
//        try (Connection connection = dataSource.getConnection()) {
//            System.out.println("connection.isValid(0) = " + connection.isValid(0));
//
//            // CRUD
//
//            // select
//
//            PreparedStatement ps = connection.prepareStatement("select * from USERS where name = ?");
//            ps.setString(1, "Marco");
//
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
//            }
//
//            // inserts
//
//            PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
//            insertPs.setString(1, "John");
//            int insertCount = insertPs.executeUpdate();
//            System.out.println("insertCount = " + insertCount);
//
//            // updates
//
//            PreparedStatement updatePs = connection.prepareStatement("update USERS set name = ? where name = ?");
//            updatePs.setString(1, "Johnny");
//            updatePs.setString(2, "John");
//            int updateCount = updatePs.executeUpdate();
//            System.out.println("updateCount = " + updateCount);
//
//            // deletes
//
//            PreparedStatement deletePs = connection.prepareStatement("delete from USERS where name = ?");
//            deletePs.setString(1, "Johnny");
//            int deleteCount = deletePs.executeUpdate();
//            System.out.println("deleteCount = " + deleteCount);
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    private static DataSource createDatasource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql'");
        return ds;
    }
//
//    private static DataSource createDataSource() {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql'");
//        return ds;
//    }
}
