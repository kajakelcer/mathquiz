package com.example.mathapp;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application {
        public static String userName;

        public void start(Stage secondaryStage) {
            mainScene root2 = new mainScene();
            VBox root = new VBox(10);
            root.setAlignment(Pos.CENTER);

            TextField usernameField = new TextField();
            usernameField.setPromptText("Enter your username");



            Button startButton = new Button("Start Game");
            startButton.setOnAction(e -> {
                userName = usernameField.getText().trim();
                Scene scene2 = new Scene(root2.retuRnRoot(userName), 300, 300);
                secondaryStage.setTitle("Math Quiz");
                secondaryStage.setScene(scene2);
                secondaryStage.show();
            });

            root.getChildren().addAll(new Label("Username:"), usernameField, startButton);

            Scene scene = new Scene(root, 300, 200);
            secondaryStage.setTitle("Start Menu");
            secondaryStage.setScene(scene);
            secondaryStage.show();


        }
        public static String getUserName(){
            return userName;
        }
        public void setUserName(String userName){
            this.userName = userName;
        }
        public void timeLimit(Stage secondaryStage){
            PauseTransition delay = new PauseTransition(Duration.seconds(30));
            delay.setOnFinished( event -> secondaryStage.close() );
            delay.play();

        }

        public static void main(String[] args) {
            launch(args);
        }
    }
