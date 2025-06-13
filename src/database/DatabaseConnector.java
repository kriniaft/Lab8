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
    private static final String URL = "jdbc:postgresql://localhost:5432/studs";
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

    public void connect(){
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(SQLException ex){
            System.out.println("Ошибка соединения");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ArrayDeque<Person> getPersons() throws SQLException {
        ArrayDeque<Person> persons = new ArrayDeque<>();
        String query = "SELECT * FROM person";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                float coordX = rs.getFloat("coord_x");
                float coordY = rs.getFloat("coord_y");
                float height = rs.getFloat("height");
                String passportID = rs.getString("passport_id");
                String hairColor = rs.getString("hair_color");
                String nationality = rs.getString("nationality");
                float locX = rs.getFloat("locat_x");
                float locY = rs.getFloat("locat_y");
                float locZ = rs.getFloat("locat_z");
                String creator = rs.getString("username");
                ZonedDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime().atZone(ZoneId.systemDefault());

                Coordinates coordinates = new Coordinates(coordX, coordY);
                Location location = new Location(locX, locY, locZ);
                Person person = new Person(id, name, coordinates, height, passportID, Color.valueOf(hairColor), Country.valueOf(nationality), location, creationDate);
                person.setCreator(creator);
                persons.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
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

        Person person = new Person(id, name, coords, height, passportID, hairColor, nationality, location, creationDate);
        person.setCreator(rs.getString("username"));
        return person;
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
            String sql = "INSERT INTO person "
                    + "(id,name,coord_x,coord_y,creation_date,height,passport_id,hair_color, nationality, locat_x, locat_y, locat_z, username) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?::color, ?::country, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, id);
                ps.setString(2, person.getName());
                ps.setFloat(3, person.getCoordinates().getX());
                ps.setFloat(4, person.getCoordinates().getY());
                ps.setTimestamp(5, Timestamp.from(person.getCreationDate().toInstant()));
                ps.setFloat(6, person.getHeight());
                ps.setString(7, person.getPassportID());
                ps.setString(8, person.getHairColor().name());
                ps.setString(9, person.getNationality().name());
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
            e.printStackTrace();
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

    public boolean updateByUd(float h, long id){

        String sql = "UPDATE person SET height = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setFloat(1, h);
            ps.setLong(2, id);
            int affected = ps.executeUpdate();

            if (affected == 0) {
                System.out.println("Не удалось изменить рост: элемент с таким ID не найден в базе данных");
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении базы данных: " + e.getMessage());
        }
        return false;
    }

    public boolean deletePerson(Person person) throws SQLException {
        String sql = "DELETE FROM person WHERE id = ? AND username = ?";
        long id = person.getId();
        String creator = person.getCreator();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.setString(2, creator);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public boolean removePersonById(long id) throws SQLException {
        String sql = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public void clearPersonsByUser(String username) throws SQLException {
        String sql = "DELETE FROM person WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
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

}

