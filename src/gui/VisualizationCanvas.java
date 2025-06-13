package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import basic.Person;
import basic.Coordinates;
import basic.Location;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import java.util.function.Consumer;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.Map;
import java.util.HashMap;

import static java.lang.Math.abs;

public class VisualizationCanvas extends Canvas {

    private List<Person> persons;
    private String currentUser;
    private double scale = 1.0;
    private double translateX = 0.0;
    private double translateY = 0.0;
    private double lastMouseX;
    private double lastMouseY;
    private Consumer<basic.Person> onPersonClick;
    private Map<Long, Double> animationProgress;
    private Timeline animationTimeline;

    public VisualizationCanvas(double width, double height, String currentUser, Consumer<basic.Person> onPersonClick) {
        super(width, height);
        this.currentUser = currentUser;
        this.persons = new java.util.ArrayList<>();
        this.onPersonClick = onPersonClick;
        this.animationProgress = new HashMap<>();
        setupEventHandlers();
        setupAnimation();
        draw();
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
        for (Person person : persons) {
            if (!animationProgress.containsKey(person.getId())) {
                animationProgress.put(person.getId(), 0.0);
            }
        }
        animationProgress.keySet().removeIf(id -> persons.stream().noneMatch(p -> p.getId() == id));

        if (animationTimeline != null) {
            animationTimeline.stop();
        }
        setupAnimation();
        draw();
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
        draw();
    }

