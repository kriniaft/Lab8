package gui;

import basic.Person;
import basic.Coordinates;
import basic.Location;
import basic.Color;
import basic.Country;
import database.DatabaseConnector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Tooltip;
import java.net.URL;
import javafx.scene.control.SplitPane;
import gui.VisualizationCanvas;
import java.util.ArrayList;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.sql.SQLException;

public class MainView {
    private final Stage stage;
    private final DatabaseConnector db;
    private final TableView<Person> tableView;
    private final ObservableList<Person> personList;
    private final FilteredList<Person> filteredList;
    private final TextField filterField;
    private final Label currentUserLabel;
    private final Button addButton;
    private final Button deleteSelectedButton;
    private final Button clearMyObjectsButton;
    private final Button infoButton;
    private final Button logoutButton;
    private final ComboBox<String> languageBox;
    private final ZonedDateTime collectionInitializationDateTime;
    private final Button profileButton;
    private final VisualizationCanvas visualizationCanvas;

    public MainView(Stage stage, DatabaseConnector db) throws SQLException {
        this.stage = stage;
        this.db = db;
        this.tableView = new TableView<>();
        this.personList = FXCollections.observableArrayList();
        this.filteredList = new FilteredList<>(personList);
        this.filterField = new TextField();
        this.currentUserLabel = new Label(LanguageBundle.getString("main.current_user") + " " + db.getUserNow());
        this.addButton = new Button(LanguageBundle.getString("main.add"));
        this.deleteSelectedButton = new Button(LanguageBundle.getString("main.delete_selected"));
        this.clearMyObjectsButton = new Button(LanguageBundle.getString("main.clear_my_objects"));
        
        // Load the info icon
        Image infoIcon = null;
        try {
            URL imageUrl = getClass().getResource("/gui/images/info_icon.png");
            if (imageUrl == null) {
                System.err.println("Image resource not found: /gui/images/info_icon.png");
            } else {
                infoIcon = new Image(imageUrl.toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }

        ImageView infoImageView = null;
        if (infoIcon != null) {
            infoImageView = new ImageView(infoIcon);
            infoImageView.setFitWidth(20); // Adjust size as needed
            infoImageView.setFitHeight(20); // Adjust size as needed
        }

        this.infoButton = new Button("i", infoImageView); // Устанавливаем текст 'i' как запасной
        this.infoButton.setShape(new javafx.scene.shape.Circle(15));
        this.logoutButton = new Button(LanguageBundle.getString("main.logout"));
        this.languageBox = new ComboBox<>();
        this.collectionInitializationDateTime = ZonedDateTime.now();

        // Create profile button with tooltip
        this.profileButton = new Button("P"); // You can replace 'P' with an image later
        this.profileButton.setShape(new javafx.scene.shape.Circle(15));
        this.profileButton.setMinSize(30, 30);
        this.profileButton.setMaxSize(30, 30);
        this.profileButton.setStyle("-fx-font-weight: bold; -fx-background-color: #42aaff; -fx-text-fill: white;"); // Light green
        Tooltip profileTooltip = new Tooltip();
        profileTooltip.textProperty().bind(currentUserLabel.textProperty()); // Bind to currentUserLabel text
        profileButton.setTooltip(profileTooltip);

        // Initialize VisualizationCanvas
        this.visualizationCanvas = new VisualizationCanvas(600, 1000, db.getUserNow(), person -> {
            // Lambda expression to handle clicks on persons in the canvas
            showAlert(Alert.AlertType.INFORMATION, LanguageBundle.getString("main.info.title"), person.toString());
        }); // Initial size

        setupUI();
        loadData();
    }

    private void setupUI() {
        stage.setTitle(LanguageBundle.getString("main.title"));
        

        // Настройка поля фильтра
        filterField.setPromptText(LanguageBundle.getString("main.filter"));
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return person.getName().toLowerCase().contains(lowerCaseFilter) ||
                        person.getPassportID().toLowerCase().contains(lowerCaseFilter) ||
                        person.getHairColor().toString().toLowerCase().contains(lowerCaseFilter) ||
                        person.getNationality().toString().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Настройка кнопки добавления
        addButton.setOnAction(e -> showAddDialog());

        // Настройка кнопок удаления
        deleteSelectedButton.setOnAction(e -> handleDeleteSelected());
        clearMyObjectsButton.setOnAction(e -> handleClearMyObjects());

        // Настройка кнопки информации
        infoButton.setOnAction(e -> showCollectionInfoDialog());

        // Настройка кнопки профиля
        profileButton.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, LanguageBundle.getString("main.current_user"), db.getUserNow());
        });

