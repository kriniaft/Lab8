package gui;

import database.DatabaseConnector;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AuthView {
    private final Stage primaryStage;
    private final DatabaseConnector authService;
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private ComboBox<String> languageBox;
    private Text welcomeText;
    private Text errorText;
    private Button loginButton;
    private Button registerButton;
    private Button switchButton;
    private boolean isLoginMode = true;
    private boolean isUpdating = false;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label confirmPasswordLabel;
    private Label languageLabel;

    public AuthView(Stage primaryStage, DatabaseConnector authService) {
        this.primaryStage = primaryStage;
        this.authService = authService;
        initialize();
    }

    public void show() {
        primaryStage.show();
    }

    public void initialize() {
        // Initialize LanguageBundle
        LanguageBundle.initialize();

        // Create the main container
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Create all UI elements first
        welcomeText = new Text();
        welcomeText.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Create form elements
        GridPane grid = createForm();
        
        // Create language selection
        HBox languageBox = createLanguageBox();
        
        // Create error text
        errorText = new Text();
        errorText.setFill(javafx.scene.paint.Color.RED);
        
        // Create buttons
        HBox buttonBox = createButtonBox();

        // Add all elements to the root
        root.getChildren().addAll(
            welcomeText,
            languageBox,
            grid,
            errorText,
            buttonBox
        );

        // Set the scene
        primaryStage.setScene(new javafx.scene.Scene(root, 400, 500));
        primaryStage.setTitle(LanguageBundle.getString("app.title"));

        // Update texts with initial language
        updateTexts();
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Username
        usernameLabel = new Label(LanguageBundle.getString("login.username"));
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        // Password
        passwordLabel = new Label(LanguageBundle.getString("login.password"));
        passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        // Confirm Password (initially hidden)
        confirmPasswordLabel = new Label(LanguageBundle.getString("login.confirm_password"));
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        grid.add(confirmPasswordLabel, 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        return grid;
    }

    private HBox createLanguageBox() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        languageLabel = new Label(LanguageBundle.getString("language.select"));
        languageBox = new ComboBox<>();
        languageBox.getItems().addAll(
            LanguageBundle.getString("language.en"),
            LanguageBundle.getString("language.ru"),
            LanguageBundle.getString("language.sk"),
            LanguageBundle.getString("language.hu")
        );
        languageBox.setValue(LanguageBundle.getString("language.en"));

        languageBox.setOnAction(e -> {
            String selected = languageBox.getValue();
            if (selected != null) {
                changeLanguage(selected);
            }
        });

        box.getChildren().addAll(languageLabel, languageBox);
        return box;
    }

    private HBox createButtonBox() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);

        loginButton = new Button(LanguageBundle.getString("login.button"));
        registerButton = new Button(LanguageBundle.getString("register.button"));
        switchButton = new Button(LanguageBundle.getString("login.switch_to_register"));

        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister());
        switchButton.setOnAction(e -> switchMode());

        // Initially show only login button
        registerButton.setVisible(false);
        box.getChildren().addAll(loginButton, registerButton, switchButton);
        return box;
    }

    private void changeLanguage(String language) {
        if (isUpdating) return;
        try {
            isUpdating = true;
            String languageCode = "";
            switch (language) {
                case "English":
                case "Английский":
                case "Angličtina":
                case "Angol":
                    languageCode = "en";
                    LanguageBundle.setEnglish();
                    break;
                case "Русский":
                case "Russian":
                case "Ruština":
                case "Orosz":
                    languageCode = "ru";
                    LanguageBundle.setRussian();
                    break;
                case "Slovenčina":
                case "Slovak":
                case "Словацкий":
                case "Szlovák":
                    languageCode = "sk";
                    LanguageBundle.setSlovak();
                    break;
                case "Magyar":
                case "Hungarian":
                case "Maďarština":
                case "Венгерский":
                    languageCode = "hu";
                    LanguageBundle.setHungarian();
                    break;
            }
            
            // Update texts without triggering language box update
            updateTextsWithoutLanguageBox();
            
            // Update language box separately
            updateLanguageBox(languageCode);
            
        } catch (Exception e) {
            System.err.println("Error changing language: " + e.getMessage());
        } finally {
            isUpdating = false;
        }
    }

    private void updateTextsWithoutLanguageBox() {
        // Update welcome text
        welcomeText.setText(LanguageBundle.getString("login.welcome"));

        // Update form labels
        usernameLabel.setText(LanguageBundle.getString("login.username"));
        passwordLabel.setText(LanguageBundle.getString("login.password"));
        confirmPasswordLabel.setText(LanguageBundle.getString("login.confirm_password"));
        languageLabel.setText(LanguageBundle.getString("language.select"));

        // Update buttons
        loginButton.setText(LanguageBundle.getString("login.button"));
        registerButton.setText(LanguageBundle.getString("register.button"));
        switchButton.setText(isLoginMode ? 
            LanguageBundle.getString("login.switch_to_register") : 
            LanguageBundle.getString("login.switch_to_login"));

        // Update window title
        primaryStage.setTitle(LanguageBundle.getString("app.title"));
    }

    private void updateLanguageBox(String languageCode) {
        // Temporarily remove the action listener
        EventHandler<ActionEvent> oldHandler = languageBox.getOnAction();
        languageBox.setOnAction(null);

        // Update the items
        languageBox.getItems().setAll(
            LanguageBundle.getString("language.en"),
            LanguageBundle.getString("language.ru"),
            LanguageBundle.getString("language.sk"),
            LanguageBundle.getString("language.hu")
        );

        languageBox.setValue(LanguageBundle.getString("language."+languageCode));

        // Restore the action listener
        languageBox.setOnAction(oldHandler);
    }

    private void updateTexts() {
        if (isUpdating) return;
        isUpdating = true;
        try {
            updateTextsWithoutLanguageBox();
            updateLanguageBox(getCurrentLanguageCode());
        } finally {
            isUpdating = false;
        }
    }

    private String getCurrentLanguageCode() {
        String currentValue = languageBox.getValue();
        if (currentValue.equals(LanguageBundle.getString("language.en"))) return "en";
        if (currentValue.equals(LanguageBundle.getString("language.ru"))) return "ru";
        if (currentValue.equals(LanguageBundle.getString("language.sk"))) return "sk";
        if (currentValue.equals(LanguageBundle.getString("language.hu"))) return "hu";
        return "en"; // default to English
    }

    private void switchMode() {
        isLoginMode = !isLoginMode;
        confirmPasswordField.setVisible(!isLoginMode);
        confirmPasswordLabel.setVisible(!isLoginMode);
        
        // Update button visibility
        loginButton.setVisible(isLoginMode);
        registerButton.setVisible(!isLoginMode);
        
        updateTexts();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorText.setText(LanguageBundle.getString("login.error.empty_fields"));
            return;
        }

        try {
            if (authService.login(username, password)) {
                authService.setUserNow(username);
                errorText.setText(LanguageBundle.getString("login.success"));
                
                // Create and show main view
                Stage mainStage = new Stage();
                MainView mainView = new MainView(mainStage, authService);
                mainView.show();
                
                // Close login window
                primaryStage.close();
            } else {
                errorText.setText(LanguageBundle.getString("login.error.invalid_credentials"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorText.setText(LanguageBundle.getString("login.error.generic"));
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorText.setText(LanguageBundle.getString("login.error.empty_fields"));
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorText.setText(LanguageBundle.getString("register.error.password_mismatch"));
            return;
        }

        try {
            if (authService.getUsers().containsKey(username)) {
                errorText.setText(LanguageBundle.getString("register.error.username_exists"));
            } else {
                authService.registration(username, password);
                errorText.setText(LanguageBundle.getString("register.success"));
                switchMode(); // Switch back to login mode after successful registration
            }
        } catch (Exception e) {
            errorText.setText(LanguageBundle.getString("register.error.generic"));
        }
    }
} 