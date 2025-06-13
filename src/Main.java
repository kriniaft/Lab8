import database.DatabaseConnector;
import gui.AuthView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database connector
            DatabaseConnector db = new DatabaseConnector();
            db.connect();
            
            // Create and initialize auth view
            AuthView authView = new AuthView(primaryStage, db);
            authView.initialize();
            authView.show();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
