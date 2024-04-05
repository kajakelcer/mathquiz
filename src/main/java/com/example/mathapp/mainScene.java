package com.example.mathapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class mainScene extends test{
    public static int points = 0; // Track the user's points
    private TextField numberField;
    private TextField equationField; // To display the equation
    private TextField scoreField;
    private Random random = new Random();

    public String userName = getUserName();
    private Timeline timeline;
    private Label timerLabel;
    private Integer timeSeconds = 30;

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


        TextField username = new TextField();
        username.setEditable(false);
        username.setText(name);

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
                }));
        timeline.playFromStart();



        HBox numberButtons = new HBox(5);
        numberButtons.setAlignment(Pos.CENTER);
        for (int i = 0; i <= 9; i++) { // Create buttons for numbers 0-9
            Button btn = new Button(String.valueOf(i));
            btn.setOnAction(e -> numberField.appendText(btn.getText()));
            numberButtons.getChildren().add(btn);
        }

        Button submitButton = new Button("Check Answer");
        submitButton.setOnAction(e -> {
            if (!numberField.getText().isEmpty()) {
                int userAnswer = Integer.parseInt(numberField.getText());
                checkAnswer(userAnswer);
                numberField.clear(); // Clear the field after submission
                generateEquation(); // Generate a new equation after each submission
            }
        });

        root.getChildren().addAll(username, equationField, numberField, scoreField, numberButtons, submitButton,saveButton, endButton,timerLabel);
        generateEquation(); // Initial equation generation


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
            default -> 0; // Default case should not happen
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
        int operation = random.nextInt(3); // 0: add, 1: subtract, 2: multiply
        String operator = switch (operation) {
            case 0 -> "+";
            case 1 -> "-";
            case 2 -> "*";
            default -> "+"; // Default case should not happen
        };
        int num1, num2, numChange;

        if (operation == 2) { // If operation is multiplication
            num1 = random.nextInt(10) + 1; // 1 to 10
            num2 = random.nextInt(10) + 1; // 1 to 10
        } else { // For addition and subtraction
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


}
