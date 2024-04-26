package com.example.mathapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class mainScene extends test{
    public static int points = 0;
    public TextField numberField;
     TextField equationField;
    public TextField scoreField;
    public Random random = new Random();
    public String userName = getUserName();
    public Timeline timeline;
    public Label timerLabel;
    public Integer timeSeconds = 30;

    public mainScene(){
    }
    public void timer(){
    }

    public VBox retuRnRoot(String name){
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        equationField = new TextField();
        equationField.setEditable(false);
        equationField.setPromptText("Solve the equation here");

        numberField = new TextField();
        numberField.setEditable(false);
        numberField.setPromptText("Enter your answer here");

        scoreField = new TextField();
        scoreField.setText("Your Score: ");

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        root.setBackground(background);

        TextField username = new TextField();
        username.setEditable(false);
        username.setText(name);

        TextField numericTextField = new TextField();

        numericTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });

        TextField timeField = new TextField();
        timeField.setEditable(false);
        timeField.setText("30 sec game time");
        timeField.snapPositionX(0);
        timeField.setMaxWidth(50);

        Button endButton = new Button("Exit Game");
        endButton.setOnAction(e -> Platform.exit());

        Button saveButton = new Button("Save score");
        saveButton.setOnAction(event -> {
            try {
                saveSystem.saving();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        timerLabel = new Label();
        timerLabel.setText("Time remaining:" + timeSeconds.toString());
        timerLabel.setStyle("-fx-font-size: 40;");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeSeconds--;
                    timerLabel.setText(timeSeconds.toString());
                    if(timeSeconds <= 0){
                        timeline.stop();
                    }
                    if(timeSeconds == 0){
                        endButton.fire();
                        saveButton.fire();
                    }
                }));
        timeline.playFromStart();

        HBox numberButtons = new HBox(5);
        numberButtons.setAlignment(Pos.CENTER);
        for (int i = 0; i <= 9; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.setOnAction(e -> numericTextField.appendText(btn.getText()));
            numberButtons.getChildren().add(btn);
        }
        Button submitButton = new Button("Check Answer");
        submitButton.setOnAction(e -> {
            if (!numericTextField.getText().isEmpty()) {
                int userAnswer = Integer.parseInt(numericTextField.getText());
                checkAnswer(userAnswer);
                numericTextField.clear();
                generateEquation();
            }
        });
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
                event.consume();
            }
        });

        root.getChildren().addAll(username, equationField, numericTextField, scoreField, numberButtons, submitButton, endButton,timerLabel);
        generateEquation();

        Platform.runLater(numericTextField::requestFocus);
        root.getStylesheets().add("style.css");
        return root;
    }
    private void checkAnswer(int userAnswer) {
        String[] equationParts = equationField.getText().split(" ");
        int num1 = Integer.parseInt(equationParts[0]);
        String operator = equationParts[1];
        int num2 = Integer.parseInt(equationParts[2]);

        int correctAnswer = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            default -> 0;
        };

        if (userAnswer == correctAnswer) {
            points++;
            scoreField.setText("Your Score: " + points + " ");
        } else {
            points--;
            scoreField.setText("Your Score: " + points + " ");
        }

    }

    private void generateEquation() {
        int operation = random.nextInt(3);
        String operator = switch (operation) {
            case 0 -> "+";
            case 1 -> "-";
            case 2 -> "*";
            default -> "+";
        };
        int num1, num2, numChange;

        if (operation == 2) {
            num1 = random.nextInt(10) + 1;
            num2 = random.nextInt(10) + 1;
        } else {
            num1 = random.nextInt(41);
            num2 = random.nextInt(41);
        }
        if(num2 > num1 && operation == 1){
            numChange = num2;
            num2 = num1;
            num1 = numChange;
        }
        equationField.setText(num1 + " " + operator + " " + num2);
    }
    public static int getPoints(){
        return points;
    }
    public void setPoints(int points){
        this.points = points;
    }
    public int getTimeSeconds(){
        return timeSeconds;
    }
    public void setTimeSeconds(int timeSeconds){
        this.timeSeconds = timeSeconds;
    }

}
