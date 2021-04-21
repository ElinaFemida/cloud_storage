package db;

import javax.sql.DataSource;
import java.sql.*;

public class UsersDB {

    private static final String INSERT_USER = "insert into chatclients (NAME, PASSWORD) values (?, ?)";
    private static final String SELECT_USER_BY_NAME_AND_PASSWORD = "select * from chatclients where NAME = ? and PASSWORD = ?";


    public static void createUser(String userName, String plainUserPassword){
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER)){
            ps.setString(1, userName);
            ps.setString(2, plainUserPassword);
            ps.executeUpdate();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doesUserExist(String name, String pass){
        boolean isAuthorized = false;
        try(Connection connection = DataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_NAME_AND_PASSWORD)){
            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet resultSet = ps.executeQuery();
            isAuthorized = resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAuthorized;
    }
}