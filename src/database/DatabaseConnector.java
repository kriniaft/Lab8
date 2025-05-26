package database;
import basic.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s467897";
    private static final String PASSWORD = "p0wZzpVdlHiU21tH";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public HashSet<Person> getPersons() {
        HashSet<Person> persons = new HashSet<>();
        String sql = "SELECT * FROM person";
        try PreparedStatement ps = .prepareStatement(sql);

    }
}