        // Настройка кнопки выхода
        logoutButton.setOnAction(e -> handleLogout());

        // Настройка выбора языка
        setupLanguageBox();

        // Настройка таблицы
        setupTableColumns();

        // Настройка сортировки
        SortedList<Person> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);

        // Отключаем множественный выбор
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Добавляем контекстное меню для редактирования роста
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editHeightItem = new MenuItem(LanguageBundle.getString("main.edit_height"));
        editHeightItem.setOnAction(e -> {
            Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null && selectedPerson.getCreator().equals(db.getUserNow())) {
                showEditHeightDialog(selectedPerson);
            }
        });
        contextMenu.getItems().add(editHeightItem);
        tableView.setContextMenu(contextMenu);

        // Создание layout
        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_LEFT); // Align to top-left
        topBox.getChildren().addAll(
            profileButton, // Profile button moved to the start
            logoutButton,  // Logout button moved next to profile
            new Label(LanguageBundle.getString("main.language")),
            languageBox,
            filterField,
            addButton,
            deleteSelectedButton,
            clearMyObjectsButton
        );

        // Create HBox for info button in top-right
        HBox rightTopBar = new HBox();
        rightTopBar.setAlignment(Pos.TOP_RIGHT);
        rightTopBar.setPadding(new Insets(10));
        rightTopBar.getChildren().add(infoButton);

        HBox overallTopBar = new HBox();
        overallTopBar.getChildren().addAll(topBox, rightTopBar);
        HBox.setHgrow(topBox, Priority.ALWAYS); // Allow topBox to grow and push rightTopBar to the right

        BorderPane root = new BorderPane();
        root.setTop(overallTopBar);

        // Create SplitPane for visualization and table
        SplitPane centerPane = new SplitPane();
        centerPane.getItems().addAll(visualizationCanvas, tableView);
        centerPane.setDividerPositions(0.3); // Устанавливаем начальную позицию напрямую
        root.setCenter(centerPane);

        Scene scene = new Scene(root, 1600, 800);
        stage.setScene(scene);
    }

    private void setupTableColumns() {
        tableView.getColumns().clear();

        // Настройка колонок таблицы
        TableColumn<Person, String> nameColumn = new TableColumn<>(LanguageBundle.getString("main.name"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Person, String> passportColumn = new TableColumn<>(LanguageBundle.getString("main.passport"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<>("passportID"));

        TableColumn<Person, Color> hairColorColumn = new TableColumn<>(LanguageBundle.getString("main.hair_color"));
        hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));

        TableColumn<Person, Country> nationalityColumn = new TableColumn<>(LanguageBundle.getString("main.nationality"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        TableColumn<Person, Float> heightColumn = new TableColumn<>(LanguageBundle.getString("main.height"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        // Координаты
        TableColumn<Person, Double> coordXColumn = new TableColumn<>(LanguageBundle.getString("main.x"));
        coordXColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            return new SimpleDoubleProperty(person.getCoordinates().getX()).asObject();
        });

        TableColumn<Person, Double> coordYColumn = new TableColumn<>(LanguageBundle.getString("main.y"));
        coordYColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            return new SimpleDoubleProperty(person.getCoordinates().getY()).asObject();
        });

        // Локация
        TableColumn<Person, Double> locXColumn = new TableColumn<>(LanguageBundle.getString("main.location_x"));
        locXColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            return new SimpleDoubleProperty(person.getLocation().getX()).asObject();
        });

        TableColumn<Person, Double> locYColumn = new TableColumn<>(LanguageBundle.getString("main.location_y"));
        locYColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            return new SimpleDoubleProperty(person.getLocation().getY()).asObject();
        });

        TableColumn<Person, Double> locZColumn = new TableColumn<>(LanguageBundle.getString("main.location_z"));
        locZColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            return new SimpleDoubleProperty(person.getLocation().getZ()).asObject();
        });

        // Дата создания
        TableColumn<Person, String> creationDateColumn = new TableColumn<>(LanguageBundle.getString("main.creation_date"));
        creationDateColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            ZonedDateTime creationDate = person.getCreationDate();
            if (creationDate != null) {
                return new SimpleStringProperty(creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            return new SimpleStringProperty("");
        });

        TableColumn<Person, String> creationTimeColumn = new TableColumn<>(LanguageBundle.getString("main.creation_time"));
        creationTimeColumn.setCellValueFactory(cellData -> {
            Person person = cellData.getValue();
            ZonedDateTime creationDate = person.getCreationDate();
            if (creationDate != null) {
                return new SimpleStringProperty(creationDate.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
            return new SimpleStringProperty("");
        });

        // Настройка стилей для строк таблицы
        tableView.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<Person>() {
                @Override
                protected void updateItem(Person item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setStyle(null); // Clear style entirely, allowing default
                        setDisable(false);
                    } else {
                        if (item.getCreator().equals(db.getUserNow())) {
                            setStyle(null); // Allow default styles for user's own items
                            setDisable(false);
                        } else {
                            setStyle("-fx-background-color: #FFCCCC;"); // Light-red background for others' items
                            setDisable(true); // Keep disabled as requested
                        }
                    }
                }
            };
            return row;
        });

        tableView.getColumns().addAll(
            nameColumn, passportColumn, hairColorColumn, nationalityColumn,
            heightColumn, coordXColumn, coordYColumn, locXColumn, locYColumn, locZColumn,
            creationDateColumn, creationTimeColumn
        );
    }

    private void loadData() throws SQLException {
        personList.clear();
        ArrayDeque<Person> persons = db.getPersons();
        if (persons != null) {
            personList.addAll(persons);
            visualizationCanvas.setPersons(new ArrayList<>(persons)); // Update canvas with data
        }
    }

    private void setupLanguageBox() {
        languageBox.getItems().addAll(
            LanguageBundle.getString("language.ru"),
            LanguageBundle.getString("language.en"),
            LanguageBundle.getString("language.sk"),
            LanguageBundle.getString("language.hu")
        );
        languageBox.setValue(LanguageBundle.getString("language.ru"));

        languageBox.setOnAction(e -> {
            String selected = languageBox.getValue();
            if (selected != null) {
                changeLanguage(selected);
            }
        });
    }

    private void changeLanguage(String language) {
        switch (language) {
            case "Русский":
            case "Russian":
            case "Ruština":
            case "Orosz":
                LanguageBundle.setRussian();
                break;
            case "English":
            case "Английский":
            case "Angličtina":
            case "Angol":
                LanguageBundle.setEnglish();
                break;
            case "Slovenčina":
            case "Slovak":
            case "Словацкий":
            case "Szlovák":
                LanguageBundle.setSlovak();
                break;
            case "Magyar":
            case "Hungarian":
            case "Maďarština":
            case "Венгерский":
                LanguageBundle.setHungarian();
                break;
        }
        updateTexts();
    }

    private void updateTexts() {
        stage.setTitle(LanguageBundle.getString("main.title"));
        currentUserLabel.setText(LanguageBundle.getString("main.current_user") + " " + db.getUserNow());
        filterField.setPromptText(LanguageBundle.getString("main.filter"));
        addButton.setText(LanguageBundle.getString("main.add"));
        deleteSelectedButton.setText(LanguageBundle.getString("main.delete_selected"));
        clearMyObjectsButton.setText(LanguageBundle.getString("main.clear_my_objects"));
        setupTableColumns();
    }

    private void showAddDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setTitle(LanguageBundle.getString("main.add"));

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        // Создаем поля ввода
        TextField nameField = new TextField();
        nameField.setPromptText(LanguageBundle.getString("main.name"));

        TextField passportField = new TextField();
        passportField.setPromptText(LanguageBundle.getString("main.passport"));

        ComboBox<Color> hairColorBox = new ComboBox<>(FXCollections.observableArrayList(Color.values()));
        hairColorBox.setPromptText(LanguageBundle.getString("main.hair_color"));

        ComboBox<Country> nationalityBox = new ComboBox<>(FXCollections.observableArrayList(Country.values()));
        nationalityBox.setPromptText(LanguageBundle.getString("main.nationality"));

        TextField heightField = new TextField();
        heightField.setPromptText(LanguageBundle.getString("main.height"));

        // Координаты
        TextField coordXField = new TextField();
        coordXField.setPromptText(LanguageBundle.getString("main.x"));
        TextField coordYField = new TextField();
        coordYField.setPromptText(LanguageBundle.getString("main.y"));

        // Локация
        TextField locXField = new TextField();
        locXField.setPromptText(LanguageBundle.getString("main.location_x"));
        TextField locYField = new TextField();
        locYField.setPromptText(LanguageBundle.getString("main.location_y"));
        TextField locZField = new TextField();
        locZField.setPromptText(LanguageBundle.getString("main.location_z"));

        // Кнопки
        Button addButton = new Button(LanguageBundle.getString("main.add"));
        Button cancelButton = new Button(LanguageBundle.getString("main.cancel"));

        addButton.setOnAction(e -> {
            try {
                // Создаем объект
                String name = nameField.getText();
                String passportID = passportField.getText();
                Color hairColor = hairColorBox.getValue();
                Country nationality = nationalityBox.getValue();

                // Validate that hair color and nationality are selected
                if (hairColor == null || nationality == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LanguageBundle.getString("main.error.title"));
                    alert.setHeaderText(null);
                    alert.setContentText(LanguageBundle.getString("main.error.missing_color_or_nationality"));
                    alert.showAndWait();
                    return; // Stop further processing
                }

                float height = Float.parseFloat(heightField.getText());
                // Validate height is greater than 0
                if (height <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LanguageBundle.getString("main.error.title"));
                    alert.setHeaderText(null);
                    alert.setContentText(LanguageBundle.getString("main.error.invalid_input"));
                    alert.showAndWait();
                    return; // Stop further processing
                }

                float coordX = Float.parseFloat(coordXField.getText());
                float coordY = Float.parseFloat(coordYField.getText());
                float locX = Float.parseFloat(locXField.getText());
                float locY = Float.parseFloat(locYField.getText());
                float locZ = Float.parseFloat(locZField.getText());

                Coordinates coordinates = new Coordinates(coordX, coordY);
                Location location = new Location(locX, locY, locZ);
                Person person = new Person(db.minId(), name, coordinates, height, passportID, hairColor, nationality, location);
                person.setCreator(db.getUserNow());

                if (db.addPerson(person)) {
                    personList.add(person);
                    dialogStage.close();
                    loadData(); // Обновляем данные после успешного добавления
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(LanguageBundle.getString("main.error.title"));
                    alert.setHeaderText(null);
                    alert.setContentText(LanguageBundle.getString("main.error.add_failed"));
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(LanguageBundle.getString("main.error.title"));
                alert.setHeaderText(null);
                alert.setContentText(LanguageBundle.getString("main.error.invalid_input"));
                alert.showAndWait();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(LanguageBundle.getString("main.error.title"));
                alert.setHeaderText(null);
                alert.setContentText(LanguageBundle.getString("main.error.database"));
                alert.showAndWait();
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, cancelButton);

        dialogVBox.getChildren().addAll(
            nameField, passportField, hairColorBox, nationalityBox, heightField,
            coordXField, coordYField, locXField, locYField, locZField,
            buttonBox
        );

        Scene dialogScene = new Scene(dialogVBox);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private void showEditHeightDialog(Person person) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(stage);
        dialogStage.setTitle(LanguageBundle.getString("main.edit_height"));

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        // Создаем поле ввода для роста
        TextField heightField = new TextField(String.valueOf(person.getHeight()));
        heightField.setPromptText(LanguageBundle.getString("main.height"));

        // Кнопки
        Button saveButton = new Button(LanguageBundle.getString("main.save"));
        Button cancelButton = new Button(LanguageBundle.getString("main.cancel"));

        saveButton.setOnAction(e -> {
            try {
                float height = Float.parseFloat(heightField.getText());
                
                if (db.updateByUd(height, person.getId())) {
                    // Обновляем объект в списке
                    int index = personList.indexOf(person);
                    if (index != -1) {
                        person.setHeight(height);
                        personList.set(index, person);
                    }
                    dialogStage.close();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(LanguageBundle.getString("main.error.title"));
                alert.setHeaderText(null);
                alert.setContentText(LanguageBundle.getString("main.error.invalid_input"));
                alert.showAndWait();
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        dialogVBox.getChildren().addAll(heightField, buttonBox);

        Scene dialogScene = new Scene(dialogVBox);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private void handleDeleteSelected() {
        Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
        if (selectedPerson == null) {
            showAlert(Alert.AlertType.WARNING, LanguageBundle.getString("main.error.title"), LanguageBundle.getString("main.error.no_selection"));
            return;
        }

        if (!selectedPerson.getCreator().equals(db.getUserNow())) {
            showAlert(Alert.AlertType.ERROR, LanguageBundle.getString("main.error.title"), LanguageBundle.getString("main.error.not_owner"));
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(LanguageBundle.getString("main.confirm.title"));
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(LanguageBundle.getString("main.confirm.delete_selected"));

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    db.removePersonById(selectedPerson.getId());
                    loadData(); // Reload data to refresh both table and canvas
                    tableView.getSelectionModel().clearSelection();
                    showAlert(Alert.AlertType.INFORMATION, LanguageBundle.getString("main.success.title"), LanguageBundle.getString("main.success.delete_selected"));
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, LanguageBundle.getString("main.error.title"), LanguageBundle.getString("main.error.delete_failed"));
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleClearMyObjects() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(LanguageBundle.getString("main.confirm.title"));
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(LanguageBundle.getString("main.confirm.clear_my_objects"));

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    db.clearPersonsByUser(db.getUserNow());
                    loadData(); // Reload all data to refresh the table and canvas
                    tableView.getSelectionModel().clearSelection(); // Clear selection after clearing objects
                    showAlert(Alert.AlertType.INFORMATION, LanguageBundle.getString("main.success.title"), LanguageBundle.getString("main.success.clear_my_objects"));
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, LanguageBundle.getString("main.error.title"), LanguageBundle.getString("main.error.database"));
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showCollectionInfoDialog() {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(LanguageBundle.getString("main.info.title"));
        infoAlert.setHeaderText(null);

        String infoContent = String.format(
            LanguageBundle.getString("main.info.type") + ": %s\n" +
            LanguageBundle.getString("main.info.elements") + ": %d\n" +
            LanguageBundle.getString("main.info.init_date") + ": %s",
            "ArrayDeque",
            personList.size(),
            collectionInitializationDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss Z"))
        );
        infoAlert.setContentText(infoContent);
        infoAlert.showAndWait();
    }

    private void handleLogout() {
        db.setUserNow(null);
        stage.close();
        // Reopen login view
        Stage loginStage = new Stage();
        AuthView authView = new AuthView(loginStage, db);
        authView.show();
    }

    public void show() {
        stage.show();
    }
} 