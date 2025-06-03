package database;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import basic.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s467897";
    private static final String PASSWORD = "p0wZzpVdlHiU21tH";
    public static Connection connection;
    private static ScriptRunner runner;
    private String userNow;

    public void setUserNow(String username) {
        this.userNow = username;
    }

    public String getUserNow(){
        return userNow;
    }

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            runner = new ScriptRunner(connection);
            setScriptRunnerConfig();
        }catch(SQLException ex){
            System.out.println("Ошибка соединения");
        }
    }

    public Connection getConnection() {
        return connection;
    }


    public void create() {
        try (Reader fr = Files.newBufferedReader(
                Paths.get("src/database/createSQL"),
                StandardCharsets.UTF_8
        )) {
            runner.runScript(fr);
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом");;
        }
    }

    public ArrayDeque<Person> getPersons() {
        ArrayDeque<Person> persons = new ArrayDeque<>();
        String sql = "SELECT id, name, coord_x, coord_y, creation_date, height, passport_id, hair_color, nationality, locat_x, locat_y, locat_z FROM person";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Person person = getPersonBy(rs);
                persons.add(person);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении скрипта SQL");;
        }
        return persons;
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
                LocalDateTime created = rs.getTimestamp("created_at").toLocalDateTime();
                users.put(login, new Users(id, login, hash, created));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка  в сохранении пользователя");
        }
        return users;
    }

    public ArrayDeque<Person> loadPersonsByUser(String username) throws SQLException {
        ArrayDeque<Person> people = new ArrayDeque<>();
        String sql = "SELECT * FROM person WHERE username = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Person person = getPersonBy(rs);
                    people.add(person);
                }
            }
        }
        return people;
    }

    public Person getPersonBy(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");

        Coordinates coords = new Coordinates();
        coords.setX(rs.getFloat("coord_x"));
        coords.setY(rs.getFloat("coord_y"));

        Color hairColor = null;
        try {
            String hairColorStr = rs.getString("hair_color");
            if (hairColorStr != null) {
                hairColor = Color.valueOf(hairColorStr);
            }
        } catch (IllegalArgumentException e) {
            hairColor = null;
        }

        Country nationality = null;
        try {
            String nationalityStr = rs.getString("nationality");
            if (nationalityStr != null) {
                nationality = Country.valueOf(nationalityStr);
            }
        } catch (IllegalArgumentException e) {
            nationality = null;
        }

        Float height = rs.getObject("height") != null ? rs.getFloat("height") : null;
        String passportID = rs.getString("passport_id");

        Location location = new Location();
        location.setX(rs.getFloat("locat_x"));
        location.setY(rs.getFloat("locat_y"));
        location.setZ(rs.getFloat("locat_z"));

        Timestamp ts = rs.getTimestamp("creation_date");
        ZonedDateTime creationDate;
        if (ts != null) {
            creationDate = ts.toInstant().atZone(nationality != null ? nationality.getZoneId() : ZoneId.systemDefault());
        } else {
            creationDate = nationality != null ? ZonedDateTime.now(nationality.getZoneId()) : ZonedDateTime.now();
        }

        return new Person(id, name, coords, height, passportID, hairColor, nationality, location, creationDate);
    }


    public void registration(String username, String password) {
        try {
            connection.setAutoCommit(false);
            if (getUsers().containsKey(username)) {
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
            System.out.println("Ошибка регистрации");
        }
    }

    public boolean login(String username, String password){
        boolean isLogin = false;
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT password_hash FROM users WHERE login = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String stored = rs.getString("password_hash");
                        isLogin = stored.equals(hashPassword(password));
                    }
                }
            }
        }catch (SQLException e) {
                System.out.println("Ошибка при входе");
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        if (!isLogin) System.out.println("Неверный логин или пароль");
        return isLogin;
    }

    public boolean addPerson(Person person) {
        return addPerson(person, userNow);
    }

    public boolean addPerson(Person person, String username) {
        boolean condition = false;
        if (!getUsers().containsKey(username)) {
            System.out.println("Вам необходимо зарегистрироваться");
            return false;
        }
        try {
            connection.setAutoCommit(false);
            int id = minId();
            String sql = "INSERT INTO music_bands "
                    + "(id,name,coord_x,coord_y,number_of_participants,genre,studio_name,created_by) "
                    + "VALUES (?, ?, ?, ?, ?, ?::music_genre, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, id);
                ps.setString(2, person.getName());
                ps.setFloat(3, person.getCoordinates().getX());
                ps.setFloat(4, person.getCoordinates().getY());
                ps.setString(5, String.valueOf(person.getCreationDate()));
                ps.setFloat(6, person.getHeight());
                ps.setString(7, person.getPassportID());
                ps.setString(8, String.valueOf(person.getHairColor()));
                ps.setString(9, String.valueOf(person.getNationality()));
                ps.setFloat(10, person.getLocation().getX());
                ps.setFloat(11, person.getLocation().getY());
                ps.setFloat(12, person.getLocation().getZ());
                ps.setString(13, username);
                if (ps.executeUpdate() == 0) throw new SQLException();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) person.setId(keys.getInt(1));
                    else throw new SQLException();
                }
            }
            connection.commit();
            condition = true;
        } catch (SQLException e) {
            System.out.println("Не получилось добавить человека");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return condition;
    }

    public int minId() throws SQLException {
        String sql = "SELECT id FROM person ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int number = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                if (id == number) {
                    number++;
                } else if (id > number) {
                    break;
                }
            }
            return number;
        }
    }

    private String hashPassword(String pw){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hashBytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
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

