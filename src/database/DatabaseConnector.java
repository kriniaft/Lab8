package database;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import basic.Users;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s467897";
    private static final String PASSWORD = "p0wZzpVdlHiU21tH";
    private static Connection connection;
    private static ScriptRunner runner;

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            runner = new ScriptRunner(connection);
            setScriptRunnerConfig();
        }catch(SQLException ex){
            System.out.println("Ошибка соединения");
        }
    }

    public void create() {
        try (Reader fr = Files.newBufferedReader(
                Paths.get("src/database/createSQL"),
                StandardCharsets.UTF_8
        )) {
            runner.runScript(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Users> getUsers(){
    Map<String, Users> users = new HashMap<>();
        String sql = "SELECT id, login, password_hash, created_at FROM users";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String login = rs.getString("login");
                String hash = rs.getString("password_hash");
                LocalDateTime created = rs.getTimestamp("created_at")
                        .toLocalDateTime();
                users.put(
                        login,
                        new Users(id, login, hash, created)
                );
            }
        } catch (SQLException e) {
            System.out.println("Ошибка  в сохранении пользователя");
        }
        return users;
    }

    public void registration(String username, String password){
        try{
            connection.setAutoCommit(false);
            if(getUsers().containsKey(username)){
                System.out.println("Пользователь уже зарегистрирован");
            } else {
                String sql = "INSERT INTO users (login, password_hash) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, username);
                    ps.setString(2, hashPassword(password));
                    if (ps.executeUpdate() > 0) {
                        connection.commit();
                        System.out.println("Пользователь '" + username + "' успешно зарегистрирован.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    public HashSet<Person> getPersons() {
        HashSet<Person> persons = new HashSet<>();
        String sql = "SELECT * FROM person";
        try PreparedStatement ps = .prepareStatement(sql);

    }
}

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-384 not supported", e);
        }
    }



    private void setScriptRunnerConfig() {
        runner.setStopOnError(true);
        runner.setAutoCommit(false);
        runner.setLogWriter(null);
        runner.setSendFullScript(false);
    }
}

