package database;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://pg:5432/studs";
    private static final String USER = "s467897";
    private static final String PASSWORD = "p0wZzpVdlHiU21tH";
    private static ScriptRunner runner;

    public void connect() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
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

    private void setScriptRunnerConfig() {
        runner.setStopOnError(true);
        runner.setAutoCommit(false);
        runner.setLogWriter(null);
        runner.setSendFullScript(false);
    }
}
}
