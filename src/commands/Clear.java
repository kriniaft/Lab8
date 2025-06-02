package commands;

import commands.base.Command;
import commands.base.Environment;
import database.DatabaseConnector;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class Clear extends Command {
    private Clear(){
        super("clear");
    }



    @Override
    public String getHelp(){
        return "Очищает коллекцию (удаляет все элементы)";
    }

    @Override
    public void execute(Environment env, InputStream sIn, PrintStream sOut, String[] commandsArgs, DatabaseConnector db) throws SQLException {
        if (env.getProfiles() == null || env.getProfiles().isEmpty()) {
            sOut.println("Коллекция пуста или не инициализирована");
            return;
        }
        try {
            Connection conn = db.getConnection();

            String deleteSQL = "DELETE FROM person WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
                ps.setString(1, db.getUserNow());
                int deletedCount = ps.executeUpdate();
                sOut.println("Удалено записей из базы: " + deletedCount);
            }

            env.getProfiles().clear();
            env.setProfiles(db.getPersons());
            sOut.println("Коллекция очищена");

        } catch (SQLException e) {
            sOut.println("Ошибка при очистке: " + e.getMessage());
        }catch(Exception e){
            System.out.println("Ошибка при удалении коллекции");
        }

    }

    public static void register(HashMap<String, Command> stringCommandHashMap) {
        Clear clear = new Clear();
        stringCommandHashMap.put(clear.getName(), clear);
    }

    public static void regForEveryone(HashMap<String, Command> mapForEveryone) {
        Clear clear = new Clear();
        mapForEveryone.put(clear.getName(), clear);
    }


}