    private void setupEventHandlers() {
        this.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();

            if (deltaY < 0) {
                scale /= zoomFactor;
            } else {
                scale *= zoomFactor;
            }

            double mouseX = event.getX();
            double mouseY = event.getY();
            translateX = mouseX - (mouseX - translateX) * (scale / (scale / (deltaY < 0 ? zoomFactor : 1/zoomFactor)));
            translateY = mouseY - (mouseY - translateY) * (scale / (scale / (deltaY < 0 ? zoomFactor : 1/zoomFactor)));

            draw();
            event.consume();
        });

        this.setOnMousePressed((MouseEvent event) -> {
            lastMouseX = event.getX();
            lastMouseY = event.getY();
        });

        this.setOnMouseDragged((MouseEvent event) -> {
            translateX += event.getX() - lastMouseX;
            translateY += event.getY() - lastMouseY;
            lastMouseX = event.getX();
            lastMouseY = event.getY();
            draw();
        });

        this.setOnMouseClicked((MouseEvent event) -> {
            double clickX = (event.getX() - translateX) / scale;
            double clickY = (event.getY() - translateY) / scale;

            for (Person person : persons) {
                double baseSize = 25;
                double headRadius = baseSize / 4;
                double bodyWidth = baseSize / 2;
                double bodyHeight = baseSize * 0.7;
                double legLength = baseSize / 2.5;

                double x = person.getCoordinates().getX() * 10;
                double y = person.getCoordinates().getY() * 10;

                double startX = x - bodyWidth / 2;
                double startY = y - bodyHeight - headRadius * 2;

                if (clickX >= startX && clickX <= startX + bodyWidth &&
                    clickY >= startY && clickY <= startY + bodyHeight + legLength) {
                    if (onPersonClick != null) {
                        onPersonClick.accept(person);
                    }
                    break;
                }
            }
        });
    }

    private void setupAnimation() {
        animationTimeline = new Timeline(
            new KeyFrame(Duration.millis(16), e -> {
                boolean needsRedraw = false;
                for (Person person : persons) {
                    Double progress = animationProgress.get(person.getId());
                    if (progress != null && progress < 1.0) {
                        animationProgress.put(person.getId(), Math.min(1.0, progress + 0.05));
                        needsRedraw = true;
                    }
                }
                if (needsRedraw) {
                    draw();
                }
            })
        );
        animationTimeline.setCycleCount(Timeline.INDEFINITE);
        animationTimeline.play();
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        gc.save();

        gc.translate(translateX, translateY);
        gc.scale(scale, scale);

        for (Person person : persons) {
            drawPerson(gc, person);
        }

        gc.restore();
    }

    private void drawPerson(GraphicsContext gc, Person person) {
        double baseSize = 25;
        double headRadius = baseSize / 4;
        double bodyWidth = baseSize / 2;
        double bodyHeight = baseSize * 0.7;
        double armLength = baseSize / 2.5;
        double legLength = baseSize / 2.5;

        double currentProgress = animationProgress.getOrDefault(person.getId(), 1.0);
        double animatedScale = currentProgress;

        headRadius *= animatedScale;
        bodyWidth *= animatedScale;
        bodyHeight *= animatedScale;
        armLength *= animatedScale;
        legLength *= animatedScale;

        double x = person.getCoordinates().getX() * 10;
        double y = person.getCoordinates().getY() * 10;

        double startX = x - bodyWidth / 2;
        double startY = y - bodyHeight - headRadius * 2;

        Color userColor = getColorForUser(person.getCreator());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.setFill(userColor.deriveColor(0, 1.0, 1.0, 0.8));
        gc.fillRect(startX, startY + headRadius * 2, bodyWidth, bodyHeight);
        gc.strokeRect(startX, startY + headRadius * 2, bodyWidth, bodyHeight);

        gc.setFill(userColor);
        gc.fillOval(startX + bodyWidth / 2 - headRadius, startY + headRadius, headRadius * 2, headRadius * 2);
        gc.strokeOval(startX + bodyWidth / 2 - headRadius, startY + headRadius, headRadius * 2, headRadius * 2);

        // --- Рисуем волосы (цвет волос) с челкой --- 
        basic.Color hairColorEnum = person.getHairColor();
        if (hairColorEnum != null) {
            javafx.scene.paint.Color fxHairColor = convertBasicColorToFxColor(hairColorEnum);
            gc.setFill(fxHairColor);

            // Рисуем основную форму волос (верх и бока) как более объемную шапочку
            // Увеличиваем ширину и высоту, чтобы волосы казались пышнее и немного закрывали лоб
            gc.fillArc(startX + bodyWidth / 2 - headRadius * 1.2, // Чуть шире головы влево
                        startY + headRadius - headRadius * 0.8, // Выше верхней части головы
                        headRadius * 2.4, // Ширина (еще больше диаметра головы)
                        headRadius * 2 + headRadius * 0.8, // Высота (еще больше)
                        45, // Начальный угол
                        90, // Длина дуги
                        javafx.scene.shape.ArcType.ROUND);

            // Рисуем челку (несколько перекрывающихся овалов для эффекта прядей)
            int numBangStrands = 7; // Увеличиваем количество прядей для более пышной челки
            double strandWidth = headRadius * 0.25; // Ширина каждой пряди
            double strandHeight = headRadius * 0.4; // Длина каждой пряди

            for (int i = 0; i < numBangStrands; i++) {
                // Располагаем пряди по ширине лба, с небольшим смещением для перекрытия
                double bangX = startX + bodyWidth / 2 - (numBangStrands / 2.0 - i) * (strandWidth * 0.6);
                // Y-позиция: чуть ниже основной линии волос, с небольшой вариацией для естественности
                double bangY = startY + headRadius + headRadius * 0.4 + (Math.sin(i * 0.5) * headRadius * 0.05); 

                // Рисуем овал для каждой пряди челки
                gc.fillOval(bangX - strandWidth / 2, bangY - strandHeight / 2, strandWidth, strandHeight);
            }
        }

        gc.setStroke(userColor.deriveColor(0, 1.0, 1.0, 0.8));
        gc.setLineWidth(2);
        gc.strokeLine(startX, startY + headRadius * 2 + bodyHeight * 0.2, startX - armLength, startY + headRadius * 2 + bodyHeight * 0.5);
        gc.strokeLine(startX + bodyWidth, startY + headRadius * 2 + bodyHeight * 0.2, startX + bodyWidth + armLength, startY + headRadius * 2 + bodyHeight * 0.5);

        gc.strokeLine(startX + bodyWidth * 0.25, startY + headRadius * 2 + bodyHeight, startX + bodyWidth * 0.25, startY + headRadius * 2 + bodyHeight + legLength);
        gc.strokeLine(startX + bodyWidth * 0.75, startY + headRadius * 2 + bodyHeight, startX + bodyWidth * 0.75, startY + headRadius * 2 + bodyHeight + legLength);

        gc.setFill(Color.BLACK);
        gc.setFont(new javafx.scene.text.Font(8 / scale));
        gc.fillText(person.getName(), startX, startY + headRadius - 5);
    }

    private javafx.scene.paint.Color convertBasicColorToFxColor(basic.Color basicColor) {
        switch (basicColor) {
            case GREEN: return Color.GREEN;
            case RED: return Color.RED;
            case BLUE: return Color.BLUE;
            case YELLOW: return Color.YELLOW;
            case WHITE: return Color.WHITE;
            default: return Color.GRAY;
        }
    }

    private Color getColorForUser(String username) {
        if (username.equals(currentUser)) {
            return Color.BLUE;
        } else {
            return Color.RED;
        }
    }
} 